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

package fr.syrows.easyinventories.events;

import fr.syrows.easyinventories.inventories.SimpleInventory;
import fr.syrows.easyinventories.tools.CloseReason;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class SimpleInventoryCloseEvent extends Event implements SimpleInventoryEvent {

    private static final HandlerList HANDLERS = new HandlerList();

    private Player player;
    private SimpleInventory inventory;
    private CloseReason reason;

    public SimpleInventoryCloseEvent(Player player, SimpleInventory inventory, CloseReason reason) {
        this.player = player;
        this.inventory = inventory;
        this.reason = reason;
    }

    /**
     * Get the player for whom his inventory has been closed.
     *
     * @return a Player object.
     */
    public Player getPlayer() {
        return this.player;
    }

    /**
     * Get the reason of the inventory closure.
     *
     * @return the reason of the closure.
     */
    public CloseReason getReason() {
        return this.reason;
    }

    /**
     * Get the inventory closed.
     *
     * @return a SimpleInventory object.
     */
    @Override
    public SimpleInventory getSimpleInventory() {
        return this.inventory;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
