package fr.syrows.inventories.openers.impl;

import fr.syrows.inventories.interfaces.AdvancedInventory;
import fr.syrows.inventories.openers.InventoryOpener;
import fr.syrows.inventories.tools.validators.InventoryValidator;
import org.bukkit.entity.Player;

public class DefaultOpener implements InventoryOpener<AdvancedInventory> {

    @Override
    public void open(Player player, AdvancedInventory inventory) {

        InventoryValidator.validateInventory(inventory);

        player.openInventory(inventory.getInventory());
    }
}
