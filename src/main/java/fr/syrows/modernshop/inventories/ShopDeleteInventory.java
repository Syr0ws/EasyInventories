package fr.syrows.modernshop.inventories;

import fr.syrows.easyinventories.configs.sections.InventorySection;
import fr.syrows.easyinventories.configs.tools.InventoryItem;
import fr.syrows.easyinventories.contents.ContainerType;
import fr.syrows.easyinventories.contents.InventoryContents;
import fr.syrows.easyinventories.contents.InventoryManager;
import fr.syrows.easyinventories.contents.impl.DefaultContents;
import fr.syrows.easyinventories.contents.items.ClickableItem;
import fr.syrows.easyinventories.events.SimpleInventoryCloseEvent;
import fr.syrows.easyinventories.inventories.impl.EasyInventory;
import fr.syrows.easyinventories.listeners.InventoryListener;
import fr.syrows.modernshop.shops.tools.ShopDeletionTool;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.Inventory;

import java.util.List;

public class ShopDeleteInventory extends EasyInventory {

    private InventoryContents contents;
    private InventorySection section;
    private Inventory inventory;

    private ShopDeletionTool deletion;
    private boolean cancelled;

    public ShopDeleteInventory(InventoryManager manager, InventorySection section, ShopDeletionTool deletion) {
        super(manager);

        this.section = section;
        this.contents = new DefaultContents(this);
        this.inventory = manager.create(this);

        this.deletion = deletion;
        this.cancelled = true;

        this.setContents();
        this.addListeners();
    }

    private void setContents() {

        List<InventoryItem> items = this.section.getInventoryContents();

        items.forEach(item -> {

            ClickableItem clickable = new ClickableItem.Builder("simple_items", item.getItemStack()).build();

            this.contents.setItems(clickable, item.getSlots());
        });
        this.setAdvancedItems();
    }

    private void setAdvancedItems() {

        ConfigurationSection advanced = this.section.getConfigurationSection().getConfigurationSection("advanced-contents");

        InventoryItem validateItem = this.section.getInventoryItem(advanced.getConfigurationSection("validate"));
        InventoryItem cancelItem = this.section.getInventoryItem(advanced.getConfigurationSection("cancel"));

        ClickableItem validate = new ClickableItem
                .Builder("validate", validateItem.getItemStack())
                .withClickEvent(event -> {
                    this.cancelled = false;
                    this.deletion.confirm();
                    event.getWhoClicked().closeInventory();
                })
                .build();

        ClickableItem cancel = new ClickableItem
                .Builder("cancel", cancelItem.getItemStack())
                .withClickEvent(event -> event.getWhoClicked().closeInventory())
                .build();

        this.contents.setItem(validateItem.getSlot(), validate);
        this.contents.setItem(cancelItem.getSlot(), cancel);
    }

    private void addListeners() {

        InventoryListener<SimpleInventoryCloseEvent> listener = new InventoryListener<>(SimpleInventoryCloseEvent.class,
                event -> { if(this.cancelled) this.deletion.cancel(); });

        super.getListenerManager().addListener(listener);
    }

    @Override
    public String getIdentifier() {
        return "shop_remove_inventory";
    }

    @Override
    public String getTitle() {
        return this.section.getInventoryTitle();
    }

    @Override
    public int getSize() {
        return 27;
    }

    @Override
    public ContainerType getType() {
        return ContainerType.CHEST;
    }

    @Override
    public InventoryContents getContents() {
        return this.contents;
    }

    @Override
    public Inventory getInventory() {
        return this.inventory;
    }
}
