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

import fr.syrows.easyinventories.contents.sort.InventorySort;
import fr.syrows.easyinventories.inventories.SimpleInventory;

/**
 * This class contains utils methods to check inventory attributes.
 */
public class InventoryValidator {

    /**
     * Validate an inventory id.
     *
     * throws IllegalArgumentException - If the id is null.
     *
     * @param id the id to validate.
     */
    public static void validateId(String id) {

        if(id == null)
            throw new IllegalArgumentException("Identifier cannot be null.");
    }

    /**
     * Validate an inventory title.
     *
     * throws IllegalArgumentException If the title exceed 32 characters.
     *
     * @param title the title to validate.
     */
    public static void validateTitle(String title) {

        if(title.length() > 32)
            throw new IllegalArgumentException("Title length must be lower than 32 characters.");
    }

    /**
     * Validate an inventory size according to its sort. The sort specified will be
     * used to check if the specified size is allowed for the inventory.
     *
     * throws IllegalArgumentException If the specified size is not allowed for this InventorySort.
     *
     * @param sort the sort of the inventory.
     * @param size the size to validate.
     */
    public static void validateSize(InventorySort sort, int size) {

        if(!sort.isAllowed(size))
            throw new IllegalArgumentException(String.format("Size %d is invalid for the type '%s'.", size, sort.getInventoryType().name()));
    }

    /**
     * Validate the id, the title and the size of an inventory.
     * Previous mentioned methods are used to perform this operation
     * so we recommend to read their documentation to get more information.
     *
     * @param inventory the inventory to validate.
     */
    public static void validateInventory(SimpleInventory inventory) {

        InventoryValidator.validateId(inventory.getId());
        InventoryValidator.validateTitle(inventory.getTitle());
        InventoryValidator.validateSize(inventory.getSort(), inventory.getSize());
    }
}
