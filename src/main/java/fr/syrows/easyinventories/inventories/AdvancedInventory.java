package fr.syrows.easyinventories.inventories;

import fr.syrows.easyinventories.contents.InventoryManager;
import fr.syrows.easyinventories.contents.InventorySort;
import fr.syrows.easyinventories.contents.InventoryContents;
import fr.syrows.easyinventories.listeners.InventoryListener;
import fr.syrows.easyinventories.openers.impl.DefaultOpener;
import fr.syrows.easyinventories.utils.SlotUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryEvent;
import org.bukkit.inventory.Inventory;

import java.util.Collections;
import java.util.List;

public interface AdvancedInventory {

    DefaultOpener DEFAULT_OPENER = new DefaultOpener();

    String getIdentifier();

    String getTitle();

    int getSize();

    InventorySort getSort();

    InventoryManager getManager();

    InventoryContents getContents();

    Inventory getInventory();

    default List<InventoryListener<? extends InventoryEvent>> getListeners() {
        return Collections.emptyList();
    }

    @SuppressWarnings("unchecked")
    default <T extends InventoryEvent> void accept(T event) {

        this.getListeners().stream()
                .filter(listener -> event.getClass().equals(listener.getType()))
                .map(listener -> (InventoryListener<T>) listener)
                .forEach(listener -> listener.accept(event));
    }

    default int getRows() {
        return SlotUtils.getRow(this.getSort(), this.getSize());
    }

    default int getColumns() {
        return this.getSort().getColumns();
    }

    default void open(Player player) {
        this.getManager().open(player, this, DEFAULT_OPENER);
    }

    default void close(Player player) {
        this.getManager().close(player);
    }
}
