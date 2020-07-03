package fr.syrows.easyinventories.events;

import fr.syrows.easyinventories.inventories.SimpleInventory;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class InventoryOpenEvent extends Event implements InventoryEvent {

    private static final HandlerList HANDLERS = new HandlerList();

    private Player player;
    private SimpleInventory inventory;

    public InventoryOpenEvent(Player player, SimpleInventory inventory) {
        this.player = player;
        this.inventory = inventory;
    }

    public Player getPlayer() {
        return this.player;
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
