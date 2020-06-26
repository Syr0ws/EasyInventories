package fr.syrows.inventories.interfaces;

import org.bukkit.entity.Player;

public interface TreeInventory extends EasyInventory {

    TreeInventory getParent();

    EasyInventory getOpened();

    void setOpened(EasyInventory inventory);

    default boolean hasParent() {
        return this.getParent() != null;
    }

    default boolean hasOpened() {
        return this.getOpened() != null;
    }

    default void back(Player player) {

        TreeInventory parent = this.getParent();

        if(parent == null)
            throw new NullPointerException("Parent inventory is null.");

        parent.setOpened(null);

        this.getManager().open(player, parent);
    }

    default void open(Player player, EasyInventory inventory) {

        this.getManager().open(player, inventory);
        this.setOpened(inventory);
    }
}
