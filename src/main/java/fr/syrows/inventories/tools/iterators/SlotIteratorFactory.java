package fr.syrows.inventories.tools.iterators;

import fr.syrows.inventories.interfaces.EasyInventory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class SlotIteratorFactory {

    public static SlotIterator getIterator(IteratorType type, EasyInventory inventory, int beginRow, int beginColumn, int endRow, int endColumn) {

        Class<? extends SlotIterator> iteratorClass = type.getType();

        SlotIterator iterator = null;

        try {

            Constructor<? extends SlotIterator> constructor = iteratorClass.getConstructor(EasyInventory.class, int.class, int.class, int.class, int.class);
            iterator = constructor.newInstance(inventory, beginRow, beginColumn, endRow, endColumn);

        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {

            e.printStackTrace();
        }
        return iterator;
    }
}
