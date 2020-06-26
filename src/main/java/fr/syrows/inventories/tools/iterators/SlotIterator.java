package fr.syrows.inventories.tools.iterators;

import fr.syrows.inventories.InventorySort;
import fr.syrows.inventories.contents.items.ClickableItem;
import fr.syrows.inventories.interfaces.EasyInventory;
import fr.syrows.inventories.tools.slots.SlotValidator;
import fr.syrows.utils.SlotUtils;

import java.util.Optional;

public abstract class SlotIterator {

    private EasyInventory inventory;
    private int beginRow, beginColumn, endRow, endColumn;

    protected int row, column;

    public SlotIterator(EasyInventory inventory, int beginRow, int beginColumn, int endRow, int endColumn) {

        this.inventory = inventory;

        SlotValidator.validateRow(inventory, beginRow);
        SlotValidator.validateRow(inventory, endRow);

        SlotValidator.validateColumn(inventory, beginColumn);
        SlotValidator.validateColumn(inventory, endColumn);

        this.beginRow = beginRow - 1;
        this.beginColumn = beginColumn - 1;

        this.endRow = endRow;
        this.endColumn = endColumn;

        this.start();
    }

    public abstract Slot next();

    public abstract Slot previous();

    public void start() {
        this.row = this.beginRow;
        this.column = this.beginColumn;
    }

    public void end() {
        this.row = this.endRow;
        this.column = this.endColumn;
    }

    public boolean hasNext() {

        if (this.row >= this.endRow) return false;

        return this.column < this.endColumn|| (this.row != this.endRow - 1);
    }

    public boolean hasPrevious() {
        return this.row > this.beginRow || this.column > this.beginColumn;
    }

    protected Slot get(int row, int column) {
        return new Slot(row + 1, column + 1);
    }

    public EasyInventory getInventory() {
        return this.inventory;
    }

    public int getBeginRow() {
        return this.beginRow;
    }

    public int getBeginColumn() {
        return this.beginColumn;
    }

    public int getEndRow() {
        return this.endRow;
    }

    public int getEndColumn() {
        return this.endColumn;
    }

    public class Slot {

        private int row, column;

        public Slot(int row, int column) {
            this.row = row;
            this.column = column;
        }

        public int getRow() {
            return this.row;
        }

        public int getColumn() {
            return this.column;
        }

        public int getSlot() {

            InventorySort sort = SlotIterator.this.inventory.getSort();

            return SlotUtils.getSlot(sort, this.row, this.column);
        }

        public Optional<ClickableItem> getItem() {
            return SlotIterator.this.inventory.getContents().getItem(this.row, this.column);
        }
    }
}
