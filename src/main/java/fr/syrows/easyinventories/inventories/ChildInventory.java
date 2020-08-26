/*
 * Copyright 2020 Syr0ws
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package fr.syrows.easyinventories.inventories;

import fr.syrows.easyinventories.tools.CloseReason;
import org.bukkit.entity.Player;

public interface ChildInventory extends SimpleInventory {

    /**
     * Returns the parent of the current inventory. May be null if this
     * this inventory hasn't parent. When closing this inventory, if the
     * CloseReason isn't CloseReason.CLOSE_ALL then this parent inventory
     * will be opened.
     *
     * @return the parent inventory.
     */
    ParentInventory getParent();

    /**
     * Set the parent inventory for this inventory.
     *
     * @param inventory - the parent inventory to set.
     */
    void setParent(ParentInventory inventory);

    /**
     * Back to the parent inventory for the specified player.
     *
     * @param player - the player for which to open the parent inventory.
     */
    default void back(Player player) {

        ParentInventory parent = this.getParent();

        if(parent == null)
            throw new NullPointerException("No parent inventory.");

        this.close(player, CloseReason.OPEN_PARENT);

        parent.setOpened(null);
        parent.open(player);
    }
}
