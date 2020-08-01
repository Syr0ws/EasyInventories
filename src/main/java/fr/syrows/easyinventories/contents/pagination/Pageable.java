/*
 * Copyright 2020 Syr0ws
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package fr.syrows.easyinventories.contents.pagination;

import fr.syrows.easyinventories.contents.items.ClickableItem;

public interface Pageable<T> {

    /**
     * An item which will be used to go to the next page.
     *
     * @return an object of type PageItem.
     */
    PageItem getNextPage();

    /**
     * An item which will be used to go to the previous page.
     *
     * @return an object of type PageItem.
     */
    PageItem getPreviousPage();

    /**
     * Returns the pagination settings for the current object.
     *
     * @return an object of type PaginationSettings.
     */
    PaginationSettings getSettings();

    /**
     * Returns the Pagination object used.
     *
     * @return an object of type Pagination.
     */
    Pagination<T> getPagination();

    /**
     * Returns the opened page.
     *
     * @return an object of type Page.
     */
    Pagination<T>.Page getOpenedPage();

    /**
     * Specify the ClickableItem which represents one element of the pagination.
     * This method will be called when displaying a page and for each element
     * of the opened page.
     *
     * @param element a paginated element.
     *
     * @return a ClickableItem object that represents the element.
     */
    ClickableItem getPageItem(T element);

    /**
     * Open and display the specified page.
     * The page object must belong to the Pagination object returned by
     * the getPagination() method.
     *
     * @param page the page to open and to display.
     *
     * @throws NullPointerException if the page is null.
     */
    void openPage(Pagination<T>.Page page);

    /**
     * Open and display the next page.
     */
    void nextPage();

    /**
     * Open and display the previous page.
     */
    void previousPage();
}
