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

    public void updatePaginationContent() {
        this.updatePageContents();
        this.updatePageItems();
    }

    public void updatePageContents() {

        SlotIterator iterator = this.getPaginationIterator();

        PageableInventory<T> inventory = this.getInventory();

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

    @Override
    @SuppressWarnings("unchecked")
    protected PageableInventory<T> getInventory() {
        return (PageableInventory<T>) super.getInventory();
    }
}
