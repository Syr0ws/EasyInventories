package fr.syrows.inventories.creators.impl;

import fr.syrows.inventories.InventorySort;
import fr.syrows.inventories.utils.Utils;
import fr.syrows.inventories.creators.InventoryCreator;
import fr.syrows.inventories.interfaces.AdvancedInventory;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;

import java.util.Arrays;
import java.util.List;

import static fr.syrows.inventories.InventorySort.*;

public class SpecialInventoryCreator implements InventoryCreator {

    private static final List<InventorySort> supported = Arrays.asList(DROPPER, DISPENSER, HOPPER);

    @Override
    public Inventory getInventory(AdvancedInventory inventory) {

        String title = Utils.parseColors(inventory.getTitle());

        InventorySort sort = inventory.getSort();

        return Bukkit.createInventory(null, sort.getType(), title);
    }

    @Override
    public boolean isSupported(InventorySort sort) {
        return SpecialInventoryCreator.supported.contains(sort);
    }
}
