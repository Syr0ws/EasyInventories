package fr.syrows.easyinventories.inventories.impl;

import fr.syrows.easyinventories.builders.InventoryBuilder;
import fr.syrows.easyinventories.contents.InventoryContents;
import fr.syrows.easyinventories.contents.InventoryManager;
import fr.syrows.easyinventories.contents.ContainerType;
import fr.syrows.easyinventories.contents.impl.DefaultContents;
import fr.syrows.easyinventories.inventories.SimpleInventory;
import org.bukkit.inventory.Inventory;

public class FastInventory extends EasyInventory implements SimpleInventory {

    private String identifier, title;
    private ContainerType sort;
    private int size;

    private InventoryContents contents;
    private Inventory inventory;

    protected FastInventory(InventoryManager manager) {
        super(manager);

        this.inventory = manager.create(this);
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
    public ContainerType getType() {
        return this.sort;
    }

    @Override
    public InventoryContents getContents() {
        return this.contents;
    }

    @Override
    public Inventory getInventory() {
        return this.inventory;
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

            return inventory;
        }
    }
}
