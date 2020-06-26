package fr.syrows.inventories.interfaces;

import fr.syrows.inventories.InventoryManager;
import fr.syrows.inventories.InventorySort;
import fr.syrows.inventories.contents.InventoryContents;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public interface EasyInventory {

    String getIdentifier();

    String getTitle();

    int getSize();

    default int getRows() {
        return this.getSize() / this.getSort().getColumns();
    }

    default int getColumns() {
        return this.getSort().getColumns();
    }

    InventorySort getSort();

    Inventory getInventory();

    InventoryContents<? extends EasyInventory> getContents();

    InventoryManager getManager();

    default void open(Player player) {
        this.getManager().open(player, this);
    }

    default void close(Player player) {
        this.getManager().close(player);
    }
}
