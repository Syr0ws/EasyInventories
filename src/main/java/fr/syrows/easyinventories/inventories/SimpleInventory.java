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

import fr.syrows.easyinventories.contents.InventoryContents;
import fr.syrows.easyinventories.contents.sort.InventorySort;
import fr.syrows.easyinventories.listeners.InventoryListenerManager;
import fr.syrows.easyinventories.manager.InventoryManager;
import fr.syrows.easyinventories.tools.CloseReason;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public interface SimpleInventory {

    /**
     * Returns the id of the inventory.
     *
     * @return an object of type String.
     */
    String getId();

    /**
     * Returns the title of the inventory. Title must not exceed 32 characters.
     *
     * @return an object of type String.
     */
    String getTitle();

    /**
     * Returns the size of the inventory.
     *
     * @return an object of type int.
     */
    int getSize();

    /**
     * Returns the number of rows of the inventory.
     *
     * @return an object of type int.
     */
    int getRows();

    /**
     * Returns the number of columns of the inventory.
     *
     * @return an object of type int.
     */
    int getColumns();

    /**
     * Returns the sort of the inventory.
     *
     * @return an object of type InventorySort.
     */
    InventorySort getSort();

    /**
     * Returns an object used to manager the contents of the inventory.
     *
     * @return an object of type InventoryContents.
     */
    InventoryContents getContents();

    /**
     * Returns the manager used by this inventory.
     *
     * @return an object of type InventoryManager.
     */
    InventoryManager getInventoryManager();

    /**
     * Returns the listener manager used by this inventory. This manager will be
     * used to handle the listeners directly while creating the inventory instead
     * of putting them in other classes.
     *
     * @return an object of type InventoryListenerManager.
     */
    InventoryListenerManager getListenerManager();

    /**
     * Get the Bukkit inventory that corresponds to this inventory.
     *
     * @return an object of type Inventory.
     */
    Inventory getInventory();

    /**
     * Open the inventory for the specified player.
     *
     * @param player - an object of type Player for the one the inventory will be opened.
     */
    void open(Player player);

    /**
     * Close the inventory for the specified player.
     *
     * @param player - an object of type Player for the one the inventory will be closed.
     * @param reason - an object of type CloseReason which corresponds to the reason of the closure.
     */
    void close(Player player, CloseReason reason);
}
