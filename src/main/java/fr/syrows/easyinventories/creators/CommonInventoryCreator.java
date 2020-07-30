package fr.syrows.easyinventories.creators;

import fr.syrows.easyinventories.contents.containers.InventorySort;
import fr.syrows.easyinventories.inventories.SimpleInventory;
import fr.syrows.easyinventories.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;

import java.util.Arrays;
import java.util.List;

import static fr.syrows.easyinventories.contents.containers.InventorySortType.*;

public class CommonInventoryCreator implements InventoryCreator {

    private static final List<InventorySort> supported = Arrays.asList(ENDER_CHEST, SHULKER_BOX, DROPPER, DISPENSER, HOPPER);

    @Override
    public Inventory getInventory(SimpleInventory inventory) {

        String title = Utils.parseColors(inventory.getTitle());

        InventorySort sort = inventory.getContainer();

        return Bukkit.createInventory(null, sort.getInventoryType(), title);
    }

    @Override
    public boolean isSupported(InventorySort sort) {
        return CommonInventoryCreator.supported.contains(sort);
    }
}
