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

package fr.syrows.easyinventories.utils;

import fr.syrows.easyinventories.contents.sort.InventorySort;

public class SlotUtils {

    /**
     * Returns the row coordinate of the specified slot. The sort parameter
     * is needed to perform the conversion because all inventories doesn't have
     * the same number of rows and columns.
     *
     * @param sort the sort of inventory the slot belongs to.
     * @param slot the slot for which get the row. Starts at index 0.
     *
     * @return the row coordinate of the specified slot. Will begin at 0.
     */
    public static int getRow(InventorySort sort, int slot) {
        return slot / sort.getDefaultColumns();
    }

    /**
     * Returns the column coordinate of the specified slot. The sort parameter
     * is needed to perform the conversion because all inventories doesn't have
     * the same number of rows and columns.
     *
     * @param sort the sort of inventory the slot belongs to.
     * @param slot the slot for which get the row. Starts at index 0.
     *
     * @return the column coordinate of the specified slot. Will begin at 0.
     */
    public static int getColumn(InventorySort sort, int slot) {
        return slot % sort.getDefaultColumns();
    }

    /**
     * Returns the slot that corresponds to the specified pair (row, column). The sort
     * parameter is needed to perform the conversion because all inventories doesn't have
     * the same number of rows and columns.
     *
     * @param sort the sort of inventory the row and column belong to.
     * @param row the row coordinate. Starts at index 1.
     * @param column the column coordinate. Starts at index 1.
     *
     * @return an inventory slot. Starts at index 0.
     */
    public static int getSlot(InventorySort sort, int row, int column) {
        return ((row - 1) * sort.getDefaultColumns()) + (column - 1);
    }
}
