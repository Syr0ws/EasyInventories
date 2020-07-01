package fr.syrows.easyinventories.openers;

import fr.syrows.easyinventories.inventories.AdvancedInventory;
import org.bukkit.entity.Player;

public interface InventoryOpener<T extends AdvancedInventory> {

    void open(Player player, T inventory);
}
