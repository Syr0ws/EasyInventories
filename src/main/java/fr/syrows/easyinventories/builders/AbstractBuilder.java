package fr.syrows.easyinventories.builders;

import fr.syrows.easyinventories.contents.containers.InventorySortType;
import fr.syrows.easyinventories.inventories.SimpleInventory;

public interface AbstractBuilder<SELF, T extends SimpleInventory> {

    SELF withIdentifier(String identifier);

    SELF withTitle(String title);

    SELF withSize(int size);

    SELF withSort(InventorySortType sort);

    T build();
}
