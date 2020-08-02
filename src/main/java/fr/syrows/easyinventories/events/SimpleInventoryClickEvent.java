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

import fr.syrows.easyinventories.contents.InventoryContents;
import fr.syrows.easyinventories.contents.items.ClickableItem;
import fr.syrows.easyinventories.inventories.SimpleInventory;
import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.Optional;

public class SimpleInventoryClickEvent extends InventoryClickEvent implements SimpleInventoryEvent {

    private static final HandlerList HANDLERS = new HandlerList();

    private SimpleInventory inventory;

    public SimpleInventoryClickEvent(InventoryClickEvent event, SimpleInventory inventory) {
        super(event.getView(), event.getSlotType(), event.getSlot(), event.getClick(), event.getAction());
        this.inventory = inventory;
    }

    /**
     * Returns the clicked inventory.
     *
     * @return a SimpleInventory object.
     */
    @Override
    public SimpleInventory getSimpleInventory() {
        return this.inventory;
    }

    /**
     * Returns an optional that contains the clicked item.
     * If the clicked item is null, the an empty optional will be returned.
     *
     * @return an Optional.
     */
    public Optional<ClickableItem> getClickableItem() {

        InventoryContents contents = this.inventory.getContents();

        return contents.getItem(super.getSlot());
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
