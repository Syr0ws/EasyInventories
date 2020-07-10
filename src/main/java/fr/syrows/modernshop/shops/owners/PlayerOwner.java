package fr.syrows.modernshop.shops.owners;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.UUID;

public class PlayerOwner implements ShopOwner {

    private UUID uuid;

    public PlayerOwner(UUID uuid) {
        this.uuid = uuid;
    }

    @Override
    public UUID getUUID() {
        return this.uuid;
    }

    @Override
    public Player getPlayer() {
        return Bukkit.getPlayer(this.uuid);
    }

    @Override
    public OfflinePlayer getOfflinePlayer() {
        return Bukkit.getOfflinePlayer(this.uuid);
    }

    @Override
    public boolean isNull() {
        return false;
    }

    @Override
    public Class<? extends ShopOwner> getType() {
        return PlayerOwner.class;
    }
}
