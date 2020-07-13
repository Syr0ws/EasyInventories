package fr.syrows.easyinventories.events;

import fr.syrows.easyinventories.contents.InventoryContents;
import fr.syrows.easyinventories.contents.items.ClickableItem;
import fr.syrows.easyinventories.inventories.SimpleInventory;
import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.Optional;

public class SimpleInventoryClickEvent extends InventoryClickEvent implements SimpleInventoryEvent {

    private static final HandlerList HANDLERS = new HandlerList();

    private SimpleInventory inventory;

    public SimpleInventoryClickEvent(InventoryClickEvent event, SimpleInventory inventory) {
        super(event.getView(), event.getSlotType(), event.getSlot(), event.getClick(), event.getAction());
        this.inventory = inventory;
    }

    @Override
    public SimpleInventory getSimpleInventory() {
        return this.inventory;
    }

    public Optional<ClickableItem> getClickableItem() {

        InventoryContents contents = this.inventory.getContents();

        return contents.getItem(super.getSlot());
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
