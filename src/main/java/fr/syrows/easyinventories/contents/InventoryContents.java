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
     * @param slot the slot to which set the item.
     * @param item the item to set.
     */
    void setItem(int slot, ClickableItem item);

    /**
     * Set a ClickableItem at the specified rows and columns.
     *
     * @param row the row to which set the item. Begins at index 1.
     * @param column the column to which set the item. Begins at index 1.
     * @param item the item to set.
     */
    void setItem(int row, int column, ClickableItem item);

    /**
     * Set a ClickableItem to the specified slots.
     *
     * @param item the item to set.
     * @param slots an array of slots to which set the item.
     */
    void setItems(ClickableItem item, int... slots);

    /**
     * Get the item at the specified slot encapsulate into an Optional.
     * If there is not item at the specified slot, the method will return
     * an empty Optional.
     *
     * @param slot the slot of the inventory you want to retrieve the item. Begins at index 0.
     * @return an Optional.
     */
    Optional<ClickableItem> getItem(int slot);

    /**
     * Get the item at the specified pair (row, column) encapsulate into an Optional.
     * If there is not item at the specified slot, the method will return an empty Optional.
     *
     * @param row the row coordinate. Begins at index 0.
     * @param column the column coordinate. Begins at index 1.
     *
     * @return an Optional.
     */
    Optional<ClickableItem> getItem(int row, int column);

    /**
     * Check there is a ClickableItem at the specified slot.
     *
     * @param slot the slot to check.
     * @return false if there is a ClickableItem at this slot or else true.
     */
    boolean isEmpty(int slot);

    /**
     * Check if there is a ClickableItem at the specified row and column.
     *
     * @param row the row coordinate.
     * @param column the column coordinate.
     * @return false if there is a ClickableItem at the specified position or else true.
     */
    boolean isEmpty(int row, int column);

    /**
     * Update the specified slot.
     *
     * @param slot the slot to update.
     */
    void update(int slot);

    /**
     * Update the position specified with row and column parameters.
     *
     * @param row the row coordinate.
     * @param column the column coordinate.
     */
    void update(int row, int column);

    /**
     * Returns a SlotIterator object which can be used to iterate through all the slots of an inventory.
     * The type of this iterator is HORIZONTAL.
     *
     * @return an object of type SlotIterator.
     */
    SlotIterator getContentsIterator();

    /**
     * Returns a two dimensional array that stores all the items of an inventory.
     * This array is a copy of the original one.
     *
     * @return a two dimensional array of ClickableItem objects.
     */
    ClickableItem[][] getContents();

    /**
     * Fill the empty slots with the specified item.
     *
     * @param item the item to fill empty slots with.
     */
    default void fillEmptySlots(ClickableItem item) {

        ClickableItem[][] contents = this.getContents();

        for(int row = 0; row < contents.length; row++) {

            for(int column = 0; column < contents.length; column++) {

                if(this.isEmpty(row, column)) this.setItem(row, column, item);
            }
        }
    }

    /**
     * Fill an entire row with the specified item.
     *
     * @param row the row to fill. Begins at index 1.
     * @param item the item to fill the row with.
     *
     * @throws InvalidPositionException if the row number is invalid (row < 1 || row > getContents().length).
     */
    default void fillRow(int row, ClickableItem item) {

        ClickableItem[][] contents = this.getContents();

        if(row < 1 || row > contents.length)
            throw new InvalidPositionException(String.format("Row must be between 1 and %d", contents.length), row);

        for(int column = 1; column < contents[row - 1].length; column++)
            this.setItem(row, column, item);
    }

    /**
     * Fill an entire column with the specified item.
     *
     * @param column the column to fill. Begins at index 1.
     * @param item the item to fill the column with.
     *
     * @throws InvalidPositionException if the column number is invalid (column < 1 || column > getContents()[0].length).
     */
    default void fillColumn(int column, ClickableItem item) {

        ClickableItem[][] contents = this.getContents();

        if(column < 1 || column > contents[0].length)
            throw new InvalidPositionException(String.format("Column must be between 1 and %d", contents.length), column);

        for(int row = 1; row < contents.length; row++)
            this.setItem(row, column, item);
    }
}
