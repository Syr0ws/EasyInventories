package fr.syrows.easyinventories.creators;

import fr.syrows.easyinventories.contents.InventorySort;
import fr.syrows.easyinventories.inventories.AdvancedInventory;
import org.bukkit.inventory.Inventory;

public interface InventoryCreator {

    Inventory getInventory(AdvancedInventory inventory);

    boolean isSupported(InventorySort sort);
}
