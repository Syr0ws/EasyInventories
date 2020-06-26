package fr.syrows.inventories.contents;

import fr.syrows.inventories.contents.items.ClickableItem;
import fr.syrows.inventories.contents.items.PageItem;
import fr.syrows.inventories.interfaces.PageableInventory;
import fr.syrows.inventories.tools.pagination.PaginationSettings;
import fr.syrows.inventories.tools.slots.SlotValidator;

import java.util.List;

public class PageableContents<T> extends InventoryContents<PageableInventory<T>> {

    public PageableContents(PageableInventory<T> inventory) {
        super(inventory);
    }

    public void updatePageContents() {

        PageableInventory<T> inventory = super.getInventory();

        PaginationSettings settings = super.getInventory().getSettings();

        int begin = settings.getBeginSlot(), end = settings.getEndSlot();

        List<T> elements = inventory.getOpenedPage().getElements();

        for(int slot = begin, index = 0; slot <= end; slot++, index++) {

            ClickableItem item;

            if(index < elements.size()) {

                T element = elements.get(index);

                item = super.getInventory().getPageItem(element);

            } else item = null;

            super.setItem(slot, item);
        }
    }

    public void updatePageContent(int slot) {

        PageableInventory<T> inventory = super.getInventory();

        SlotValidator.validateSlot(inventory, slot);

        PaginationSettings settings = super.getInventory().getSettings();

        if(!this.isPageSlot(slot))
            throw new IllegalArgumentException(String.format("Slot %d is not a pageable slot.", slot));

        List<T> elements = inventory.getOpenedPage().getElements();

        T element = elements.get(slot - settings.getBeginSlot());

        ClickableItem item = inventory.getPageItem(element);

        super.setItem(slot, item);
    }

    public void updatePageItems() {

        PageableInventory<T> inventory = super.getInventory();

        PageItem previous = inventory.getPreviousPage(), next = inventory.getNextPage();
        ClickableItem previousItem = previous.getItem(), nextItem = next.getItem();

        super.setItem(previous.getSlot(), previousItem);
        super.setItem(next.getSlot(), nextItem);
    }

    @Override
    public void refresh() {

        this.updatePageContents();
        this.updatePageItems();

        super.refresh();
    }

    private boolean isPageSlot(int slot) {

        PaginationSettings settings = super.getInventory().getSettings();

        return slot >= settings.getBeginSlot() && slot <= settings.getEndSlot();
    }
}
