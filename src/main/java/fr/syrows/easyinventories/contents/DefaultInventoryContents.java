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

package fr.syrows.easyinventories.contents;

import fr.syrows.easyinventories.contents.items.ClickableItem;
import fr.syrows.easyinventories.inventories.SimpleInventory;
import fr.syrows.easyinventories.iterators.SlotIterator;
import fr.syrows.easyinventories.tools.SlotValidator;
import fr.syrows.easyinventories.utils.SlotUtils;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.Optional;

public class DefaultInventoryContents implements InventoryContents {

    private SimpleInventory inventory;
    private ClickableItem[][] contents;

    public DefaultInventoryContents(SimpleInventory inventory) {

        int rows = inventory.getRows(), columns = inventory.getColumns();

        this.contents = new ClickableItem[rows][columns]; // Creating a two dimensional array to work with rows and columns.
        this.inventory = inventory;
    }

    @Override
    public void setItem(int slot, ClickableItem item) {

        SlotValidator.validateSlot(this.inventory, slot);

        int row = SlotUtils.getRow(this.inventory.getSort(), slot);
        int column = SlotUtils.getColumn(this.inventory.getSort(), slot);

        this.contents[row][column] = item;
        this.update(slot); // Updating slot in the Bukkit inventory.
    }

    @Override
    public void setItem(int row, int column, ClickableItem item) {

        SlotValidator.validatePosition(this.inventory, row, column);

        this.contents[row - 1][column - 1] = item;
        this.update(row, column); // Updating slot in the Bukkit inventory.
    }

    @Override
    public void setItems(ClickableItem item, int... slots) {

        if(slots.length == 0)
            throw new IllegalArgumentException("No slots specified.");

        for(int slot : slots) this.setItem(slot, item);
    }

    @Override
    public Optional<ClickableItem> getItem(int slot) {

        SlotValidator.validateSlot(this.inventory, slot);

        int row = SlotUtils.getRow(this.inventory.getSort(), slot);
        int column = SlotUtils.getColumn(this.inventory.getSort(), slot);

        ClickableItem item = this.contents[row][column];

        return item == null ? Optional.empty() : Optional.of(item);
    }

    @Override
    public Optional<ClickableItem> getItem(int row, int column) {

        SlotValidator.validatePosition(this.inventory, row, column);

        ClickableItem item =  this.contents[row - 1][column - 1];

        return item == null ? Optional.empty() : Optional.of(item);
    }

    @Override
    public boolean isEmpty(int slot) {
        return !this.getItem(slot).isPresent();
    }

    @Override
    public boolean isEmpty(int row, int column) {
        return !this.getItem(row, column).isPresent();
    }

    @Override
    public void update(int slot) {

        Optional<ClickableItem> optional = this.getItem(slot);

        // If there is ClickableItem at the specified slot, retrieving its ItemStack or else retrieving null.
        ItemStack stack = optional.map(ClickableItem::getItemStack).orElse(null);

        this.inventory.getInventory().setItem(slot, stack);
    }

    @Override
    public void update(int row, int column) {

        int slot = SlotUtils.getSlot(this.inventory.getSort(), row, column);

        Optional<ClickableItem> optional = this.getItem(slot);

        // If there is ClickableItem at the specified slot, retrieving its ItemStack or else retrieving null.
        ItemStack stack = optional.map(ClickableItem::getItemStack).orElse(null);

        this.inventory.getInventory().setItem(slot, stack);
    }

    // TODO
    @Override
    public SlotIterator getContentsIterator() {
        return null;
    }

    @Override
    public ClickableItem[][] getContents() {
        return Arrays.copyOf(this.contents, this.contents.length);
    }

    // Using protected visibility to allows the method to be redefined
    // and to change its return type to prevent multiple casts in code.
    protected SimpleInventory getInventory() {
        return this.inventory;
    }
}
