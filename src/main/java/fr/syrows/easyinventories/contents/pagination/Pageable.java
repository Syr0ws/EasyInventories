package fr.syrows.easyinventories.contents.pagination;

import fr.syrows.easyinventories.contents.items.ClickableItem;

public interface Pageable<T> {

    PageItem getNextPage();

    PageItem getPreviousPage();

    PaginationSettings getSettings();

    Pagination<T> getPagination();

    Pagination<T>.Page getOpenedPage();

    ClickableItem getPageItem(T element);

    void openPage(Pagination<T>.Page page);

    void nextPage();

    void previousPage();
}
