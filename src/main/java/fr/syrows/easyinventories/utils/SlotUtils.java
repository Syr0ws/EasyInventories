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

    public static int getRow(InventorySort sort, int slot) {
        return slot / sort.getDefaultColumns();
    }

    public static int getColumn(InventorySort sort, int slot) {
        return slot % sort.getDefaultColumns();
    }

    public static int getSlot(InventorySort sort, int row, int column) {
        return ((row - 1) * sort.getDefaultColumns()) + (column - 1);
    }
}
