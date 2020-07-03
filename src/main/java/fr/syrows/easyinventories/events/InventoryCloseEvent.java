package fr.syrows.easyinventories.events;

import fr.syrows.easyinventories.inventories.SimpleInventory;
import fr.syrows.easyinventories.tools.CloseReason;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class InventoryCloseEvent extends Event implements InventoryEvent {

    private static final HandlerList HANDLERS = new HandlerList();

    private Player player;
    private SimpleInventory inventory;
    private CloseReason reason;

    public InventoryCloseEvent(Player player, SimpleInventory inventory, CloseReason reason) {
        this.player = player;
        this.inventory = inventory;
        this.reason = reason;
    }

    public Player getPlayer() {
        return this.player;
    }

    public CloseReason getReason() {
        return this.reason;
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
