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

package fr.syrows.easyinventories.contents.sort;

import org.bukkit.event.inventory.InventoryType;

public interface InventorySort {

    /**
     * Check if the specified size is allowed for the current InventorySort.
     *
     * @param size - the size to check.
     * @return true if the specified size is allowed or else false.
     */
    boolean isAllowed(int size);

    /**
     * Returns the default number of columns of the inventory.
     *
     * @return a number of columns.
     */
    int getDefaultColumns();

    /**
     * Returns the default size of the inventory.
     *
     * @return an inventory size.
     */
    int getDefaultSize();

    /**
     * Returns the default title of the inventory.
     *
     * @return the default title of the inventory.
     */
    String getDefaultTitle();

    /**
     * A wrapper used to get an InventoryType from the current object
     * of the InventorySort class.
     *
     * @return an object of the InventoryType class.
     */
    InventoryType getInventoryType();
}
