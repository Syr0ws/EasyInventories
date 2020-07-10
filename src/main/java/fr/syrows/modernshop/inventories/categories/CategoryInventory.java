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
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.Inventory;

import java.util.List;

public class CategoryInventory extends EasyInventory implements PageableInventory<ShopCategory>, TreeInventory {

    protected InventorySection section;

    private PageableContents<ShopCategory> contents;

    private Pagination<ShopCategory> pagination;
    private Pagination<ShopCategory>.Page page;

    private TreeInventory opened;
    private Inventory inventory;

    public CategoryInventory(List<ShopCategory> categories, InventoryManager manager, InventorySection section) {

        super(manager);

        this.section = section;

        this.pagination = new Pagination<>(categories, this.getSettings().countSlots());
        this.contents = new PageableContents<>(this);

        this.inventory = this.getInventoryManager().create(this);

        this.setContents();
    }

    protected void setContents() {

        List<InventoryItem> items = this.section.getInventoryContents();

        items.forEach(item -> {

            ClickableItem clickable = new ClickableItem.Builder("simple_items", item.getItemStack()).build();

            this.contents.setItems(clickable, item.getSlots());
        });
        this.setAdvancedItems();
    }

    protected void setAdvancedItems() {

        ConfigurationSection advanced = this.section.getConfigurationSection().getConfigurationSection("advanced-contents");

        InventoryItem close = this.section.getInventoryItem(advanced.getConfigurationSection("close"));

        ClickableItem closeItem = new ClickableItem
                .Builder("close", close.getItemStack())
                .withClickEvent(event -> event.getWhoClicked().closeInventory())
                .build();

        this.contents.setItem(close.getSlot(), closeItem);
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
    public Pagination<ShopCategory> getPagination() {
        return this.pagination;
    }

    @Override
    public Pagination<ShopCategory>.Page getOpenedPage() {
        return this.page;
    }

    @Override
    public PaginationSettings getSettings() {
        return this.section.getPaginationSection().getSettings();
    }

    @Override
    public ClickableItem getPageItem(ShopCategory category) {
        return new ClickableItem.Builder(category.getIdentifier(), category.getItem()).build();
    }

    @Override
    public void setOpenedPage(Pagination<ShopCategory>.Page page) {
        this.page = page;
    }

    @Override
    public String getIdentifier() {
        return "main_category_inventory";
    }

    @Override
    public String getTitle() {
        return this.section.getInventoryTitle();
    }

    @Override
    public int getSize() {
        return 54;
    }

    @Override
    public ContainerType getType() {
        return ContainerType.CHEST;
    }

    @Override
    public PageableContents<ShopCategory> getContents() {
        return this.contents;
    }

    @Override
    public Inventory getInventory() {
        return this.inventory;
    }

    @Override
    public TreeInventory getParent() {
        return null;
    }

    @Override
    public void setParent(TreeInventory treeInventory) {}

    @Override
    public TreeInventory getOpened() {
        return this.opened;
    }

    @Override
    public void setOpened(TreeInventory inventory) {
        this.opened = inventory;
    }
}
