package fr.syrows.easyinventories.contents;

import org.bukkit.event.inventory.InventoryType;

import java.util.Arrays;

public enum InventorySort {

    CHEST(9, 9, 18, 27, 36, 45, 54), DROPPER(3), DISPENSER(3), HOPPER(5);

    private int columns;
    private int[] sizes;

    InventorySort(int columns, int... sizes) {

        if(sizes.length == 0) sizes = new int[]{this.getType().getDefaultSize()};

        this.columns = columns;
        this.sizes = sizes;
    }

    public int getColumns() {
        return this.columns;
    }

    public int getDefaultSize() {
        return this.sizes[0];
    }

    public String getDefaultTitle() {
        return this.getType().getDefaultTitle();
    }

    public boolean isValid(int size) {
        return Arrays.stream(this.sizes).anyMatch(s -> s == size);
    }

    public InventoryType getType() {
        return InventoryType.valueOf(this.name());
    }
}
