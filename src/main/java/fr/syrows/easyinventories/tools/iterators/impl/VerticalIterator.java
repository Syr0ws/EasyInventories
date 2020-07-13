package fr.syrows.easyinventories.tools.iterators.impl;

import fr.syrows.easyinventories.inventories.SimpleInventory;
import fr.syrows.easyinventories.tools.iterators.SlotIterator;

public class VerticalIterator extends SlotIterator {

    public VerticalIterator(SimpleInventory inventory, int beginRow, int beginColumn, int endRow, int endColumn) {
        super(inventory, beginRow, beginColumn, endRow, endColumn);
    }

    public VerticalIterator(SimpleInventory inventory, int beginRow, int beginColumn, int endRow, int endColumn, int[] blacklisted) {
        super(inventory, beginRow, beginColumn, endRow, endColumn, blacklisted);
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
