package fr.syrows.easyinventories.openers.impl;

import fr.syrows.easyinventories.inventories.SimpleInventory;
import fr.syrows.easyinventories.openers.InventoryOpener;
import fr.syrows.easyinventories.tools.validators.InventoryValidator;
import org.bukkit.entity.Player;

public class DefaultInventoryOpener implements InventoryOpener<SimpleInventory> {

    public static final DefaultInventoryOpener INSTANCE = new DefaultInventoryOpener();

    @Override
    public void open(Player player, SimpleInventory inventory) {

        InventoryValidator.validateInventory(inventory);

        player.openInventory(inventory.getInventory());
    }
}
