package fr.syrows.inventories.builders;

import fr.syrows.inventories.InventoryManager;
import fr.syrows.inventories.InventorySort;
import fr.syrows.inventories.interfaces.AdvancedInventory;

public abstract class InventoryBuilder<SELF, T extends AdvancedInventory> implements AbstractBuilder<SELF, T> {

    protected InventoryManager manager;

    protected String identifier, title;
    protected InventorySort sort;
    protected int size;

    public InventoryBuilder(InventoryManager manager) {

        this.manager = manager;

        this.identifier = "unknown";
        this.sort = InventorySort.CHEST;

        this.title = this.sort.getDefaultTitle();
        this.size = this.sort.getDefaultSize();
    }

    @Override
    public SELF withIdentifier(String identifier) {
        this.identifier = identifier;
        return this.self();
    }

    @Override
    public SELF withTitle(String title) {
        this.title = title;
        return this.self();
    }

    @Override
    public SELF withSize(int size) {
        this.size = size;
        return this.self();
    }

    @Override
    public SELF withSort(InventorySort sort) {
        this.sort = sort;
        return this.self();
    }

    @SuppressWarnings("unchecked")
    private SELF self() {
        return (SELF) this;
    }
}
