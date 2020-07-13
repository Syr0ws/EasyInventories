package fr.syrows.easyinventories.builders;

import fr.syrows.easyinventories.contents.InventoryManager;
import fr.syrows.easyinventories.contents.ContainerType;
import fr.syrows.easyinventories.inventories.SimpleInventory;

public abstract class InventoryBuilder<SELF, T extends SimpleInventory> implements AbstractBuilder<SELF, T> {

    protected InventoryManager manager;

    protected String identifier, title;
    protected ContainerType sort;
    protected int size;

    public InventoryBuilder(InventoryManager manager) {

        this.manager = manager;

        this.identifier = "unknown";
        this.sort = ContainerType.CHEST;

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
    public SELF withSort(ContainerType sort) {
        this.sort = sort;
        return this.self();
    }

    @SuppressWarnings("unchecked")
    private SELF self() {
        return (SELF) this;
    }
}
