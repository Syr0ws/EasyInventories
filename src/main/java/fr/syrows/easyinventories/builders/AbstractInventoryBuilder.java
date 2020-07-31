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

package fr.syrows.easyinventories.builders;

import fr.syrows.easyinventories.contents.sort.InventorySortType;
import fr.syrows.easyinventories.inventories.SimpleInventory;

public interface AbstractInventoryBuilder<SELF, T extends SimpleInventory> {

    /**
     * Set the id of the inventory.
     *
     * @param id - the id of the inventory.
     * @return an object of type SELF.
     */
    SELF withId(String id);

    /**
     * Set the title of the inventory.
     * @param title - the title of the inventory.
     * @return an object of type SELF.
     */
    SELF withTitle(String title);

    /**
     * Set the size of the inventory.
     *
     * @param size - the size of the inventory.
     * @return an object of type SELF.
     */
    SELF withSize(int size);

    /**
     * Set the sort if the inventory.
     *
     * @param sort - the sort of the inventory.
     * @return an object of type SELF.
     */
    SELF withSort(InventorySortType sort);

    /**
     * Get the built inventory with the parameters spcified with the method above.
     *
     * @return an object of type T.
     */
    T build();
}
