package fr.syrows.inventories.creators;

import fr.syrows.inventories.InventorySort;
import fr.syrows.inventories.interfaces.AdvancedInventory;
import org.bukkit.inventory.Inventory;

public interface InventoryCreator {

    Inventory getInventory(AdvancedInventory inventory);

    boolean isSupported(InventorySort sort);
}
