package fr.syrows.inventories.openers;

import fr.syrows.inventories.interfaces.AdvancedInventory;
import org.bukkit.entity.Player;

public interface InventoryOpener<T extends AdvancedInventory> {

    void open(Player player, T inventory);
}
