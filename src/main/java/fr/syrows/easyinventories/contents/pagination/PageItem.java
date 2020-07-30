package fr.syrows.easyinventories.contents.pagination;

import fr.syrows.easyinventories.contents.items.ClickableItem;
import fr.syrows.easyinventories.inventories.impl.PageableInventory;
import fr.syrows.easyinventories.tools.SlotValidator;
import fr.syrows.easyinventories.utils.SlotUtils;
import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;

public class PageItem {

    private PageableInventory<?> container;
    private PageType type;

    private ItemStack stack;
    private int slot;

    public PageItem(PageableInventory<?> container, PageType type, ItemStack stack, int slot) {

        SlotValidator.validateSlot(container, slot);

        this.container = container;
        this.type = type;
        this.stack = stack;
        this.slot = slot;
    }

    public PageItem(PageableInventory<?> container, PageType type, ItemStack stack, int row, int column) {

        SlotValidator.validateRow(container, row);
        SlotValidator.validateColumn(container, column);

        this.container = container;
        this.type = type;
        this.stack = stack;
        this.slot = SlotUtils.getSlot(container.getContainer(), row, column);
    }

    public ClickableItem getItem() {

        String identifier = this.type.getIdentifier();
        Consumer<PageableInventory<?>> consumer = this.type.getConsumer();

        return new ClickableItem.Builder(identifier, this.stack).withClickEvent((event) -> consumer.accept(this.container)).build();
    }

    public int getSlot() {
        return this.slot;
    }

    public enum PageType {

        PREVIOUS("previous_page", PageableInventory::previousPage),
        NEXT("next_page", PageableInventory::nextPage);

        private String identifier;
        private Consumer<PageableInventory<?>> consumer;

        PageType(String identifier, Consumer<PageableInventory<?>> consumer) {
            this.identifier = identifier;
            this.consumer = consumer;
        }

        public String getIdentifier() {
            return this.identifier;
        }

        public Consumer<PageableInventory<?>> getConsumer() {
            return this.consumer;
        }
    }
}
