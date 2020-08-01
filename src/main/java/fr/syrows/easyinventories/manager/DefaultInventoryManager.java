/*
 * Copyright 2020 Syr0ws
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package fr.syrows.easyinventories.manager;

import fr.syrows.easyinventories.contents.InventoryContents;
import fr.syrows.easyinventories.contents.sort.InventorySort;
import fr.syrows.easyinventories.contents.items.ClickableItem;
import fr.syrows.easyinventories.creators.ChestInventoryCreator;
import fr.syrows.easyinventories.creators.CommonInventoryCreator;
import fr.syrows.easyinventories.creators.InventoryCreator;
import fr.syrows.easyinventories.events.SimpleInventoryClickEvent;
import fr.syrows.easyinventories.events.SimpleInventoryCloseEvent;
import fr.syrows.easyinventories.events.SimpleInventoryOpenEvent;
import fr.syrows.easyinventories.inventories.SimpleInventory;
import fr.syrows.easyinventories.tools.CloseReason;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class DefaultInventoryManager implements InventoryManager {

    static {
        InventoryCreator.creators.add(new ChestInventoryCreator());
        InventoryCreator.creators.add(new CommonInventoryCreator());
    }

    private Plugin plugin; // Needed in the PluginDisableEvent.
    private Map<Player, SimpleInventory> inventories = new HashMap<>();

    public DefaultInventoryManager(Plugin plugin) {

        this.plugin = plugin;

        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new InventoryListeners(), this.plugin); // Registering listener class.
    }

    @Override
    public void openInventory(Player player, SimpleInventory inventory) {

        if(inventory.getInventory() == null)
            throw new NullPointerException("Bukkit inventory is null. Create it first.");

        if(this.hasOpenedInventory(player))
            this.closeInventory(player, CloseReason.CLOSE_ALL); // Closing the opened inventory if player has one.

        SimpleInventoryOpenEvent event = new SimpleInventoryOpenEvent(player, inventory);

        inventory.getListenerManager().accept(event); // Calling listeners registered in the listener manager.
        Bukkit.getPluginManager().callEvent(event); // Calling listeners registered in the classes that implement Listener.

        this.inventories.put(player, inventory);

        player.openInventory(inventory.getInventory());
    }

    @Override
    public void closeInventory(Player player, CloseReason reason) {

        // Using silent close method to handle data.
        // Must be called before closing inventory or else the InventoryCloseEvent will do it again.
        this.silentClose(player, reason);

        player.closeInventory(); // Closing the inventory.
        player.updateInventory(); // Updating player's inventory to prevent graphic bugs.
    }

    @Override
    public boolean hasOpenedInventory(Player player) {
        return this.inventories.containsKey(player);
    }

    @Override
    public SimpleInventory getOpenedInventory(Player player) {
        return this.inventories.getOrDefault(player, null);
    }

    @Override
    public List<SimpleInventory> getInventories() {
        return new ArrayList<>(this.inventories.values());
    }

    @Override
    public List<Player> getViewers() {
        return new ArrayList<>(this.inventories.keySet());
    }

    // This method will not close the inventory using the Player#closeInventory() method.
    // This has for goal to prevent stackOverFlow errors with the InventoryCloseEvent below.
    private void silentClose(Player player, CloseReason reason) {

        if(this.hasOpenedInventory(player)) {

            SimpleInventory inventory = this.getOpenedInventory(player); // Retrieving the opened inventory.
            SimpleInventoryCloseEvent event = new SimpleInventoryCloseEvent(player, inventory, reason);

            inventory.getListenerManager().accept(event); // Calling listeners registered in the listener manager.
            Bukkit.getPluginManager().callEvent(event); // Calling listeners registered in the classes that implement Listener.

            this.inventories.remove(player); // Removing data.
        }
    }

    private class InventoryListeners implements Listener {

        private final List<InventoryAction> toCancel = Arrays.asList(
                InventoryAction.MOVE_TO_OTHER_INVENTORY,
                InventoryAction.UNKNOWN,
                InventoryAction.NOTHING
        );

        @EventHandler
        public void onInventoryClick(InventoryClickEvent event) {

            Player player = (Player) event.getWhoClicked();

            Inventory clicked = event.getClickedInventory();

            if(clicked == null || !DefaultInventoryManager.this.hasOpenedInventory(player)) return;

            SimpleInventory inventory = DefaultInventoryManager.this.getOpenedInventory(player);

            if(!clicked.equals(player.getInventory())) {

                event.setCancelled(true); // Cancelling actions in this inventory.

                InventoryContents contents = inventory.getContents();

                SimpleInventoryClickEvent customEvent = new SimpleInventoryClickEvent(event, inventory);

                Optional<ClickableItem> optional = contents.getItem(event.getSlot()); // Retrieving current item.
                optional.ifPresent(clickableItem -> clickableItem.accept(customEvent)); // Executing its consumer if it has one.

                inventory.getListenerManager().accept(customEvent); // Calling listeners registered in the listener manager.
                Bukkit.getPluginManager().callEvent(customEvent); // Calling listeners registered in the classes that implement Listener.

            } else event.setCancelled(this.toCancel.contains(event.getAction())); // Cancelling only the the action is contained in the toCancel list above.
        }

        @EventHandler
        public void onInventoryClose(InventoryCloseEvent event) {

            Player player = (Player) event.getPlayer();

            // Inventory is already closed so it's not necessary to close it again.
            // That is why we use the silent close method here.
            DefaultInventoryManager.this.silentClose(player, CloseReason.CLOSE_ALL);

            player.updateInventory(); // Updating player's inventory to prevent graphic bugs.
        }

        @EventHandler
        public void onInventoryDrag(InventoryDragEvent event) {

            Player player = (Player) event.getWhoClicked();

            // Do not use consumers here.
            // The clicked inventory is always the top inventory so if a player clicks on its
            // inventory, it will consider the one at the top and not the real clicked.

            if(!DefaultInventoryManager.this.hasOpenedInventory(player)) return;

            SimpleInventory inventory = DefaultInventoryManager.this.getOpenedInventory(player);

            // Checking that the clicked slot is inside the opened inventory.
            boolean match = event.getRawSlots().stream().anyMatch(slot -> slot < inventory.getSize());

            event.setCancelled(match);
        }

        @EventHandler
        public void onPlayerQuit(PlayerQuitEvent event) {

            Player player = event.getPlayer();

            DefaultInventoryManager.this.closeInventory(player, CloseReason.CLOSE_ALL);
        }

        @EventHandler
        public void onPluginDisable(PluginDisableEvent event) {

            if(!event.getPlugin().equals(DefaultInventoryManager.this.plugin)) return;

            List<Player> viewers = DefaultInventoryManager.this.getViewers();

            viewers.forEach(viewer -> DefaultInventoryManager.this.closeInventory(viewer, CloseReason.CLOSE_ALL));
        }
    }
}
