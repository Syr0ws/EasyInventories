package fr.syrows.easyinventories.openers.impl;

import fr.syrows.easyinventories.inventories.AdvancedInventory;
import fr.syrows.easyinventories.openers.InventoryOpener;
import fr.syrows.easyinventories.tools.validators.InventoryValidator;
import org.bukkit.entity.Player;

public class DefaultOpener implements InventoryOpener<AdvancedInventory> {

    @Override
    public void open(Player player, AdvancedInventory inventory) {

        InventoryValidator.validateInventory(inventory);

        player.openInventory(inventory.getInventory());
    }
}
