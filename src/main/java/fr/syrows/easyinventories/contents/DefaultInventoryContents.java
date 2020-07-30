package fr.syrows.easyinventories.contents;

import fr.syrows.easyinventories.contents.items.ClickableItem;
import fr.syrows.easyinventories.inventories.SimpleInventory;
import fr.syrows.easyinventories.iterators.SlotIterator;
import fr.syrows.easyinventories.tools.SlotValidator;
import fr.syrows.easyinventories.utils.SlotUtils;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.Optional;

public class DefaultInventoryContents implements InventoryContents {

    private SimpleInventory inventory;
    private ClickableItem[][] contents;

    public DefaultInventoryContents(SimpleInventory inventory) {

        int rows = inventory.getRows(), columns = inventory.getColumns();

        this.contents = new ClickableItem[rows][columns];
        this.inventory = inventory;
    }

    @Override
    public void setItem(int slot, ClickableItem item) {

        SlotValidator.validateSlot(this.inventory, slot);

        int row = SlotUtils.getRow(this.inventory.getContainer(), slot);
        int column = SlotUtils.getColumn(this.inventory.getContainer(), slot);

        this.contents[row][column] = item;
        this.update(slot);
    }

    @Override
    public void setItem(int row, int column, ClickableItem item) {

        SlotValidator.validatePosition(this.inventory, row, column);

        this.contents[row - 1][column - 1] = item;
        this.update(row, column);
    }

    @Override
    public void setItems(ClickableItem item, int... slots) {

        if(slots.length == 0)
            throw new IllegalArgumentException("No slots specified.");

        for(int slot : slots) this.setItem(slot, item);
    }

    @Override
    public Optional<ClickableItem> getItem(int slot) {

        SlotValidator.validateSlot(this.inventory, slot);

        int row = SlotUtils.getRow(this.inventory.getContainer(), slot);
        int column = SlotUtils.getColumn(this.inventory.getContainer(), slot);

        ClickableItem item = this.contents[row][column];

        return item == null ? Optional.empty() : Optional.of(item);
    }

    @Override
    public Optional<ClickableItem> getItem(int row, int column) {

        SlotValidator.validatePosition(this.inventory, row, column);

        ClickableItem item =  this.contents[row - 1][column - 1];

        return item == null ? Optional.empty() : Optional.of(item);
    }

    @Override
    public boolean isEmpty(int slot) {
        return !this.getItem(slot).isPresent();
    }

    @Override
    public boolean isEmpty(int row, int column) {
        return !this.getItem(row, column).isPresent();
    }

    @Override
    public void update(int slot) {

        Optional<ClickableItem> optional = this.getItem(slot);
        ItemStack stack = optional.map(ClickableItem::getItemStack).orElse(null);

        this.inventory.getInventory().setItem(slot, stack);
    }

    @Override
    public void update(int row, int column) {

        int slot = SlotUtils.getSlot(this.inventory.getContainer(), row, column);

        Optional<ClickableItem> optional = this.getItem(slot);

        ItemStack stack = optional.map(ClickableItem::getItemStack).orElse(null);

        this.inventory.getInventory().setItem(slot, stack);
    }

    @Override
    public SlotIterator getContentsIterator() {
        return null;
    }

    @Override
    public ClickableItem[][] getContents() {
        return Arrays.copyOf(this.contents, this.contents.length);
    }

    protected SimpleInventory getInventory() {
        return this.inventory;
    }
}
