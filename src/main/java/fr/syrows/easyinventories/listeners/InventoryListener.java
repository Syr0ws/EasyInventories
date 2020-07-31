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

package fr.syrows.easyinventories.listeners;

import fr.syrows.easyinventories.events.SimpleInventoryEvent;

import java.util.function.Consumer;

public class InventoryListener<T extends SimpleInventoryEvent> {

    private Class<T> type;
    private Consumer<T> consumer;

    public InventoryListener(Class<T> type, Consumer<T> consumer) {
        this.type = type;
        this.consumer = consumer;
    }

    public Class<T> getType() {
        return this.type;
    }

    public Consumer<T> getConsumer() {
        return this.consumer;
    }

    public void accept(T event) {
        this.consumer.accept(event);
    }
}
