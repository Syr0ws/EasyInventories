package fr.syrows.inventories.contents.items;

import fr.syrows.inventories.tools.Updater;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;

public class ClickableItem {

    private String id;
    private ItemStack item;

    private Consumer<InventoryClickEvent> consumer;
    private Updater<ClickableItem> updater;

    private ClickableItem(Builder builder) {

        this.id = builder.id;
        this.item = builder.item;

        this.consumer = builder.consumer;
        this.updater = builder.updater;
    }

    public String getId() {
        return this.id;
    }

    public ItemStack getItemStack() {
        return this.item;
    }

    public boolean hasConsumer() {
        return this.consumer != null;
    }

    public boolean hasUpdater() {
        return this.updater != null;
    }

    public void setConsumer(Consumer<InventoryClickEvent> consumer) {
        this.consumer = consumer;
    }

    public void setUpdater(Updater<ClickableItem> updater) {
        this.updater = updater;
    }

    public void accept(InventoryClickEvent event) {
        if(this.hasConsumer()) this.consumer.accept(event);
    }

    public void update() {
        if(this.hasUpdater()) this.updater.update(this);
    }

    public static class Builder {

        private String id;
        private ItemStack item;

        private Consumer<InventoryClickEvent> consumer;
        private Updater<ClickableItem> updater;

        public Builder(String id, ItemStack item) {
            this.id = id;
            this.item = item;
        }

        public Builder withClickEvent(Consumer<InventoryClickEvent> consumer) {
            this.consumer = consumer;
            return this;
        }

        public Builder withUpdater(Updater<ClickableItem> updater) {
            this.updater = updater;
            return this;
        }

        public ClickableItem build() {
            return new ClickableItem(this);
        }
    }
}
