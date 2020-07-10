package fr.syrows.modernshop.items;

import fr.syrows.modernshop.utils.Version;

public class CustomItemFactory {

    public static CustomItem getInstance() {
        return Version.isLegacy() ? new CustomItemLegacy() : new CustomItem();
    }
}
