package fr.syrows.easyinventories.inventories.impl;

import fr.syrows.easyinventories.contents.InventoryManager;
import fr.syrows.easyinventories.inventories.SimpleInventory;
import fr.syrows.easyinventories.listeners.InventoryListenerManager;
import fr.syrows.easyinventories.utils.SlotUtils;

public abstract class EasyInventory implements SimpleInventory {

    private InventoryManager inventoryManager;
    private InventoryListenerManager listenerManager;

    public EasyInventory(InventoryManager manager) {
        this.inventoryManager = manager;
        this.listenerManager = new InventoryListenerManager();
    }

    @Override
    public int getRows() {
        return SlotUtils.getRow(this.getType(), this.getSize());
    }

    @Override
    public int getColumns() {
        return getType().getColumns();
    }

    @Override
    public InventoryManager getInventoryManager() {
        return this.inventoryManager;
    }

    @Override
    public InventoryListenerManager getListenerManager() {
        return this.listenerManager;
    }
}
