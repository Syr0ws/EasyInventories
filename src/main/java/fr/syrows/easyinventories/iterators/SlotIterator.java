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

package fr.syrows.easyinventories.iterators;

import fr.syrows.easyinventories.contents.sort.InventorySort;
import fr.syrows.easyinventories.contents.items.ClickableItem;
import fr.syrows.easyinventories.inventories.SimpleInventory;
import fr.syrows.easyinventories.tools.SlotValidator;
import fr.syrows.easyinventories.utils.SlotUtils;

import java.util.Optional;

public abstract class SlotIterator {

    private SimpleInventory inventory;
    private int beginRow, beginColumn;
    private int endRow, endColumn;
    private int[] blacklisted;

    protected int row, column;

    public SlotIterator(SimpleInventory inventory, int beginRow, int beginColumn, int endRow, int endColumn) {

        SlotValidator.validatePosition(inventory, beginRow, beginColumn);
        SlotValidator.validatePosition(inventory, endRow, endColumn);

        this.inventory = inventory; // Was in first position, before validations.

        this.beginRow = beginRow - 1;
        this.beginColumn = beginColumn - 1;

        this.endRow = endRow;
        this.endColumn = endColumn;

        this.blacklisted = new int[0];

        this.start();
    }

    public SlotIterator(SimpleInventory inventory, int beginRow, int beginColumn, int endRow, int endColumn, int[] blacklisted) {
        this(inventory, beginRow, beginColumn, endRow, endColumn);

        this.blacklisted = blacklisted;

        Slot beginSlot = new Slot(beginRow, beginColumn), endSlot = new Slot(endRow, endColumn);

        if(this.isBlacklisted(beginSlot.getSlot()))
            throw new IllegalArgumentException("Begin slot cannot be blacklisted.");

        if(this.isBlacklisted(endSlot.getSlot()))
            throw new IllegalArgumentException("End slot cannot be blacklisted.");
    }

    public abstract Slot next();

    public abstract Slot previous();

    public void start() {
        this.row = this.beginRow;
        this.column = this.beginColumn;
    }

    public void end() {
        this.row = this.endRow;
        this.column = this.endColumn;
    }

    public boolean hasNext() {

        if (this.row >= this.endRow) return false;

        return this.column < this.endColumn|| (this.row != this.endRow - 1);
    }

    public boolean hasPrevious() {
        return this.row > this.beginRow || this.column > this.beginColumn;
    }

    protected Slot get(int row, int column) {
        return new Slot(row + 1, column + 1);
    }

    public SimpleInventory getInventory() {
        return this.inventory;
    }

    public int getBeginRow() {
        return this.beginRow;
    }

    public int getBeginColumn() {
        return this.beginColumn;
    }

    public int getEndRow() {
        return this.endRow;
    }

    public int getEndColumn() {
        return this.endColumn;
    }

    public int[] getBlacklisted() {
        return this.blacklisted;
    }

    public boolean isBlacklisted(int slot) {

        for(int blacklisted : this.blacklisted) {

            if(blacklisted == slot) return true;
        }
        return false;
    }

    public class Slot {

        private int row, column;

        public Slot(int row, int column) {
            this.row = row;
            this.column = column;
        }

        public int getRow() {
            return this.row;
        }

        public int getColumn() {
            return this.column;
        }

        public int getSlot() {

            InventorySort sort = SlotIterator.this.inventory.getSort();

            return SlotUtils.getSlot(sort, this.row, this.column);
        }

        public Optional<ClickableItem> getItem() {
            return SlotIterator.this.inventory.getContents().getItem(this.row, this.column);
        }
    }
}
