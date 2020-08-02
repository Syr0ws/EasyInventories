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

package fr.syrows.easyinventories.contents.pagination;

import fr.syrows.easyinventories.contents.items.ClickableItem;
import fr.syrows.easyinventories.inventories.impl.PageableInventory;
import fr.syrows.easyinventories.tools.SlotValidator;
import fr.syrows.easyinventories.utils.SlotUtils;
import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;

public class PageItem {

    private PageableInventory<?> container;
    private PageType type;

    private ItemStack stack;
    private int slot;

    public PageItem(PageableInventory<?> container, PageType type, ItemStack stack, int slot) {

        SlotValidator.validateSlot(container, slot); // Checking slot.

        this.container = container;
        this.type = type;
        this.stack = stack;
        this.slot = slot;
    }

    public ClickableItem getItem() {

        String identifier = this.type.getIdentifier();
        Consumer<PageableInventory<?>> consumer = this.type.getConsumer();

        return new ClickableItem.Builder(identifier, this.stack).withClickEvent((event) -> consumer.accept(this.container)).build();
    }

    public int getSlot() {
        return this.slot;
    }

    public enum PageType {

        PREVIOUS("previous_page", PageableInventory::previousPage),
        NEXT("next_page", PageableInventory::nextPage);

        private String identifier;
        private Consumer<PageableInventory<?>> consumer; // Action performed when a player clicks on the item.

        PageType(String identifier, Consumer<PageableInventory<?>> consumer) {
            this.identifier = identifier;
            this.consumer = consumer;
        }

        public String getIdentifier() {
            return this.identifier;
        }

        public Consumer<PageableInventory<?>> getConsumer() {
            return this.consumer;
        }
    }
}
