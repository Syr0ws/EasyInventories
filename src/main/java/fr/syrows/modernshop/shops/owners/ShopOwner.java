package fr.syrows.modernshop.shops.owners;

import fr.syrows.modernshop.utils.converters.Convertible;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.UUID;

public interface ShopOwner extends Convertible<ShopOwner> {

    NullOwner NULL_OWNER = new NullOwner();

    UUID getUUID();

    Player getPlayer();

    OfflinePlayer getOfflinePlayer();

    boolean isNull();
}
