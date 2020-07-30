package fr.syrows.easyinventories.inventories.impl;

import fr.syrows.easyinventories.contents.PageableInventoryContents;
import fr.syrows.easyinventories.manager.InventoryManager;
import fr.syrows.easyinventories.contents.pagination.Pageable;
import fr.syrows.easyinventories.contents.pagination.Pagination;
import fr.syrows.easyinventories.contents.pagination.PaginationSettings;
import fr.syrows.easyinventories.tools.InventoryValidator;
import org.bukkit.entity.Player;

public abstract class PageableInventory<T> extends AbstractInventory implements Pageable<T> {

    private Pagination<T>.Page opened;

    public PageableInventory(InventoryManager manager) {
        super(manager);
    }

    @Override
    public abstract PageableInventoryContents<T> getContents();

    @Override
    public void nextPage() {

        Pagination<T> pagination = this.getPagination();
        Pagination<T>.Page opened = this.getOpenedPage();

        if(pagination.hasNext(opened)) this.openPage(pagination.getNext(opened));
    }

    @Override
    public void previousPage() {

        Pagination<T> pagination = this.getPagination();
        Pagination<T>.Page opened = this.getOpenedPage();

        if(pagination.hasPrevious(opened)) this.openPage(pagination.getPrevious(opened));
    }

    @Override
    public void openPage(Pagination<T>.Page page) {

        this.opened = page;

        PageableInventoryContents<T> contents = this.getContents();

        contents.updatePageContents();
        contents.updatePageItems();
    }

    @Override
    public void open(Player player) {

        this.validate();

        Pagination<T> pagination = this.getPagination();
        Pagination<T>.Page toOpen = this.opened == null ? pagination.getFirst() : this.opened;

        this.openPage(toOpen);

        super.open(player);
    }

    private void validate() {

        InventoryValidator.validateInventory(this);

        PaginationSettings settings = this.getSettings();
        Pagination<T> pagination = this.getPagination();

        if(pagination.getElementsPerPage() != settings.countSlots())
            throw new UnsupportedOperationException("Incoherence between number of elements per page and allocated slots.");
    }
}
