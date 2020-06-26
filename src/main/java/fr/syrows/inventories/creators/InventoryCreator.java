package fr.syrows.inventories.creators;

import fr.syrows.inventories.InventorySort;
import fr.syrows.inventories.interfaces.EasyInventory;
import org.bukkit.inventory.Inventory;

public interface InventoryCreator {

    Inventory getInventory(EasyInventory inventory);

    boolean isSupported(InventorySort sort);
}
