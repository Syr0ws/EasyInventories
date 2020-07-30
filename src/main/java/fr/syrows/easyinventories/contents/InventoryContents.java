package fr.syrows.easyinventories.contents;

import fr.syrows.easyinventories.contents.items.ClickableItem;
import fr.syrows.easyinventories.exceptions.InvalidPositionException;
import fr.syrows.easyinventories.iterators.SlotIterator;

import java.util.Optional;

public interface InventoryContents {

    void setItem(int slot, ClickableItem item);

    void setItem(int row, int column, ClickableItem item);

    void setItems(ClickableItem item, int... slots);

    Optional<ClickableItem> getItem(int slot);

    Optional<ClickableItem> getItem(int row, int column);

    boolean isEmpty(int slot);

    boolean isEmpty(int row, int column);

    void update(int slot);

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
