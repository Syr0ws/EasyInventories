package fr.syrows.easyinventories.inventories;

import fr.syrows.easyinventories.contents.InventoryContents;
import fr.syrows.easyinventories.contents.containers.InventorySort;
import fr.syrows.easyinventories.listeners.InventoryListenerManager;
import fr.syrows.easyinventories.manager.InventoryManager;
import fr.syrows.easyinventories.tools.CloseReason;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public interface SimpleInventory {

    String getIdentifier();

    String getTitle();

    int getSize();

    int getRows();

    int getColumns();

    InventorySort getContainer();

    InventoryContents getContents();

    InventoryManager getInventoryManager();

    InventoryListenerManager getListenerManager();

    Inventory getInventory();

    void open(Player player);

    void close(Player player, CloseReason reason);
}
