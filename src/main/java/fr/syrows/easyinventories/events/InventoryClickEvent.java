package fr.syrows.easyinventories.events;

import fr.syrows.easyinventories.inventories.SimpleInventory;
import org.bukkit.event.HandlerList;

public class InventoryClickEvent extends org.bukkit.event.inventory.InventoryClickEvent implements InventoryEvent {

    private static final HandlerList HANDLERS = new HandlerList();

    private SimpleInventory inventory;

    public InventoryClickEvent(org.bukkit.event.inventory.InventoryClickEvent event, SimpleInventory inventory) {
        super(event.getView(), event.getSlotType(), event.getSlot(), event.getClick(), event.getAction());
        this.inventory = inventory;
    }

    @Override
    public SimpleInventory getSimpleInventory() {
        return this.inventory;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
