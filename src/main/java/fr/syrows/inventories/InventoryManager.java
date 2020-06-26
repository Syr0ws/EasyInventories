package fr.syrows.inventories;

import fr.syrows.inventories.contents.InventoryContents;
import fr.syrows.inventories.contents.items.ClickableItem;
import fr.syrows.inventories.creators.InventoryCreator;
import fr.syrows.inventories.creators.impl.ChestInventoryCreator;
import fr.syrows.inventories.creators.impl.SpecialInventoryCreator;
import fr.syrows.inventories.interfaces.EasyInventory;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class InventoryManager {

    private static final List<InventoryCreator> creators = Arrays.asList(new ChestInventoryCreator(), new SpecialInventoryCreator());

    private Plugin plugin;

    private HashMap<Player, EasyInventory> inventories = new HashMap<>();

    public InventoryManager(Plugin plugin) {

        this.plugin = plugin;

        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new InventoryListeners(), this.plugin);
    }

    public Inventory create(EasyInventory inventory) {

        InventorySort sort = inventory.getSort();

        Optional<InventoryCreator> optional = InventoryManager.creators.stream()
                .filter(creator -> creator.isSupported(sort))
                .findFirst();

        if(!optional.isPresent())
            throw new NullPointerException(String.format("No inventory creator found for type '%s'.", sort.name()));

        return optional.get().getInventory(inventory);
    }

    public void open(Player player, EasyInventory inventory) {

        if(inventory.getInventory() == null)
            throw new NullPointerException("Bukkit inventory is null. Create it first.");

        if(this.hasOpenedInventory(player)) this.close(player);

        player.openInventory(inventory.getInventory());

        this.inventories.put(player, inventory);
    }

    public void close(Player player) {

        player.closeInventory();

        this.inventories.remove(player);
    }

    public boolean hasOpenedInventory(Player player) {
        return this.inventories.containsKey(player);
    }

    public EasyInventory getOpenedInventory(Player player) {
        return this.inventories.getOrDefault(player, null);
    }

    public List<Player> getViewers(EasyInventory inventory) {

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

            EasyInventory inventory = InventoryManager.this.getOpenedInventory(player);

            if(!clicked.equals(player.getInventory())) {

                event.setCancelled(true);

                InventoryContents<? extends EasyInventory> contents = inventory.getContents();

                Optional<ClickableItem> optional = contents.getItem(event.getSlot());

                optional.ifPresent(clickableItem -> clickableItem.accept(event));

            } else event.setCancelled(this.toCancel.contains(event.getAction()));
        }

        @EventHandler
        public void onInventoryClose(InventoryCloseEvent event) {

            Player player = (Player) event.getPlayer();

            if(!InventoryManager.this.hasOpenedInventory(player)) return;

            InventoryManager.this.inventories.remove(player);
        }

        @EventHandler
        public void onPlayerQuit(PlayerQuitEvent event) {

            Player player = event.getPlayer();

            if(!InventoryManager.this.hasOpenedInventory(player)) return;

            EasyInventory inventory = InventoryManager.this.getOpenedInventory(player);

            inventory.close(player);
        }

        @EventHandler
        public void onPluginDisable(PluginDisableEvent event) {

            if(!event.getPlugin().equals(InventoryManager.this.plugin)) return;

            ArrayList<Player> viewers = InventoryManager.this.getAllViewers();

            viewers.forEach(player -> {

                EasyInventory inventory = InventoryManager.this.getOpenedInventory(player);
                inventory.close(player);
            });
        }
    }
}
