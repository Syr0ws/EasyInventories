package fr.syrows.easyinventories.builders;

import fr.syrows.easyinventories.contents.InventorySort;
import fr.syrows.easyinventories.inventories.AdvancedInventory;

public interface AbstractBuilder<SELF, T extends AdvancedInventory> {

    SELF withIdentifier(String identifier);

    SELF withTitle(String title);

    SELF withSize(int size);

    SELF withSort(InventorySort sort);

    T build();
}
