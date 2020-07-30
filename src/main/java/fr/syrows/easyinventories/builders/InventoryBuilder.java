package fr.syrows.easyinventories.builders;

import fr.syrows.easyinventories.contents.containers.InventorySortType;
import fr.syrows.easyinventories.inventories.SimpleInventory;
import fr.syrows.easyinventories.manager.InventoryManager;

public abstract class InventoryBuilder<SELF, T extends SimpleInventory> implements AbstractBuilder<SELF, T> {

    protected InventoryManager manager;
    protected String identifier, title;
    protected InventorySortType sort;
    protected int size;

    public InventoryBuilder(InventoryManager manager) {

        this.manager = manager;

        this.identifier = "unknown";
        this.sort = InventorySortType.CHEST;

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
    public SELF withSort(InventorySortType sort) {
        this.sort = sort;
        return this.self();
    }

    @SuppressWarnings("unchecked")
    private SELF self() {
        return (SELF) this;
    }
}
