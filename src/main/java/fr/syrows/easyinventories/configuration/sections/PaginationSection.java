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

package fr.syrows.easyinventories.configuration.sections;

import fr.syrows.easyinventories.contents.pagination.PageItem;
import fr.syrows.easyinventories.contents.pagination.PaginationSettings;
import fr.syrows.easyinventories.inventories.impl.PageableInventory;

public interface PaginationSection {

    /**
     * Get the requested PageItem by path.
     *
     * @param inventory the inventory in which the item will be set.
     * @param type the type of page of the item.
     * @param replacement If true, the item which will be used is the one when there is no previous/next page.
     *                    If false, the item which will be used is the one when there is a previous/next page.
     *
     * @return the requested PageItem.
     */
    PageItem getPageItem(PageableInventory<?> inventory, PageItem.PageType type, boolean replacement);

    /**
     * Get the requested PaginationSettings by path.
     *
     * @return the requested PaginationSettings.
     * @throws NullPointerException if the path was found.
     */
    PaginationSettings getSettings();
}
