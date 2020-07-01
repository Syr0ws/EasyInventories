package fr.syrows.easyinventories.creators.impl;

import fr.syrows.easyinventories.contents.InventorySort;
import fr.syrows.easyinventories.utils.Utils;
import fr.syrows.easyinventories.creators.InventoryCreator;
import fr.syrows.easyinventories.inventories.AdvancedInventory;
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
