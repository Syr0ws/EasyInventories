package fr.syrows.inventories.contents;

import fr.syrows.inventories.contents.items.ClickableItem;
import fr.syrows.inventories.interfaces.EasyInventory;
import fr.syrows.inventories.tools.iterators.IteratorType;
import fr.syrows.inventories.tools.iterators.SlotIterator;
import fr.syrows.inventories.tools.iterators.SlotIteratorFactory;
import fr.syrows.inventories.tools.slots.SlotValidator;
import fr.syrows.utils.SlotUtils;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;

public class InventoryContents<T extends EasyInventory> {

    private T inventory;
    private ClickableItem[][] contents;

    public InventoryContents(T inventory) {

        int rows = inventory.getRows(), columns = inventory.getColumns();

        this.inventory = inventory;
        this.contents = new ClickableItem[rows][columns];
    }

    public void setItem(int slot, ClickableItem item) {

        SlotValidator.validateSlot(this.inventory, slot);

        int row = SlotUtils.getRow(this.inventory.getSort(), slot);
        int column = SlotUtils.getColumn(this.inventory.getSort(), slot);

        this.contents[row][column] = item;
        this.update(slot);
    }

    public void setItem(int row, int column, ClickableItem item) {

        SlotValidator.validateRow(this.inventory, row);
        SlotValidator.validateColumn(this.inventory, column);

        this.contents[row - 1][column - 1] = item;
        this.update(row, column);
    }

    public void setItems(ClickableItem item, int... slots) {

        if(slots.length == 0)
            throw new IllegalArgumentException("No slots specified.");

        for(int slot : slots) this.setItem(slot, item);
    }

    public Optional<ClickableItem> getItem(int slot) {

        SlotValidator.validateSlot(this.inventory, slot);

        int row = SlotUtils.getRow(this.inventory.getSort(), slot);
        int column = SlotUtils.getColumn(this.inventory.getSort(), slot);

        ClickableItem item = this.contents[row][column];

        return item == null ? Optional.empty() : Optional.of(item);
    }

    public Optional<ClickableItem> getItem(int row, int column) {

        SlotValidator.validateRow(this.inventory, row);
        SlotValidator.validateColumn(this.inventory, column);

        ClickableItem item =  this.contents[row - 1][column - 1];

        return item == null ? Optional.empty() : Optional.of(item);
    }

    public void refresh() {

        SlotIterator iterator = SlotIteratorFactory.getIterator(
                IteratorType.HORIZONTAL,
                this.inventory, 1, 1,
                this.inventory.getRows(),
                this.inventory.getColumns());

        while(iterator.hasNext()) {

            SlotIterator.Slot slot = iterator.next();

            this.update(slot.getSlot());
        }
    }

    private void update(int slot) {

        Optional<ClickableItem> optional = this.getItem(slot);

        this.inventory.getInventory().setItem(slot, optional.map(ClickableItem::getItemStack).orElse(null));
    }

    private void update(int row, int column) {

        int slot = SlotUtils.getSlot(this.inventory.getSort(), row, column);

        Optional<ClickableItem> optional = this.getItem(slot);

        ItemStack stack = optional.map(ClickableItem::getItemStack).orElse(null);

        this.inventory.getInventory().setItem(slot, stack);
    }

    public T getInventory() {
        return this.inventory;
    }
}
