/*
 * Copyright 2020 Syr0ws
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package fr.syrows.easyinventories.inventories.impl;

import fr.syrows.easyinventories.builders.InventoryBuilder;
import fr.syrows.easyinventories.contents.DefaultInventoryContents;
import fr.syrows.easyinventories.contents.InventoryContents;
import fr.syrows.easyinventories.contents.containers.InventorySortType;
import fr.syrows.easyinventories.inventories.SimpleInventory;
import fr.syrows.easyinventories.manager.InventoryManager;
import org.bukkit.inventory.Inventory;

public class FastInventory extends AbstractInventory implements SimpleInventory {

    private String identifier, title;
    private InventorySortType sort;
    private int size;

    private InventoryContents contents;
    private Inventory inventory;

    protected FastInventory(InventoryManager manager) {
        super(manager);
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
    public InventorySortType getContainer() {
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

            inventory.inventory = super.manager.findCreator(super.sort).getInventory(inventory);
            inventory.contents = new DefaultInventoryContents(inventory);

            return inventory;
        }
    }
}
