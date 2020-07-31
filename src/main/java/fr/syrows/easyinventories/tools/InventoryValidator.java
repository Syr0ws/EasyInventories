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

public class InventoryValidator {

    public static void validateIdentifier(String identifier) {

        if(identifier == null)
            throw new IllegalArgumentException("Identifier cannot be null.");
    }

    public static void validateTitle(String title) {

        if(title.length() > 32)
            throw new IllegalArgumentException("Title length must be lower than 32 characters.");
    }

    public static void validateSize(InventorySort sort, int size) {

        if(!sort.isAllowed(size))
            throw new IllegalArgumentException(String.format("Size %d is invalid for the type '%s'.", size, sort.getInventoryType().name()));
    }

    public static void validateInventory(SimpleInventory inventory) {

        InventoryValidator.validateIdentifier(inventory.getId());
        InventoryValidator.validateTitle(inventory.getTitle());
        InventoryValidator.validateSize(inventory.getSort(), inventory.getSize());
    }
}
