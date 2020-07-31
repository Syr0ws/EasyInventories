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
