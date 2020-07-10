package fr.syrows.modernshop.shops.owners;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.UUID;

public class NullOwner implements ShopOwner {

    @Override
    public UUID getUUID() {
        return null;
    }

    @Override
    public Player getPlayer() {
        return null;
    }

    @Override
    public OfflinePlayer getOfflinePlayer() {
        return null;
    }

    @Override
    public boolean isNull() {
        return true;
    }

    @Override
    public Class<NullOwner> getType() {
        return NullOwner.class;
    }
}
