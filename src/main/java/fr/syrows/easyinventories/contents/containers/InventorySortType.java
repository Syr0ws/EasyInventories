package fr.syrows.easyinventories.contents.containers;

import org.bukkit.event.inventory.InventoryType;

import java.util.Arrays;

public enum InventorySortType implements InventorySort {

    CHEST(9, 9, 18, 27, 36, 45, 54),
    ENDER_CHEST(9),
    SHULKER_BOX(9),
    DROPPER(3),
    DISPENSER(3),
    HOPPER(5);

    private int columns;
    private int[] sizes;

    InventorySortType(int columns, int... sizes) {

        if(sizes.length == 0) sizes = new int[]{this.getInventoryType().getDefaultSize()};

        this.columns = columns;
        this.sizes = sizes;
    }

    @Override
    public boolean isAllowed(int size) {
        return Arrays.stream(this.sizes).anyMatch(s -> s == size);
    }

    @Override
    public int getDefaultColumns() {
        return this.columns;
    }

    @Override
    public int getDefaultSize() {
        return this.sizes[0];
    }

    @Override
    public String getDefaultTitle() {
        return this.getInventoryType().getDefaultTitle();
    }

    @Override
    public InventoryType getInventoryType() {
        return InventoryType.valueOf(this.name());
    }
}
