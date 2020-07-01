package fr.syrows.easyinventories.listeners;

import org.bukkit.event.inventory.InventoryEvent;

import java.util.ArrayList;
import java.util.List;

public class InventoryListenerManager {

    private List<InventoryListener<? extends InventoryEvent>> listeners;

    public InventoryListenerManager() {
        this.listeners = new ArrayList<>();
    }

    public void addListener(InventoryListener<? extends InventoryEvent> listener) {
        this.listeners.add(listener);
    }

    public void removeListener(InventoryListener<? extends InventoryEvent> listener) {
        this.listeners.remove(listener);
    }

    public void removeListeners(Class<? extends InventoryListener<? extends InventoryEvent>> type) {
        this.listeners.removeIf(listener -> listener.getClass().equals(type));
    }

    public boolean hasListener(Class<? extends InventoryListener<? extends InventoryEvent>> type) {
        return this.listeners.stream().anyMatch(listener -> listener.getClass().equals(type));
    }

    @SuppressWarnings("unchecked")
    public <T extends InventoryEvent> void accept(T event) {
        this.listeners.stream()
                .filter(listener -> event.getClass().equals(listener.getType()))
                .map(listener -> (InventoryListener<T>) listener)
                .forEach(listener -> listener.accept(event));
    }
}
