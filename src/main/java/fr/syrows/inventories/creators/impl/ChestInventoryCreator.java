package fr.syrows.inventories.creators.impl;

import fr.syrows.inventories.InventorySort;
import fr.syrows.inventories.utils.Utils;
import fr.syrows.inventories.creators.InventoryCreator;
import fr.syrows.inventories.interfaces.AdvancedInventory;
import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

public class ChestInventoryCreator implements InventoryCreator {

    @Override
    public Inventory getInventory(AdvancedInventory inventory) {

        String title = Utils.parseColors(inventory.getTitle());

        int size = inventory.getSize();

        return Bukkit.createInventory(null, size, title);
    }

    @Override
    public boolean isSupported(InventorySort sort) {
        return sort.getType() == InventoryType.CHEST;
    }
}
