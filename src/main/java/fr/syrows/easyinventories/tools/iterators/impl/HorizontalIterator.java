package fr.syrows.easyinventories.tools.iterators.impl;

import fr.syrows.easyinventories.inventories.SimpleInventory;
import fr.syrows.easyinventories.tools.iterators.SlotIterator;

import java.util.NoSuchElementException;

public class HorizontalIterator extends SlotIterator {

    public HorizontalIterator(SimpleInventory inventory, int beginRow, int beginColumn, int endRow, int endColumn) {
        super(inventory, beginRow, beginColumn, endRow, endColumn);
    }

    public HorizontalIterator(SimpleInventory inventory, int beginRow, int beginColumn, int endRow, int endColumn, int[] blacklisted) {
        super(inventory, beginRow, beginColumn, endRow, endColumn, blacklisted);
    }

    @Override
    public Slot next() {

        if(!super.hasNext())
            throw new NoSuchElementException("No element found.");

        if(super.column >= super.getEndColumn()) {

            super.row++;
            super.column = super.getBeginColumn();
        }
        Slot slot = super.get(super.row, super.column++);

        return super.isBlacklisted(slot.getSlot()) ? this.next() : slot;
    }

    @Override
    public Slot previous() {

        if(!super.hasPrevious())
            throw new NoSuchElementException("No element found.");

        if(super.column == super.getBeginColumn() || super.column >= super.getEndColumn()) {

            super.row--;
            super.column = super.getEndColumn();
        }
        Slot slot =  super.get(super.row, --super.column);

        return super.isBlacklisted(slot.getSlot()) ? this.previous() : slot;
    }
}
