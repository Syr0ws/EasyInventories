package fr.syrows.easyinventories.inventories;

import fr.syrows.easyinventories.tools.CloseReason;
import org.bukkit.entity.Player;

public interface TreeInventory extends SimpleInventory {

    TreeInventory getParent();

    void setParent(TreeInventory inventory);

    TreeInventory getOpened();

    void setOpened(TreeInventory inventory);

    default void back(Player player) {

        TreeInventory parent = this.getParent();

        if(parent == null)
            throw new NullPointerException("No parent inventory.");

        this.close(player, CloseReason.OPEN_PARENT);

        parent.setOpened(null);
        parent.open(player);
    }

    default void open(Player player, TreeInventory inventory) {

        this.close(player, CloseReason.OPEN_CHILD);

        inventory.open(player);
        inventory.setParent(this);

        this.setOpened(inventory);
    }
}
