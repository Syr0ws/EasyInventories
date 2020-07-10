package fr.syrows.modernshop.shops.tools;

import fr.syrows.modernshop.inventories.categories.impl.ShopItemSelector;
import fr.syrows.modernshop.shops.ShopType;
import fr.syrows.modernshop.shops.items.ShopItem;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;

public class ShopCreationData {

    private Player player;
    private Sign sign;

    private ShopType type;
    private ShopItemSelector selector;

    public ShopCreationData(Player player, Sign sign, ShopType type, ShopItemSelector selector) {
        this.player = player;
        this.sign = sign;
        this.type = type;
        this.selector = selector;
    }

    public Player getPlayer() {
        return this.player;
    }

    public Sign getSign() {
        return this.sign;
    }

    public ShopType getShopType() {
        return this.type;
    }

    public ShopItem getShopItem() {
        return this.selector.getSelectedItem();
    }

    public ShopItemSelector getShopItemSelector() {
        return this.selector;
    }
}
