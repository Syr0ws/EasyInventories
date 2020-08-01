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

package fr.syrows.easyinventories.tools;

import fr.syrows.easyinventories.exceptions.InvalidPositionException;
import fr.syrows.easyinventories.inventories.SimpleInventory;

public class SlotValidator {

    /**
     * Check that a row number is valid for an inventory inventory.
     *
     * @param inventory the inventory for which the row number must be checked.
     * @param row the row number to check. Starts at index 1.
     *
     * @throws InvalidPositionException if the row number is lower than 1 or
     * greater than the max number of rows of the inventory.
     */
    public static void validateRow(SimpleInventory inventory, int row) {

        if(row <= 0 || row > inventory.getRows())
            throw new InvalidPositionException(String.format("Row %d is invalid.", row), row);
    }

    /**
     * Check that a column number is valid for an inventory.
     *
     * @param inventory the inventory for which the column number must be checked.
     * @param column the column number to check. Starts at index 1.
     *
     * @throws InvalidPositionException if the column number is lower than 1 or
     * greater than the max number of columns of the inventory.
     */
    public static void validateColumn(SimpleInventory inventory, int column) {

        if(column <= 0 || column > inventory.getColumns())
            throw new InvalidPositionException(String.format("Column %d is invalid.", column), column);
    }

    /**
     * Check that a slot number is valid for an inventory.
     *
     * @param inventory the inventory for which the slot number must be checked.
     * @param slot the slot number to check. Starts at index 0.
     *
     * @throws InvalidPositionException if the slot is lower than 0 or greater or equals
     * than the size of the inventory.
     */
    public static void validateSlot(SimpleInventory inventory, int slot) {

        if(slot < 0 || slot >= inventory.getSize())
            throw new InvalidPositionException(String.format("Slot %d is invalid.", slot), slot);
    }

    /**
     * Check if a pair (row, column) is valid for an inventory.
     * This method calls the methods validateRow(SimpleInventory inventory, int row)
     * and validateColumn(SimpleInventory inventory, int column) to perform the check.
     *
     * @param inventory the inventory for which the coordinates must be checked.
     * @param row the row coordinate.
     * @param column the column coordinate.
     */
    public static void validatePosition(SimpleInventory inventory, int row, int column) {

        SlotValidator.validateRow(inventory, row);
        SlotValidator.validateColumn(inventory, column);
    }
}
