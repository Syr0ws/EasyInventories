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

    /**
     * Create a new InventoryListener object.
     *
     * @param type the class of the listener.
     * @param consumer the consumer which will be performed when the listener is called.
     */
    public InventoryListener(Class<T> type, Consumer<T> consumer) {
        this.type = type;
        this.consumer = consumer;
    }

    /**
     * The type of the listener.
     *
     * @return a class of type T.
     */
    public Class<T> getType() {
        return this.type;
    }

    /**
     * The consumer to perform when the listener is called.
     *
     * @return a consumer of type T.
     */
    public Consumer<T> getConsumer() {
        return this.consumer;
    }

    /**
     * The event to accept with the consumer.
     *
     * @param event an object of type T.
     */
    public void accept(T event) {
        this.consumer.accept(event);
    }
}
