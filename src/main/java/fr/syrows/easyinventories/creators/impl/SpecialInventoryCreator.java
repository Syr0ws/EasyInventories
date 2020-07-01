package fr.syrows.easyinventories.creators.impl;

import fr.syrows.easyinventories.contents.InventorySort;
import fr.syrows.easyinventories.utils.Utils;
import fr.syrows.easyinventories.creators.InventoryCreator;
import fr.syrows.easyinventories.inventories.AdvancedInventory;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;

import java.util.Arrays;
import java.util.List;

import static fr.syrows.easyinventories.contents.InventorySort.*;

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
