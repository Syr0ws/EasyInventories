package fr.syrows.easyinventories.manager;

import fr.syrows.easyinventories.contents.InventoryContents;
import fr.syrows.easyinventories.contents.containers.InventorySort;
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

    public static final List<InventoryCreator> creators = new ArrayList<>();

    static {
        creators.add(new ChestInventoryCreator());
        creators.add(new CommonInventoryCreator());
    }

    private Plugin plugin;
    private Map<Player, SimpleInventory> inventories = new HashMap<>();

    public DefaultInventoryManager(Plugin plugin) {

        this.plugin = plugin;

        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new InventoryListeners(), this.plugin);
    }

    @Override
    public void openInventory(Player player, SimpleInventory inventory) {

        if(inventory.getInventory() == null)
            throw new NullPointerException("Bukkit inventory is null. Create it first.");

        if(this.hasOpenedInventory(player)) this.closeInventory(player, CloseReason.CLOSE_ALL);

        SimpleInventoryOpenEvent event = new SimpleInventoryOpenEvent(player, inventory);

        inventory.getListenerManager().accept(event);

        Bukkit.getPluginManager().callEvent(event);

        this.inventories.put(player, inventory);

        player.openInventory(inventory.getInventory());
    }

    @Override
    public void closeInventory(Player player, CloseReason reason) {

        this.silentClose(player, reason);

        player.closeInventory();
        player.updateInventory();
    }

    @Override
    public boolean hasOpenedInventory(Player player) {
        return this.inventories.containsKey(player);
    }

    @Override
    public InventoryCreator findCreator(InventorySort sort) {

        Optional<InventoryCreator> optional = creators.stream()
                .filter(creator -> creator.isSupported(sort))
                .findFirst();

        if(!optional.isPresent())
            throw new NullPointerException(String.format("No container found for type '%s'.", sort.getInventoryType().name()));

        return optional.get();
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

    private void silentClose(Player player, CloseReason reason) {

        if(this.hasOpenedInventory(player)) {

            SimpleInventory inventory = this.getOpenedInventory(player);
            SimpleInventoryCloseEvent event = new SimpleInventoryCloseEvent(player, inventory, reason);

            inventory.getListenerManager().accept(event);

            Bukkit.getPluginManager().callEvent(event);

            this.inventories.remove(player);
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

                event.setCancelled(true);

                InventoryContents contents = inventory.getContents();

                SimpleInventoryClickEvent customEvent = new SimpleInventoryClickEvent(event, inventory);

                Optional<ClickableItem> optional = contents.getItem(event.getSlot());
                optional.ifPresent(clickableItem -> clickableItem.accept(customEvent));

                inventory.getListenerManager().accept(customEvent);

                Bukkit.getPluginManager().callEvent(customEvent);

            } else event.setCancelled(this.toCancel.contains(event.getAction()));
        }

        @EventHandler
        public void onInventoryClose(InventoryCloseEvent event) {

            Player player = (Player) event.getPlayer();

            DefaultInventoryManager.this.silentClose(player, CloseReason.CLOSE_ALL);

            player.updateInventory();
        }

        @EventHandler
        public void onInventoryDrag(InventoryDragEvent event) {

            Player player = (Player) event.getWhoClicked();

            // Do not use consumers here.
            // The clicked inventory is always the top inventory so if a player clicks on its
            // inventory, it will consider the one at the top and not the real clicked.

            if(!DefaultInventoryManager.this.hasOpenedInventory(player)) return;

            SimpleInventory inventory = DefaultInventoryManager.this.getOpenedInventory(player);

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
