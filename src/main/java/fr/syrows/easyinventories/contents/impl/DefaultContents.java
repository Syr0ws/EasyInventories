package fr.syrows.easyinventories.contents.impl;

import fr.syrows.easyinventories.contents.InventoryContents;
import fr.syrows.easyinventories.inventories.AdvancedInventory;

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
