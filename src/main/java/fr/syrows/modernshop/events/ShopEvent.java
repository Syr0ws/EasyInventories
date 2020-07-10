package fr.syrows.modernshop.events;

import fr.syrows.modernshop.shops.Shop;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class ShopEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();

    private Shop shop;

    public ShopEvent(Shop shop) {
        this.shop = shop;
    }

    public Shop getShop() {
        return this.shop;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
