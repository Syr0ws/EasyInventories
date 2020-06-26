package fr.syrows.inventories.interfaces;

import fr.syrows.inventories.contents.PageableContents;
import fr.syrows.inventories.contents.items.ClickableItem;
import fr.syrows.inventories.contents.items.PageItem;
import fr.syrows.inventories.tools.pagination.Pagination;
import fr.syrows.inventories.tools.pagination.PaginationSettings;
import org.bukkit.entity.Player;

public interface PageableInventory<T> extends EasyInventory {

    PageItem getNextPage();

    PageItem getPreviousPage();

    Pagination<T> getPagination();

    Pagination<T>.Page getOpenedPage();

    PaginationSettings getSettings();

    ClickableItem getPageItem(T element);

    void setOpenedPage(Pagination<T>.Page page);

    @Override
    PageableContents<T> getContents();

    default void open(Pagination<T>.Page page) {

        this.setOpenedPage(page);

        PageableContents<T> contents = this.getContents();

        contents.updatePageContents();
        contents.updatePageItems();
    }

    @Override
    default void open(Player player) {

        this.open(this.getOpenedPage());

        EasyInventory.super.open(player);
    }

    default void nextPage() {

        Pagination<T> pagination = this.getPagination();
        Pagination<T>.Page opened = this.getOpenedPage();

        if(pagination.hasNext(opened)) this.open(pagination.getNext(opened));
    }

    default void previousPage() {

        Pagination<T> pagination = this.getPagination();
        Pagination<T>.Page opened = this.getOpenedPage();

        if(pagination.hasPrevious(opened)) this.open(pagination.getPrevious(opened));
    }
}
