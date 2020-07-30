package fr.syrows.easyinventories.inventories.impl;

import fr.syrows.easyinventories.inventories.SimpleInventory;
import fr.syrows.easyinventories.listeners.InventoryListenerManager;
import fr.syrows.easyinventories.manager.InventoryManager;
import fr.syrows.easyinventories.tools.CloseReason;
import fr.syrows.easyinventories.utils.SlotUtils;
import org.bukkit.entity.Player;

public abstract class AbstractInventory implements SimpleInventory {

    private InventoryManager inventoryManager;
    private InventoryListenerManager listenerManager;

    public AbstractInventory(InventoryManager manager) {
        this.inventoryManager = manager;
        this.listenerManager = new InventoryListenerManager();
    }

    @Override
    public void open(Player player) {
        this.getInventoryManager().openInventory(player, this);
    }

    @Override
    public void close(Player player, CloseReason reason) {
        this.getInventoryManager().closeInventory(player, reason);
    }

    @Override
    public int getRows() {
        return SlotUtils.getRow(this.getContainer(), this.getSize());
    }

    @Override
    public int getColumns() {
        return getContainer().getDefaultColumns();
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
