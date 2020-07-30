package fr.syrows.easyinventories.contents.containers;

import org.bukkit.event.inventory.InventoryType;

public interface InventorySort {

    boolean isAllowed(int size);

    int getDefaultColumns();

    int getDefaultSize();

    String getDefaultTitle();

    InventoryType getInventoryType();
}
