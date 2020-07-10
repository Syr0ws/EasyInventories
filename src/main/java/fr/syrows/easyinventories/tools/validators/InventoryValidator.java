package fr.syrows.easyinventories.tools.validators;

import fr.syrows.easyinventories.contents.ContainerType;
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

    public static void validateSize(ContainerType sort, int size) {

        if(!sort.isValid(size))
            throw new IllegalArgumentException(String.format("Size %d is invalid for the type '%s'.", size, sort.name()));
    }

    public static void validateInventory(SimpleInventory inventory) {

        InventoryValidator.validateIdentifier(inventory.getIdentifier());
        InventoryValidator.validateTitle(inventory.getTitle());
        InventoryValidator.validateSize(inventory.getType(), inventory.getSize());
    }
}
