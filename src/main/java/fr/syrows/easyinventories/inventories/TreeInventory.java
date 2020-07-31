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
