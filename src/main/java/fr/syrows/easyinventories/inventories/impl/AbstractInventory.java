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

package fr.syrows.easyinventories.inventories.impl;

import fr.syrows.easyinventories.inventories.SimpleInventory;
import fr.syrows.easyinventories.listeners.InventoryListenerManager;
import fr.syrows.easyinventories.manager.InventoryManager;
import fr.syrows.easyinventories.tools.CloseReason;
import fr.syrows.easyinventories.utils.SlotUtils;
import org.bukkit.entity.Player;

public abstract class AbstractInventory implements SimpleInventory {

    private InventoryManager inventoryManager;
    private InventoryListenerManager listenerManager;

    public AbstractInventory(InventoryManager manager) {
        this.inventoryManager = manager;
        this.listenerManager = new InventoryListenerManager();
    }

    @Override
    public void open(Player player) {
        this.getInventoryManager().openInventory(player, this);
    }

    @Override
    public void close(Player player, CloseReason reason) {
        this.getInventoryManager().closeInventory(player, reason);
    }

    @Override
    public int getRows() {
        return SlotUtils.getRow(this.getContainer(), this.getSize());
    }

    @Override
    public int getColumns() {
        return getContainer().getDefaultColumns();
    }

    @Override
    public InventoryManager getInventoryManager() {
        return this.inventoryManager;
    }

    @Override
    public InventoryListenerManager getListenerManager() {
        return this.listenerManager;
    }
}
