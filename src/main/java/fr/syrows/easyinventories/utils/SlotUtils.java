package fr.syrows.easyinventories.utils;

import fr.syrows.easyinventories.contents.ContainerType;

public class SlotUtils {

    public static int getRow(ContainerType sort, int slot) {
        return slot / sort.getColumns();
    }

    public static int getColumn(ContainerType sort, int slot) {
        return slot % sort.getColumns();
    }

    public static int getSlot(ContainerType sort, int row, int column) {
        return ((row - 1) * sort.getColumns()) + (column - 1);
    }
}
