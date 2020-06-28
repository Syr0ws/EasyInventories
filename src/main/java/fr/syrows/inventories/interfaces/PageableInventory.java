package fr.syrows.inventories.interfaces;

import fr.syrows.inventories.contents.impl.PageableContents;
import fr.syrows.inventories.contents.items.ClickableItem;
import fr.syrows.inventories.contents.items.PageItem;
import fr.syrows.inventories.openers.impl.PageableInventoryOpener;
import fr.syrows.inventories.tools.pagination.Pagination;
import fr.syrows.inventories.tools.pagination.PaginationSettings;
import org.bukkit.entity.Player;

public interface PageableInventory<T> extends AdvancedInventory {

    PageItem getNextPage();

    PageItem getPreviousPage();

    Pagination<T> getPagination();

    Pagination<T>.Page getOpenedPage();

    PaginationSettings getSettings();

    ClickableItem getPageItem(T element);

    void setOpenedPage(Pagination<T>.Page page);

    default void openPage(Pagination<T>.Page page) {

        this.setOpenedPage(page);

        PageableContents<T> contents = this.getContents();

        contents.updatePageContents();
        contents.updatePageItems();
    }

    default void nextPage() {

        Pagination<T> pagination = this.getPagination();
        Pagination<T>.Page opened = this.getOpenedPage();

        if(pagination.hasNext(opened)) this.openPage(pagination.getNext(opened));
    }

    default void previousPage() {

        Pagination<T> pagination = this.getPagination();
        Pagination<T>.Page opened = this.getOpenedPage();

        if(pagination.hasPrevious(opened)) this.openPage(pagination.getPrevious(opened));
    }

    @Override
    PageableContents<T> getContents();

    @Override
    default void open(Player player) {

        PageableInventoryOpener<T> opener = new PageableInventoryOpener<>();

        this.getManager().open(player, this, opener);
    }
}
