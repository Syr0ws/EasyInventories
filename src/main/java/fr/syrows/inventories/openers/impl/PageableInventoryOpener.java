package fr.syrows.inventories.openers.impl;

import fr.syrows.inventories.interfaces.PageableInventory;
import fr.syrows.inventories.openers.InventoryOpener;
import fr.syrows.inventories.tools.pagination.Pagination;
import fr.syrows.inventories.tools.validators.InventoryValidator;
import org.bukkit.entity.Player;

public class PageableInventoryOpener<T> implements InventoryOpener<PageableInventory<T>> {

    @Override
    public void open(Player player, PageableInventory<T> inventory) {

        InventoryValidator.validateInventory(inventory);

        if(inventory.getPagination().getElementsPerPage() != inventory.getSettings().countSlots())
            throw new UnsupportedOperationException("Number of elements per page must be the same than the number of allocated slots to place them.");

        Pagination<T> pagination = inventory.getPagination();
        Pagination<T>.Page first = pagination.getFirst();

        inventory.openPage(first);

        player.openInventory(inventory.getInventory());
    }
}
