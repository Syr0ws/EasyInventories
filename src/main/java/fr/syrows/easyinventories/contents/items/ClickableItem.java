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

package fr.syrows.easyinventories.contents.items;

import fr.syrows.easyinventories.events.SimpleInventoryClickEvent;
import fr.syrows.easyinventories.tools.Updater;
import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;

public class ClickableItem {

    private String id;
    private ItemStack item;

    private Consumer<SimpleInventoryClickEvent> consumer;
    private Updater<ClickableItem> updater;

    private ClickableItem() {}

    public String getId() {
        return this.id;
    }

    public ItemStack getItemStack() {
        return this.item;
    }

    public void setItemStack(ItemStack item) {

        if(item == null)
            throw new IllegalArgumentException("ItemStack cannot be null.");

        this.item = item;
    }

    public boolean hasConsumer() {
        return this.consumer != null;
    }

    public boolean hasUpdater() {
        return this.updater != null;
    }

    public void setConsumer(Consumer<SimpleInventoryClickEvent> consumer) {
        this.consumer = consumer;
    }

    public void setUpdater(Updater<ClickableItem> updater) {
        this.updater = updater;
    }

    public void accept(SimpleInventoryClickEvent event) {
        if(this.hasConsumer()) this.consumer.accept(event);
    }

    public void update() {
        if(this.hasUpdater()) this.updater.update(this);
    }

    public static class Builder {

        private String id;
        private ItemStack item;

        private Consumer<SimpleInventoryClickEvent> consumer;
        private Updater<ClickableItem> updater;

        public Builder(String id, ItemStack item) {
            this.id = id;
            this.item = item;
        }

        public Builder withClickEvent(Consumer<SimpleInventoryClickEvent> consumer) {
            this.consumer = consumer;
            return this;
        }

        public Builder withUpdater(Updater<ClickableItem> updater) {
            this.updater = updater;
            return this;
        }

        public ClickableItem build() {

            ClickableItem item = new ClickableItem();

            if(this.id == null)
                throw new NullPointerException("ID cannot be null.");

            item.setItemStack(this.item);
            item.id = this.id;

            item.consumer = this.consumer;
            item.updater = this.updater;

            return item;
        }
    }
}
