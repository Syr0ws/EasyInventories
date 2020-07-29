package fr.syrows.easyinventories.contents;

import fr.syrows.easyinventories.contents.items.ClickableItem;
import fr.syrows.easyinventories.creators.InventoryCreator;
import fr.syrows.easyinventories.creators.impl.ChestInventoryCreator;
import fr.syrows.easyinventories.creators.impl.CommonInventoryCreator;
import fr.syrows.easyinventories.events.SimpleInventoryClickEvent;
import fr.syrows.easyinventories.events.SimpleInventoryOpenEvent;
import fr.syrows.easyinventories.events.SimpleInventoryCloseEvent;
import fr.syrows.easyinventories.inventories.SimpleInventory;
import fr.syrows.easyinventories.openers.InventoryOpener;
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
import java.util.stream.Collectors;

public class InventoryManager {

    private static final List<InventoryCreator> CREATORS = Arrays.asList(new ChestInventoryCreator(), new CommonInventoryCreator());

    private Plugin plugin;

    private HashMap<Player, SimpleInventory> inventories = new HashMap<>();

    public InventoryManager(Plugin plugin) {

        this.plugin = plugin;

        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new InventoryListeners(), this.plugin);
    }

    public Inventory create(SimpleInventory inventory) {

        ContainerType sort = inventory.getType();

        Optional<InventoryCreator> optional = InventoryManager.CREATORS.stream()
                .filter(creator -> creator.isSupported(sort))
                .findFirst();

        if(!optional.isPresent())
            throw new NullPointerException(String.format("No inventory creator found for type '%s'.", sort.name()));

        return optional.get().getInventory(inventory);
    }

    public <T extends SimpleInventory> void open(Player player, T inventory, InventoryOpener<T> opener) {

        if(inventory.getInventory() == null)
            throw new NullPointerException("Bukkit inventory is null. Create it first.");

        if(this.hasOpenedInventory(player)) this.close(player, CloseReason.CLOSE_ALL);

        opener.open(player, inventory);

        SimpleInventoryOpenEvent event = new SimpleInventoryOpenEvent(player, inventory);

        inventory.getListenerManager().accept(event);

        Bukkit.getPluginManager().callEvent(event);

        this.inventories.put(player, inventory);
    }

    public void close(Player player, CloseReason reason) {

        this.silentClose(player, reason);

        player.closeInventory();
        player.updateInventory();
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

    public boolean hasOpenedInventory(Player player) {
        return this.inventories.containsKey(player);
    }

    public SimpleInventory getOpenedInventory(Player player) {
        return this.inventories.getOrDefault(player, null);
    }

    public List<Player> getViewers(SimpleInventory inventory) {

        return this.inventories.entrySet().stream()
                .filter(entry -> entry.getValue().equals(inventory))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    public List<Player> getViewers(String identifier) {

        return this.inventories.entrySet().stream()
                .filter(entry -> entry.getValue().getIdentifier().equals(identifier))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    public List<Player> getViewers(Class<? extends SimpleInventory> clazz) {

        return this.inventories.entrySet().stream()
                .filter(entry -> entry.getValue().getClass().equals(clazz))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    public <T extends SimpleInventory> List<T> getInventories(Class<T> clazz) {

        return this.inventories.values().stream()
                .filter(inventory -> inventory.getClass().equals(clazz))
                .map(clazz::cast)
                .collect(Collectors.toList());
    }

    public ArrayList<Player> getAllViewers() {
        return new ArrayList<>(this.inventories.keySet());
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

            if(clicked == null || !InventoryManager.this.hasOpenedInventory(player)) return;

            SimpleInventory inventory = InventoryManager.this.getOpenedInventory(player);

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

            InventoryManager.this.silentClose(player, CloseReason.CLOSE_ALL);

            player.updateInventory();
        }

        @EventHandler
        public void onInventoryDrag(InventoryDragEvent event) {

            Player player = (Player) event.getWhoClicked();

            // Do not use consumers here.
            // The clicked inventory is always the top inventory so if a player clicks on its
            // inventory, it will consider the one at the top and not the real clicked.

            if(!InventoryManager.this.hasOpenedInventory(player)) return;

            SimpleInventory inventory = InventoryManager.this.getOpenedInventory(player);

            boolean match = event.getRawSlots().stream().anyMatch(slot -> slot < inventory.getSize());

            event.setCancelled(match);
        }

        @EventHandler
        public void onPlayerQuit(PlayerQuitEvent event) {

            Player player = event.getPlayer();

            InventoryManager.this.close(player, CloseReason.CLOSE_ALL);
        }

        @EventHandler
        public void onPluginDisable(PluginDisableEvent event) {

            if(!event.getPlugin().equals(InventoryManager.this.plugin)) return;

            ArrayList<Player> viewers = InventoryManager.this.getAllViewers();

            viewers.forEach(viewer -> InventoryManager.this.close(viewer, CloseReason.CLOSE_ALL));
        }
    }
}
