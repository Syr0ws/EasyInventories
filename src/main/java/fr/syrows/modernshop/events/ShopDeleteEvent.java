package fr.syrows.modernshop.events;

import fr.syrows.modernshop.shops.Shop;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;

public class ShopDeleteEvent extends ShopEvent implements Cancellable {

    private Player player;
    private boolean cancelled;

    public ShopDeleteEvent(Player player, Shop shop) {
        super(shop);
        this.player = player;
    }

    public Player getPlayer() {
        return this.player;
    }

    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}
