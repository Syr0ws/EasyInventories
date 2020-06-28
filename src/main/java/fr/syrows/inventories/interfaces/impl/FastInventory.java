package fr.syrows.inventories.interfaces.impl;

import fr.syrows.inventories.InventoryManager;
import fr.syrows.inventories.InventorySort;
import fr.syrows.inventories.builders.InventoryBuilder;
import fr.syrows.inventories.contents.InventoryContents;
import fr.syrows.inventories.contents.impl.DefaultContents;
import fr.syrows.inventories.interfaces.AdvancedInventory;
import fr.syrows.inventories.openers.InventoryOpener;
import fr.syrows.inventories.openers.impl.DefaultOpener;
import org.bukkit.inventory.Inventory;

public class FastInventory implements AdvancedInventory {

    protected String identifier, title;
    protected InventorySort sort;
    protected int size;

    protected InventoryContents contents;
    protected Inventory inventory;

    private InventoryManager manager;

    protected FastInventory(InventoryManager manager) {
        this.manager = manager;
    }

    @Override
    public String getIdentifier() {
        return this.identifier;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public int getSize() {
        return this.size;
    }

    @Override
    public InventorySort getSort() {
        return this.sort;
    }

    @Override
    public Inventory getInventory() {
        return this.inventory;
    }

    @Override
    public InventoryContents getContents() {
        return this.contents;
    }

    @Override
    public InventoryManager getManager() {
        return this.manager;
    }

    public static class Builder extends InventoryBuilder<Builder, FastInventory> {

        public Builder(InventoryManager manager) {
            super(manager);
        }

        @Override
        public FastInventory build() {

            FastInventory inventory = new FastInventory(super.manager);

            inventory.identifier = super.identifier;
            inventory.title = super.title;
            inventory.size = super.size;
            inventory.sort = super.sort;

            inventory.contents = new DefaultContents(inventory);
            inventory.inventory = this.manager.create(inventory);

            return inventory;
        }
    }
}
