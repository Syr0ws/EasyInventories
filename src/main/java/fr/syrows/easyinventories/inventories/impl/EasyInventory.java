package fr.syrows.easyinventories.inventories.impl;

import fr.syrows.easyinventories.contents.InventoryManager;
import fr.syrows.easyinventories.inventories.AdvancedInventory;
import fr.syrows.easyinventories.listeners.InventoryListener;
import org.bukkit.event.inventory.InventoryEvent;

import java.util.ArrayList;
import java.util.List;

public abstract class EasyInventory implements AdvancedInventory {

    private InventoryManager manager;

    private List<InventoryListener<? extends InventoryEvent>> listeners;

    public EasyInventory(InventoryManager manager) {

        this.manager = manager;
        this.listeners = new ArrayList<>();
    }

    @Override
    public InventoryManager getManager() {
        return this.manager;
    }

    @Override
    public List<InventoryListener<? extends InventoryEvent>> getListeners() {
        return this.listeners;
    }
}
