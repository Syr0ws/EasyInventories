package fr.syrows.easyinventories.creators;

import fr.syrows.easyinventories.contents.ContainerType;
import fr.syrows.easyinventories.inventories.SimpleInventory;
import org.bukkit.inventory.Inventory;

public interface InventoryCreator {

    Inventory getInventory(SimpleInventory inventory);

    boolean isSupported(ContainerType sort);
}
