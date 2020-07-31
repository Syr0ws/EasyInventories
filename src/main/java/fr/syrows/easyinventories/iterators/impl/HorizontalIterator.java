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

package fr.syrows.easyinventories.iterators.impl;

import fr.syrows.easyinventories.inventories.SimpleInventory;
import fr.syrows.easyinventories.iterators.SlotIterator;

import java.util.NoSuchElementException;

public class HorizontalIterator extends SlotIterator {

    public HorizontalIterator(SimpleInventory inventory, int beginRow, int beginColumn, int endRow, int endColumn) {
        super(inventory, beginRow, beginColumn, endRow, endColumn);
    }

    public HorizontalIterator(SimpleInventory inventory, int beginRow, int beginColumn, int endRow, int endColumn, int[] blacklisted) {
        super(inventory, beginRow, beginColumn, endRow, endColumn, blacklisted);
    }

    @Override
    public Slot next() {

        if(!super.hasNext())
            throw new NoSuchElementException("No element found.");

        if(super.column >= super.getEndColumn()) {

            super.row++;
            super.column = super.getBeginColumn();
        }
        Slot slot = super.get(super.row, super.column++);

        return super.isBlacklisted(slot.getSlot()) ? this.next() : slot;
    }

    @Override
    public Slot previous() {

        if(!super.hasPrevious())
            throw new NoSuchElementException("No element found.");

        if(super.column == super.getBeginColumn() || super.column >= super.getEndColumn()) {

            super.row--;
            super.column = super.getEndColumn();
        }
        Slot slot =  super.get(super.row, --super.column);

        return super.isBlacklisted(slot.getSlot()) ? this.previous() : slot;
    }
}
