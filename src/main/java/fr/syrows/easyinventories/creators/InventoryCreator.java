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

package fr.syrows.easyinventories.creators;

import fr.syrows.easyinventories.contents.sort.InventorySort;
import fr.syrows.easyinventories.inventories.SimpleInventory;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface InventoryCreator {

    // A List that store all the creators. Can be accessed directly to add or remove creators easily.
    List<InventoryCreator> creators = new ArrayList<>();

    /**
     * A wrapper that returns an Inventory from a SimpleInventory object.
     *
     * @param inventory an object of type SimpleInventory to wrap into an Inventory.
     *
     * @return the wrapped inventory.
     */
    Inventory getInventory(SimpleInventory inventory);

    /**
     * Check is an InventorySort object is supported by the creator.
     *
     * @param sort an InventorySort object to check.
     *
     * @return true if the sort is supported or else false.
     */
    boolean isSupported(InventorySort sort);

    /**
     * Returns an optional that encapsulate an InventoryCreator object.
     *
     * To get the creator, the method will search into the creators list
     * specified in this interface. If no creator has been found, an empty
     * optional will be returned.
     *
     * @param sort an InventorySort object for which get an InventoryCreator.
     *
     * @return an optional.
     */
    static Optional<InventoryCreator> findCreator(InventorySort sort) {
        return creators.stream().filter(creator -> creator.isSupported(sort)).findFirst();
    }
}
