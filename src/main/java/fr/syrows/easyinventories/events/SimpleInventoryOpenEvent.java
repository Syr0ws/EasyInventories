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
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class SimpleInventoryOpenEvent extends Event implements SimpleInventoryEvent {

    private static final HandlerList HANDLERS = new HandlerList();

    private Player player;
    private SimpleInventory inventory;

    public SimpleInventoryOpenEvent(Player player, SimpleInventory inventory) {
        this.player = player;
        this.inventory = inventory;
    }

    public Player getPlayer() {
        return this.player;
    }

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
