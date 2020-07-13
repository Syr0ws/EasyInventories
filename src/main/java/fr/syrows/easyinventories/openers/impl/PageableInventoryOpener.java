package fr.syrows.easyinventories.openers.impl;

import fr.syrows.easyinventories.inventories.PageableInventory;
import fr.syrows.easyinventories.openers.InventoryOpener;
import fr.syrows.easyinventories.tools.pagination.Pagination;
import fr.syrows.easyinventories.tools.pagination.PaginationSettings;
import fr.syrows.easyinventories.tools.validators.InventoryValidator;
import org.bukkit.entity.Player;

public class PageableInventoryOpener<T> implements InventoryOpener<PageableInventory<T>> {

    @Override
    public void open(Player player, PageableInventory<T> inventory) {

        this.validate(inventory);

        Pagination<T> pagination = inventory.getPagination();
        Pagination<T>.Page toOpen = inventory.getOpenedPage() == null ? pagination.getFirst() : inventory.getOpenedPage();

        inventory.openPage(toOpen);

        player.openInventory(inventory.getInventory());
    }

    private void validate(PageableInventory<T> inventory) {

        InventoryValidator.validateInventory(inventory);

        PaginationSettings settings = inventory.getSettings();
        Pagination<T> pagination = inventory.getPagination();

        if(pagination.getElementsPerPage() != settings.countSlots())
            throw new UnsupportedOperationException("Incoherence between number of elements per page and allocated slots.");
    }
}
