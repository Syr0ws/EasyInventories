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

public interface ParentInventory extends SimpleInventory {

    /**
     * Returns the opened inventory. May be null if no inventory is opened.
     *
     * @return the opened inventory.
     */
    ChildInventory getOpened();

    /**
     * Set an opened inventory for this one. * This method will be called when
     * using the open(Player player, TreeInventory inventory) method.
     *
     * @param inventory - the inventory to set.
     */
    void setOpened(ChildInventory inventory);

    /**
     * Open a child inventory for the specified player.
     *
     * @param player - the player to open the inventory.
     * @param inventory - the inventory to open.
     */
    default void open(Player player, ChildInventory inventory) {

        this.close(player, CloseReason.OPEN_CHILD);

        inventory.open(player);
        inventory.setParent(this);

        this.setOpened(inventory);
    }
}
