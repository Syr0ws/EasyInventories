package fr.syrows.inventories.tools.iterators;

import fr.syrows.inventories.tools.iterators.impl.HorizontalIterator;
import fr.syrows.inventories.tools.iterators.impl.VerticalIterator;

public enum IteratorType {

    HORIZONTAL(HorizontalIterator.class), VERTICAL(VerticalIterator.class);

    private Class<? extends SlotIterator> type;

    IteratorType(Class<? extends SlotIterator> type) {
        this.type = type;
    }

    public Class<? extends SlotIterator> getType() {
        return this.type;
    }
}
