package fr.syrows.inventories.interfaces.impl;

import fr.syrows.inventories.InventoryManager;
import fr.syrows.inventories.builders.InventoryBuilder;
import fr.syrows.inventories.contents.InventoryContents;
import fr.syrows.inventories.interfaces.ValueInventory;

public class FastValueInventory<T> extends FastInventory implements ValueInventory<T> {

    private T element;

    protected FastValueInventory(InventoryManager manager) {
        super(manager);
    }

    @Override
    public T getElement() {
        return this.element;
    }

    @Override
    public void setElement(T element) {
        this.element = element;
    }

    public static class Builder<T> extends InventoryBuilder<Builder<T>, FastValueInventory<T>> {

        public Builder(InventoryManager manager) {
            super(manager);
        }

        @Override
        public FastValueInventory<T> build() {

            FastValueInventory<T> inventory = new FastValueInventory<>(super.manager);

            inventory.identifier = super.identifier;
            inventory.title = super.title;
            inventory.size = super.size;
            inventory.sort = super.sort;

            inventory.contents = new InventoryContents<>(inventory);
            inventory.inventory = this.manager.create(inventory);

            return inventory;
        }
    }
}
