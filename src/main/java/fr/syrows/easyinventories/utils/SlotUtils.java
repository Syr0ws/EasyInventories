package fr.syrows.easyinventories.utils;

import fr.syrows.easyinventories.contents.containers.InventorySort;

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
