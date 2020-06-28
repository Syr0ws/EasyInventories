package fr.syrows.inventories.tools.validators;

import fr.syrows.inventories.exceptions.InvalidSlotException;
import fr.syrows.inventories.interfaces.AdvancedInventory;

public class SlotValidator {

    public static void validateRow(AdvancedInventory inventory, int row) {

        if(row <= 0 || row > inventory.getRows())
            throw new InvalidSlotException(String.format("Row %d is invalid.", row));
    }

    public static void validateColumn(AdvancedInventory inventory, int column) {

        if(column <= 0 || column > inventory.getColumns())
            throw new InvalidSlotException(String.format("Column %d is invalid.", column));
    }

    public static void validateSlot(AdvancedInventory inventory, int slot) {

        if(slot < 0 || slot >= inventory.getSize())
            throw new InvalidSlotException(String.format("Slot %d is invalid.", slot));
    }
}
