package fr.syrows.easyinventories.events;

import fr.syrows.easyinventories.inventories.SimpleInventory;

public interface InventoryEvent {

    SimpleInventory getSimpleInventory();
}
