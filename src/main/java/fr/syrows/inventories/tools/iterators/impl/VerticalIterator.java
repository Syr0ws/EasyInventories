package fr.syrows.inventories.tools.iterators.impl;

import fr.syrows.inventories.interfaces.EasyInventory;
import fr.syrows.inventories.tools.iterators.SlotIterator;

public class VerticalIterator extends SlotIterator {

    public VerticalIterator(EasyInventory inventory, int beginRow, int beginColumn, int endRow, int endColumn) {
        super(inventory, beginRow, beginColumn, endRow, endColumn);
    }

    @Override
    public Slot next() {
        return null;
    }

    @Override
    public Slot previous() {
        return null;
    }
}
