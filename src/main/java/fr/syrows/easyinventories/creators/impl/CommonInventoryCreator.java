package fr.syrows.easyinventories.creators.impl;

import fr.syrows.easyinventories.contents.ContainerType;
import fr.syrows.easyinventories.utils.Utils;
import fr.syrows.easyinventories.creators.InventoryCreator;
import fr.syrows.easyinventories.inventories.SimpleInventory;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;

import java.util.Arrays;
import java.util.List;

import static fr.syrows.easyinventories.contents.ContainerType.*;

public class CommonInventoryCreator implements InventoryCreator {

    private static final List<ContainerType> supported = Arrays.asList(ENDER_CHEST, SHULKER_BOX, DROPPER, DISPENSER, HOPPER);

    @Override
    public Inventory getInventory(SimpleInventory inventory) {

        String title = Utils.parseColors(inventory.getTitle());

        ContainerType sort = inventory.getType();

        return Bukkit.createInventory(null, sort.getType(), title);
    }

    @Override
    public boolean isSupported(ContainerType sort) {
        return CommonInventoryCreator.supported.contains(sort);
    }
}
