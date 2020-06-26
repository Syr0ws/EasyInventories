package fr.syrows.utils;

import fr.syrows.inventories.InventorySort;

public class SlotUtils {

    public static int getRow(InventorySort sort, int slot) {
        return slot / sort.getColumns();
    }

    public static int getColumn(InventorySort sort, int slot) {
        return slot % sort.getColumns();
    }

    public static int getSlot(InventorySort sort, int row, int column) {
        return ((row - 1) * sort.getColumns()) + (column - 1);
    }
}
