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

    public static void validateRow(SimpleInventory inventory, int row) {

        if(row <= 0 || row > inventory.getRows())
            throw new InvalidPositionException(String.format("Row %d is invalid.", row), row);
    }

    public static void validateColumn(SimpleInventory inventory, int column) {

        if(column <= 0 || column > inventory.getColumns())
            throw new InvalidPositionException(String.format("Column %d is invalid.", column), column);
    }

    public static void validateSlot(SimpleInventory inventory, int slot) {

        if(slot < 0 || slot >= inventory.getSize())
            throw new InvalidPositionException(String.format("Slot %d is invalid.", slot), slot);
    }

    public static void validatePosition(SimpleInventory inventory, int row, int column) {

        SlotValidator.validateRow(inventory, row);
        SlotValidator.validateColumn(inventory, column);
    }
}
