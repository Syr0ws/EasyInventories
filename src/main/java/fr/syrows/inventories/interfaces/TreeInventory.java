package fr.syrows.inventories.interfaces;

import org.bukkit.entity.Player;

public interface TreeInventory extends AdvancedInventory {

    TreeInventory getParent();

    void setParent(TreeInventory inventory);

    TreeInventory getOpened();

    void setOpened(TreeInventory inventory);

    default void back(Player player) {

        TreeInventory parent = this.getParent();

        if(parent == null)
            throw new NullPointerException("No parent inventory.");

        parent.setOpened(null);
        parent.open(player);
    }

    default void open(Player player, TreeInventory inventory) {

        inventory.open(player);
        inventory.setParent(this);

        this.setOpened(inventory);
    }
}
