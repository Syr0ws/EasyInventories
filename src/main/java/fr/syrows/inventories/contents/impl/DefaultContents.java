package fr.syrows.inventories.contents.impl;

import fr.syrows.inventories.contents.InventoryContents;
import fr.syrows.inventories.interfaces.AdvancedInventory;

public class DefaultContents extends InventoryContents {

    private AdvancedInventory inventory;

    public DefaultContents(AdvancedInventory inventory) {
        super(inventory);
        this.inventory = inventory;
    }

    @Override
    public AdvancedInventory getInventory() {
        return this.inventory;
    }
}
