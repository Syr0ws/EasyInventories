package fr.syrows.inventories.builders.tools;

import fr.syrows.inventories.InventorySort;

public class BuilderValidator {

    public static void validateIdentifier(String identifier) {

        if(identifier == null)
            throw new IllegalArgumentException("Identifier cannot be null.");
    }

    public static void validateTitle(String title) {

        if(title.length() > 32)
            throw new IllegalArgumentException("Title length must be lower than 32 characters.");
    }

    public static void validateSize(InventorySort sort, int size) {

        if(!sort.isValid(size))
            throw new IllegalArgumentException(String.format("Size %d is invalid for the type '%s'.", size, sort.name()));
    }
}
