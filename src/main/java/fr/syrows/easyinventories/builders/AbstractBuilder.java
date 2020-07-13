package fr.syrows.easyinventories.builders;

import fr.syrows.easyinventories.contents.ContainerType;
import fr.syrows.easyinventories.inventories.SimpleInventory;

public interface AbstractBuilder<SELF, T extends SimpleInventory> {

    SELF withIdentifier(String identifier);

    SELF withTitle(String title);

    SELF withSize(int size);

    SELF withSort(ContainerType sort);

    T build();
}
