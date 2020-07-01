package fr.syrows.easyinventories.inventories;

import fr.syrows.easyinventories.contents.ContainerType;
import fr.syrows.easyinventories.contents.InventoryContents;
import fr.syrows.easyinventories.contents.InventoryManager;
import fr.syrows.easyinventories.listeners.InventoryListenerManager;
import fr.syrows.easyinventories.openers.impl.DefaultInventoryOpener;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public interface SimpleInventory {

    String getIdentifier();

    String getTitle();

    int getSize();

    int getRows();

    int getColumns();

    ContainerType getType();

    InventoryContents getContents();

    InventoryManager getInventoryManager();

    InventoryListenerManager getListenerManager();

    Inventory getInventory();

    default void open(Player player) {
        this.getInventoryManager().open(player, this, DefaultInventoryOpener.INSTANCE);
    }

    default void close(Player player) {
        this.getInventoryManager().close(player);
    }
}
