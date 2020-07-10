package fr.syrows.modernshop.utils.parsers;

import fr.syrows.modernshop.shops.Shop;
import fr.syrows.modernshop.shops.ShopLocation;
import fr.syrows.modernshop.shops.owners.ShopOwner;

public class ShopMessageParser {

    private Shop shop;
    private String message;

    public ShopMessageParser(Shop shop, String message) {
        this.shop = shop;
        this.message = message;
    }

    public ShopMessageParser parseOwner() {

        ShopOwner owner = this.shop.getOwner();

        if(owner.isNull())
            throw new UnsupportedOperationException("Shop does not have owner.");

        this.message = this.message.replace("%owner_name%", owner.getOfflinePlayer().getName());

        return this;
    }

    public ShopMessageParser parseLocation() {

        ShopLocation location = this.shop.getLocation();

        this.message = this.message.replace("%shop_world%", location.getWorldName())
                .replace("%shop_x%", Integer.toString(location.getX()))
                .replace("%shop_y%", Integer.toString(location.getY()))
                .replace("%shop_z%", Integer.toString(location.getZ()));

        return this;
    }

    public String get() {
        return MessageParser.parseColors(this.message);
    }
}
