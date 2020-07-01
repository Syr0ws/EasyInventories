package fr.syrows.easyinventories.openers;

import fr.syrows.easyinventories.inventories.SimpleInventory;
import org.bukkit.entity.Player;

public interface InventoryOpener<T extends SimpleInventory> {

    void open(Player player, T inventory);
}
