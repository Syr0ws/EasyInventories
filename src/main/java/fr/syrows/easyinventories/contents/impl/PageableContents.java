package fr.syrows.easyinventories.contents.impl;

import fr.syrows.easyinventories.contents.InventoryContents;
import fr.syrows.easyinventories.contents.items.ClickableItem;
import fr.syrows.easyinventories.contents.items.PageItem;
import fr.syrows.easyinventories.inventories.PageableInventory;
import fr.syrows.easyinventories.tools.iterators.IteratorType;
import fr.syrows.easyinventories.tools.iterators.SlotIterator;
import fr.syrows.easyinventories.tools.iterators.SlotIteratorFactory;
import fr.syrows.easyinventories.tools.pagination.PaginationSettings;

import java.util.List;

public class PageableContents<T> extends InventoryContents {

    private PageableInventory<T> inventory;

    public PageableContents(PageableInventory<T> inventory) {
        super(inventory);
        this.inventory = inventory;
    }

    public void updatePageContents() {

        PageableInventory<T> inventory = this.getInventory();

        PaginationSettings settings = inventory.getSettings();

        SlotIterator iterator = SlotIteratorFactory.getIterator(IteratorType.HORIZONTAL,
                this.inventory,
                settings.getBeginRow(),
                settings.getBeginColumn(),
                settings.getEndRow(),
                settings.getEndColumn(),
                settings.getBlacklisted());

        List<T> elements = inventory.getOpenedPage().getElements();

        int index = 0;
        while(iterator.hasNext()) {

            SlotIterator.Slot slot = iterator.next();

            ClickableItem item;

            if(index < elements.size()) {

                T element = elements.get(index);

                item = inventory.getPageItem(element);

            } else item = null;

            super.setItem(slot.getSlot(), item);

            index++;
        }
    }

    public void updatePageItems() {

        PageableInventory<T> inventory = this.getInventory();

        PageItem previous = inventory.getPreviousPage(), next = inventory.getNextPage();
        ClickableItem previousItem = previous.getItem(), nextItem = next.getItem();

        previousItem.update();
        nextItem.update();

        super.setItem(previous.getSlot(), previousItem);
        super.setItem(next.getSlot(), nextItem);
    }

    @Override
    public PageableInventory<T> getInventory() {
        return this.inventory;
    }
}
