package fr.syrows.modernshop.inventories.categories;

import fr.syrows.easyinventories.configs.sections.InventorySection;
import fr.syrows.easyinventories.configs.sections.PaginationSection;
import fr.syrows.easyinventories.configs.tools.InventoryItem;
import fr.syrows.easyinventories.contents.ContainerType;
import fr.syrows.easyinventories.contents.InventoryManager;
import fr.syrows.easyinventories.contents.impl.PageableContents;
import fr.syrows.easyinventories.contents.items.ClickableItem;
import fr.syrows.easyinventories.contents.items.PageItem;
import fr.syrows.easyinventories.inventories.PageableInventory;
import fr.syrows.easyinventories.inventories.TreeInventory;
import fr.syrows.easyinventories.inventories.impl.EasyInventory;
import fr.syrows.easyinventories.tools.pagination.Pagination;
import fr.syrows.easyinventories.tools.pagination.PaginationSettings;
import fr.syrows.modernshop.categories.ShopCategory;
import fr.syrows.modernshop.shops.items.ShopItem;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.List;

public class CategoryContentInventory extends EasyInventory implements PageableInventory<ShopItem>, TreeInventory {

    protected InventorySection section;

    private ShopCategory category;

    private PageableContents<ShopItem> contents;

    private Pagination<ShopItem> pagination;
    private Pagination<ShopItem>.Page page;

    private TreeInventory parent;

    private Inventory inventory;

    public CategoryContentInventory(InventoryManager manager, ShopCategory category, InventorySection section) {
        super(manager);

        this.category = category;
        this.section = section;

        this.pagination = new Pagination<>(category.getContents(), this.getSettings().countSlots());
        this.contents = new PageableContents<>(this);

        this.inventory = this.getInventoryManager().create(this);

        this.setContents();
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

        InventoryItem close = this.section.getInventoryItem(advanced.getConfigurationSection("close"));

        ClickableItem closeItem = new ClickableItem
                .Builder("close", close.getItemStack())
                .withClickEvent(event -> event.getWhoClicked().closeInventory())
                .build();

        InventoryItem back = this.section.getInventoryItem(advanced.getConfigurationSection("back"));

        ClickableItem backItem = new ClickableItem
                .Builder("back", back.getItemStack())
                .withClickEvent(event -> TreeInventory.super.back((Player) event.getWhoClicked()))
                .build();

        this.contents.setItem(close.getSlot(), closeItem);
        this.contents.setItem(back.getSlot(), backItem);
    }

    @Override
    public PageItem getNextPage() {

        PaginationSection section = this.section.getPaginationSection();

        InventoryItem item = section.getPageItem(PageItem.PageType.NEXT, !this.pagination.hasNext(this.page));

        return new PageItem(this, PageItem.PageType.NEXT, item.getItemStack(), item.getSlot());
    }

    @Override
    public PageItem getPreviousPage() {

        PaginationSection section = this.section.getPaginationSection();

        InventoryItem item = section.getPageItem(PageItem.PageType.PREVIOUS, !this.pagination.hasPrevious(this.page));

        return new PageItem(this, PageItem.PageType.PREVIOUS, item.getItemStack(), item.getSlot());
    }

    @Override
    public Pagination<ShopItem> getPagination() {
        return this.pagination;
    }

    @Override
    public Pagination<ShopItem>.Page getOpenedPage() {
        return this.page;
    }

    @Override
    public ClickableItem getPageItem(ShopItem item) {
        return new ClickableItem.Builder(item.getIdentifier(), item.getItemStack()).build();
    }

    @Override
    public PaginationSettings getSettings() {
        return this.section.getPaginationSection().getSettings();
    }

    @Override
    public void setOpenedPage(Pagination<ShopItem>.Page page) {
        this.page = page;
    }

    @Override
    public String getIdentifier() {
        return "category_content_inventory";
    }

    @Override
    public String getTitle() {

        String title = this.section.getInventoryTitle();

        return title.replace("%category_name%", this.category.getName());
    }

    @Override
    public int getSize() {
        return 45;
    }

    @Override
    public ContainerType getType() {
        return ContainerType.CHEST;
    }

    @Override
    public PageableContents<ShopItem> getContents() {
        return this.contents;
    }

    @Override
    public Inventory getInventory() {
        return this.inventory;
    }

    @Override
    public TreeInventory getParent() {
        return this.parent;
    }

    @Override
    public void setParent(TreeInventory inventory) {
        this.parent = inventory;
    }

    @Override
    public TreeInventory getOpened() {
        return null;
    }

    @Override
    public void setOpened(TreeInventory treeInventory) {

    }
}
