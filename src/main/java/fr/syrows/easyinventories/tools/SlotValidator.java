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
