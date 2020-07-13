package fr.syrows.easyinventories.tools.iterators;

import fr.syrows.easyinventories.inventories.SimpleInventory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class SlotIteratorFactory {

    public static SlotIterator getIterator(IteratorType type, SimpleInventory inventory, int beginRow, int beginColumn, int endRow, int endColumn) {

        Class<? extends SlotIterator> iteratorClass = type.getType();

        SlotIterator iterator = null;

        try {

            Constructor<? extends SlotIterator> constructor = iteratorClass.getConstructor(SimpleInventory.class, int.class, int.class, int.class, int.class);
            iterator = constructor.newInstance(inventory, beginRow, beginColumn, endRow, endColumn);

        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {

            e.printStackTrace();
        }
        return iterator;
    }

    public static SlotIterator getIterator(IteratorType type, SimpleInventory inventory, int beginRow, int beginColumn, int endRow, int endColumn, int... blacklisted) {

        Class<? extends SlotIterator> iteratorClass = type.getType();

        SlotIterator iterator = null;

        try {

            Constructor<? extends SlotIterator> constructor = iteratorClass.getConstructor(SimpleInventory.class, int.class, int.class, int.class, int.class, int[].class);
            iterator = constructor.newInstance(inventory, beginRow, beginColumn, endRow, endColumn, blacklisted);

        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {

            e.printStackTrace();
        }
        return iterator;
    }
}
