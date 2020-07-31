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
import fr.syrows.easyinventories.contents.containers.InventorySort;
import fr.syrows.easyinventories.listeners.InventoryListenerManager;
import fr.syrows.easyinventories.manager.InventoryManager;
import fr.syrows.easyinventories.tools.CloseReason;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public interface SimpleInventory {

    String getIdentifier();

    String getTitle();

    int getSize();

    int getRows();

    int getColumns();

    InventorySort getContainer();

    InventoryContents getContents();

    InventoryManager getInventoryManager();

    InventoryListenerManager getListenerManager();

    Inventory getInventory();

    void open(Player player);

    void close(Player player, CloseReason reason);
}
