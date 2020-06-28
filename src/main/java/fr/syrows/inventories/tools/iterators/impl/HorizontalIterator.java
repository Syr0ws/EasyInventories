package fr.syrows.inventories.tools.iterators.impl;

import fr.syrows.inventories.interfaces.AdvancedInventory;
import fr.syrows.inventories.tools.iterators.SlotIterator;

import java.util.NoSuchElementException;

public class HorizontalIterator extends SlotIterator {

    public HorizontalIterator(AdvancedInventory inventory, int beginRow, int beginColumn, int endRow, int endColumn) {
        super(inventory, beginRow, beginColumn, endRow, endColumn);
    }

    @Override
    public Slot next() {

        if(!super.hasNext())
            throw new NoSuchElementException("No element found.");

        if(super.column >= super.getEndColumn()) {

            super.row++;
            super.column = super.getBeginColumn();
        }
        return super.get(super.row, super.column++);
    }

    @Override
    public Slot previous() {

        if(!super.hasPrevious())
            throw new NoSuchElementException("No element found.");

        if(super.column == super.getBeginColumn() || super.column >= super.getEndColumn()) {

            super.row--;
            super.column = super.getEndColumn();
        }
        return super.get(super.row, --super.column);
    }
}
