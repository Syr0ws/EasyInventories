/*
 * Copyright 2020 Syr0ws
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package fr.syrows.easyinventories.iterators;

import fr.syrows.easyinventories.inventories.SimpleInventory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class SlotIteratorFactory {

    /**
     * Create a new SlotIterator.
     *
     * @param type the type of iterator you want to get.
     * @param inventory the inventory in which the iterator will iterate.
     * @param beginRow the begin row of the iterator.
     * @param beginColumn the begin column of the iterator.
     * @param endRow the end row of the iterator.
     * @param endColumn the end column of the iterator.
     * @param blacklisted an array of blacklisted slots. The iterator will not iterate over these slots.
     *
     * @return an object of type SlotIterator according to the type specified.
     */
    public static SlotIterator getIterator(SlotIteratorType type, SimpleInventory inventory, int beginRow, int beginColumn, int endRow, int endColumn, int... blacklisted) {

        Class<? extends SlotIterator> iteratorClass = type.getIteratorClass();

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
