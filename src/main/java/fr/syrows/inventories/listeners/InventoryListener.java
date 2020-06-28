package fr.syrows.inventories.listeners;

import org.bukkit.event.inventory.InventoryEvent;

import java.util.function.Consumer;

public class InventoryListener<T extends InventoryEvent> {

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
