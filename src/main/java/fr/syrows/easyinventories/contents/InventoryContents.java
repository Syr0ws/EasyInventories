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
import fr.syrows.easyinventories.exceptions.InvalidPositionException;
import fr.syrows.easyinventories.iterators.SlotIterator;

import java.util.Optional;

public interface InventoryContents {

    /**
     * Set a ClickableItem at the specified slot.
     *
     * @param slot - the slot to which set the item.
     * @param item - the item to set.
     */
    void setItem(int slot, ClickableItem item);

    /**
     * Set a ClickableItem at the specified rows and columns.
     *
     * @param row - the row to which set the item.
     * @param column - the column to which set the item.
     * @param item - the item to set.
     */
    void setItem(int row, int column, ClickableItem item);

    /**
     * Set a ClickableItem to the specified slots.
     *
     * @param item - the item to set.
     * @param slots - an array of slots to which set the item.
     */
    void setItems(ClickableItem item, int... slots);

    Optional<ClickableItem> getItem(int slot);

    Optional<ClickableItem> getItem(int row, int column);

    /**
     * Check there is a ClickableItem at the specified slot.
     *
     * @param slot - the slot to check.
     * @return false if there is a ClickableItem at this slot or else true.
     */
    boolean isEmpty(int slot);

    /**
     * Check if there is a ClickableItem at the specified row and column.
     *
     * @param row - the row coordinate.
     * @param column - the column coordinate.
     * @return false if there is a ClickableItem at the specified position or else true.
     */
    boolean isEmpty(int row, int column);

    /**
     * Update the specified slot.
     *
     * @param slot - the slot to update.
     */
    void update(int slot);

    /**
     * Update the position specified with row and column parameters.
     *
     * @param row - the row coordinate.
     * @param column - the column coordinate.
     */
    void update(int row, int column);

    SlotIterator getContentsIterator();

    ClickableItem[][] getContents();

    default void fillEmptySlots(ClickableItem item) {

        ClickableItem[][] contents = this.getContents();

        for(int row = 0; row < contents.length; row++) {

            for(int column = 0; column < contents.length; column++) {

                if(this.isEmpty(row, column)) this.setItem(row, column, item);
            }
        }
    }

    default void fillRow(int row, ClickableItem item) {

        ClickableItem[][] contents = this.getContents();

        if(row < 1 || row > contents.length)
            throw new InvalidPositionException(String.format("Row must be between 1 and %d", contents.length), row);

        for(int column = 1; column < contents[row - 1].length; column++)
            this.setItem(row, column, item);
    }

    default void fillColumn(int column, ClickableItem item) {

        ClickableItem[][] contents = this.getContents();

        if(column < 1 || column > contents[0].length)
            throw new InvalidPositionException(String.format("Column must be between 1 and %d", contents.length), column);

        for(int row = 1; row < contents.length; row++)
            this.setItem(row, column, item);
    }
}
