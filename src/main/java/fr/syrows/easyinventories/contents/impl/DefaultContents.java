package fr.syrows.easyinventories.contents.impl;

import fr.syrows.easyinventories.contents.InventoryContents;
import fr.syrows.easyinventories.inventories.SimpleInventory;

public class DefaultContents extends InventoryContents {

    private SimpleInventory inventory;

    public DefaultContents(SimpleInventory inventory) {
        super(inventory);
        this.inventory = inventory;
    }

    @Override
    public SimpleInventory getInventory() {
        return this.inventory;
    }
}
