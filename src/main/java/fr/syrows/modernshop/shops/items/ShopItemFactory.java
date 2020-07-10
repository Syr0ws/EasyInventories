package fr.syrows.modernshop.shops.items;

import fr.syrows.modernshop.utils.Version;
import org.bukkit.inventory.ItemStack;

public class ShopItemFactory {

    public static ShopItem getInstance() {
        return Version.IS_LEGACY_VERSION ? new ShopItemLegacy() : new ShopItem();
    }

    public static ShopItem getInstance(ItemStack stack) {
        return Version.IS_LEGACY_VERSION ? new ShopItemLegacy(stack) : new ShopItem(stack);
    }
}
