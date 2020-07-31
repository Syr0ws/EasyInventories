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

package fr.syrows.easyinventories.contents;

import fr.syrows.easyinventories.contents.items.ClickableItem;
import fr.syrows.easyinventories.contents.pagination.PageItem;
import fr.syrows.easyinventories.inventories.impl.PageableInventory;
import fr.syrows.easyinventories.iterators.SlotIterator;
import fr.syrows.easyinventories.iterators.SlotIteratorEnum;
import fr.syrows.easyinventories.iterators.SlotIteratorFactory;
import fr.syrows.easyinventories.contents.pagination.PaginationSettings;

import java.util.List;

public class PageableInventoryContents<T> extends DefaultInventoryContents {

    public PageableInventoryContents(PageableInventory<T> inventory) {
        super(inventory);
    }

    /**
     * Update the page items and refresh page contents.
     */
    public void updatePagination() {
        this.updatePageContents();
        this.updatePageItems();
    }

    /**
     * Refresh the page contents by resetting all the items.
     */
    public void updatePageContents() {

        // Retrieving a SlotIterator to iterate through all the pageable slots.
        SlotIterator iterator = this.getPaginationIterator();

        PageableInventory<T> inventory = this.getInventory();

        // Retrieving the paginated objects.
        List<T> elements = inventory.getOpenedPage().getElements();

        int index = 0;
        while(iterator.hasNext()) {

            SlotIterator.Slot slot = iterator.next();

            ClickableItem item;

            // Index is lower than the number of elements of the page
            // so there is an item to add in the inventory.
            if(index < elements.size()) {

                T element = elements.get(index);

                item = inventory.getPageItem(element);

            } else item = null;

            super.setItem(slot.getSlot(), item);

            index++;
        }
    }

    /**
     * Update the items used to change page.
     */
    public void updatePageItems() {

        PageableInventory<T> inventory = this.getInventory();

        PageItem previous = inventory.getPreviousPage(), next = inventory.getNextPage();
        ClickableItem previousItem = previous.getItem(), nextItem = next.getItem();

        previousItem.update(); // Updating the previous page item.
        nextItem.update(); // Updating the next page item.

        super.setItem(previous.getSlot(), previousItem);
        super.setItem(next.getSlot(), nextItem);
    }

    /**
     * Returns a SlotIterator used to iterate through all the pageable slots.
     *
     * @return an object of type SlotIterator.
     */
    public SlotIterator getPaginationIterator() {

        PaginationSettings settings = this.getInventory().getSettings();

        return SlotIteratorFactory.getIterator(SlotIteratorEnum.HORIZONTAL,
                this.getInventory(),
                settings.getBeginRow(),
                settings.getBeginColumn(),
                settings.getEndRow(),
                settings.getEndColumn(),
                settings.getBlacklisted());
    }

    /**
     * Retrieve the stored inventory as a PageableInventory<T>.
     *
     * @return the stored inventory as a PageableInventory<T>.
     */
    @Override
    @SuppressWarnings("unchecked")
    protected PageableInventory<T> getInventory() {
        return (PageableInventory<T>) super.getInventory();
    }
}
