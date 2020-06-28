package fr.syrows.inventories.tools.iterators.impl;

import fr.syrows.inventories.interfaces.AdvancedInventory;
import fr.syrows.inventories.tools.iterators.SlotIterator;

public class VerticalIterator extends SlotIterator {

    public VerticalIterator(AdvancedInventory inventory, int beginRow, int beginColumn, int endRow, int endColumn) {
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
