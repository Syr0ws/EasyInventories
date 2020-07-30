package fr.syrows.easyinventories.iterators;

import fr.syrows.easyinventories.iterators.impl.HorizontalIterator;
import fr.syrows.easyinventories.iterators.impl.VerticalIterator;

public enum SlotIteratorEnum implements SlotIteratorType {

    HORIZONTAL(HorizontalIterator.class), VERTICAL(VerticalIterator.class);

    private Class<? extends SlotIterator> type;

    SlotIteratorEnum(Class<? extends SlotIterator> type) {
        this.type = type;
    }

    public Class<? extends SlotIterator> getIteratorClass() {
        return this.type;
    }
}
