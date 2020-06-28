package fr.syrows.inventories.builders;

import fr.syrows.inventories.InventorySort;
import fr.syrows.inventories.interfaces.AdvancedInventory;

public interface AbstractBuilder<SELF, T extends AdvancedInventory> {

    SELF withIdentifier(String identifier);

    SELF withTitle(String title);

    SELF withSize(int size);

    SELF withSort(InventorySort sort);

    T build();
}
