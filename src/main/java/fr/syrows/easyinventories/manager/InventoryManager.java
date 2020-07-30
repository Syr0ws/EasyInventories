package fr.syrows.easyinventories.manager;

import fr.syrows.easyinventories.contents.containers.InventorySort;
import fr.syrows.easyinventories.creators.InventoryCreator;
import fr.syrows.easyinventories.inventories.SimpleInventory;
import fr.syrows.easyinventories.tools.CloseReason;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.stream.Collectors;

public interface InventoryManager {

    void openInventory(Player player, SimpleInventory inventory);

    void closeInventory(Player player, CloseReason reason);

    boolean hasOpenedInventory(Player player);

    InventoryCreator findCreator(InventorySort sort);

    SimpleInventory getOpenedInventory(Player player);

    List<SimpleInventory> getInventories();

    List<Player> getViewers();

    default List<Player> getViewers(String id) {
        return this.getViewers().stream()
                .filter(viewer -> this.getOpenedInventory(viewer).getIdentifier().equals(id))
                .collect(Collectors.toList());
    }

    default List<Player> getViewers(SimpleInventory inventory) {
        return this.getViewers().stream()
                .filter(viewer -> this.getOpenedInventory(viewer).equals(inventory))
                .collect(Collectors.toList());
    }

    default List<Player> getViewers(Class<? extends SimpleInventory> clazz) {
        return this.getViewers().stream()
                .filter(viewer -> this.getOpenedInventory(viewer).getClass().equals(clazz))
                .collect(Collectors.toList());
    }

    default <T extends SimpleInventory> List<T> getInventories(Class<T> clazz) {
        return this.getInventories().stream()
                .filter(inventory -> inventory.getClass().equals(clazz))
                .map(clazz::cast)
                .collect(Collectors.toList());
    }
}
