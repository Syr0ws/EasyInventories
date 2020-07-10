package fr.syrows.modernshop.inventories.categories.impl;

import fr.syrows.easyinventories.configs.InventoryConfig;
import fr.syrows.easyinventories.configs.sections.InventorySection;
import fr.syrows.easyinventories.configs.sections.PaginationSection;
import fr.syrows.easyinventories.configs.tools.InventoryItem;
import fr.syrows.easyinventories.contents.ContainerType;
import fr.syrows.easyinventories.contents.InventoryManager;
import fr.syrows.easyinventories.contents.impl.PageableContents;
import fr.syrows.easyinventories.contents.items.ClickableItem;
import fr.syrows.easyinventories.contents.items.PageItem;
import fr.syrows.easyinventories.events.SimpleInventoryClickEvent;
import fr.syrows.easyinventories.inventories.PageableInventory;
import fr.syrows.easyinventories.inventories.TreeInventory;
import fr.syrows.easyinventories.inventories.impl.EasyInventory;
import fr.syrows.easyinventories.tools.StringParser;
import fr.syrows.easyinventories.tools.pagination.Pagination;
import fr.syrows.easyinventories.tools.pagination.PaginationSettings;
import fr.syrows.easyinventories.utils.ItemUtils;
import fr.syrows.modernshop.ShopPlugin;
import fr.syrows.modernshop.categories.ShopCategory;
import fr.syrows.modernshop.categories.ShopCategoryManager;
import fr.syrows.modernshop.inventories.categories.CategoryContentInventory;
import fr.syrows.modernshop.inventories.categories.CategoryInventory;
import fr.syrows.modernshop.inventories.provider.InventoryProvider;
import fr.syrows.modernshop.shops.Shop;
import fr.syrows.modernshop.shops.ShopManager;
import fr.syrows.modernshop.shops.ShopType;
import fr.syrows.modernshop.shops.items.ShopItem;
import fr.syrows.modernshop.utils.parsers.ShopMessageParser;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.function.Consumer;

public class ShopViewer {

    private InventoryProvider provider;

    private ShopCategoryManager categoryManager;
    private ShopManager shopManager;

    public ShopViewer(InventoryProvider provider, ShopCategoryManager categoryManager, ShopManager shopManager) {
        this.provider = provider;
        this.categoryManager = categoryManager;
        this.shopManager = shopManager;
    }

    public void openInventory(Player player) {

        SCategoryInventory inventory = this.getCategoryInventory();

        inventory.open(player);
    }

    private SCategoryInventory getCategoryInventory() {

        InventoryConfig config = this.provider.getConfigManager().getInventoryConfig("shop_menus");

        InventorySection section = config.getSection("category-inventory");

        return new SCategoryInventory(this.categoryManager.getShopCategories(), this.provider.getInventoryManager(), section);
    }

    private SCategoryContentInventory getCategoryContentInventory(ShopCategory category) {

        InventoryConfig config = this.provider.getConfigManager().getInventoryConfig("shop_menus");

        InventorySection section = config.getSection("category-content-inventory");

        return new SCategoryContentInventory(provider.getInventoryManager(), category, section);
    }

    private ShopInventory getShopInventory(ShopItem item) {

        InventoryConfig config = this.provider.getConfigManager().getInventoryConfig("shop_menus");

        InventorySection section = config.getSection("shop-inventory");

        List<Shop> shops = this.shopManager.getShopsByPriority(item);

        return new ShopInventory(item, shops, section);
    }

    private MyShopsInventory getMyShopsInventory(Player player) {

        List<Shop> shops = this.shopManager.getShops(player.getUniqueId());

        InventoryConfig config = this.provider.getConfigManager().getInventoryConfig("shop_menus");

        InventorySection section = config.getSection("my-shops-inventory");

        return new MyShopsInventory(shops, section);
    }

    public class SCategoryInventory extends CategoryInventory {

        public SCategoryInventory(List<ShopCategory> categories, InventoryManager manager, InventorySection section) {
            super(categories, manager, section);
        }

        @Override
        protected void setAdvancedItems() {

            ConfigurationSection advanced = super.section.getConfigurationSection().getConfigurationSection("advanced-contents");

            InventoryItem myShopsItem = super.section.getInventoryItem(advanced.getConfigurationSection("my-shops"));

            ClickableItem myShopsClickable = new ClickableItem
                    .Builder("my_shops", myShopsItem.getItemStack())
                    .build();

            Consumer<SimpleInventoryClickEvent> consumer = event -> {

                Player player = (Player) event.getWhoClicked();

                if(ShopViewer.this.shopManager.countShops(player.getUniqueId()) == 0) {

                    ItemStack stack = advanced.getItemStack("my-shops.no-shop");

                    if(event.getCurrentItem().equals(stack)) return;

                    ClickableItem noShopClickable = new ClickableItem.Builder("no_shop", stack).build();

                    super.getContents().setItem(myShopsItem.getSlot(), noShopClickable);

                    Bukkit.getScheduler().runTaskLater(JavaPlugin.getPlugin(ShopPlugin.class), () -> super.getContents().setItem(myShopsItem.getSlot(), myShopsClickable), 60L);

                } else {

                    MyShopsInventory inventory = ShopViewer.this.getMyShopsInventory(player);
                    super.open(player, inventory);
                }
            };
            myShopsClickable.setConsumer(consumer);

            super.getContents().setItem(myShopsItem.getSlot(), myShopsClickable);

            super.setAdvancedItems();
        }

        @Override
        public ClickableItem getPageItem(ShopCategory category) {

            ClickableItem clickable = super.getPageItem(category);

            clickable.setConsumer(event -> {

                SCategoryContentInventory inventory = ShopViewer.this.getCategoryContentInventory(category);

                Player player = (Player) event.getWhoClicked();

                super.open(player, inventory);
            });
            return clickable;
        }
    }

    public class SCategoryContentInventory extends CategoryContentInventory {

        private TreeInventory opened;

        public SCategoryContentInventory(InventoryManager manager, ShopCategory category, InventorySection section) {
            super(manager, category, section);
        }

        @Override
        public ClickableItem getPageItem(ShopItem item) {

            ConfigurationSection advanced = super.section.getConfigurationSection().getConfigurationSection("advanced-contents");

            ClickableItem clickable = super.getPageItem(item);

            clickable.setConsumer(event -> {

                Player player = (Player) event.getWhoClicked();

                if(ShopViewer.this.shopManager.getShops(item).size() == 0) {

                    ItemStack stack = advanced.getItemStack("no-shop");

                    if(event.getCurrentItem().equals(stack)) return;

                    ClickableItem noShopClickable = new ClickableItem.Builder("no_shop", stack).build();

                    super.getContents().setItem(event.getSlot(), noShopClickable);

                    Bukkit.getScheduler().runTaskLater(JavaPlugin.getPlugin(ShopPlugin.class), () -> {

                        Pagination<ShopItem>.Page page = super.getOpenedPage();
                        int index = super.getPagination().indexOf(item);

                        if(index >= page.getBeginIndex() && index <= page.getEndIndex()) {

                            super.getContents().setItem(event.getSlot(), clickable);
                        }
                    }, 60L);

                } else {

                    ShopInventory inventory = ShopViewer.this.getShopInventory(item);
                    super.open(player, inventory);
                }
            });
            return clickable;
        }

        @Override
        public TreeInventory getOpened() {
            return this.opened;
        }

        @Override
        public void setOpened(TreeInventory inventory) {
            this.opened = inventory;
        }
    }

    public class ShopInventory extends EasyInventory implements PageableInventory<Shop>, TreeInventory {

        private PageableContents<Shop> contents;

        private Pagination<Shop> pagination;
        private Pagination<Shop>.Page page;

        private TreeInventory parent;

        private Inventory inventory;
        private InventorySection section;

        private ShopItem item;

        public ShopInventory(ShopItem item, List<Shop> shops, InventorySection section) {
            super(ShopViewer.this.provider.getInventoryManager());

            this.section = section;
            this.item = item;

            this.pagination = new Pagination<>(shops, this.getSettings().countSlots());
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

            int slot = advanced.getInt("shop-item-slot");
            this.contents.setItem(slot, new ClickableItem.Builder("shop_item", this.item.getItemStack()).build());

            this.contents.setItem(close.getSlot(), closeItem);
            this.contents.setItem(back.getSlot(), backItem);
        }

        private ItemStack getPlayerShopItem(Shop shop) {

            ConfigurationSection advanced = this.section.getConfigurationSection().getConfigurationSection("advanced-contents");

            ItemStack stack = new ItemStack(advanced.getItemStack("player-shop"));

            StringParser parser = string -> new ShopMessageParser(shop, string).parseOwner().get();

            ItemUtils.parse(stack, parser);

            if(stack.getType() == Material.SKULL_ITEM) {

                SkullMeta skullM = (SkullMeta) stack.getItemMeta();
                skullM.setOwningPlayer(Bukkit.getOfflinePlayer(shop.getOwner().getUUID()));
                stack.setItemMeta(skullM);
            }
            return stack;
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
        public Pagination<Shop> getPagination() {
            return this.pagination;
        }

        @Override
        public Pagination<Shop>.Page getOpenedPage() {
            return this.page;
        }

        @Override
        public PaginationSettings getSettings() {
            return this.section.getPaginationSection().getSettings();
        }

        @Override
        public ClickableItem getPageItem(Shop shop) {

            ConfigurationSection advanced = this.section.getConfigurationSection().getConfigurationSection("advanced-contents");

            ItemStack stack;

            if(shop.getType() == ShopType.PLAYER) stack = this.getPlayerShopItem(shop);
            else stack = new ItemStack(advanced.getItemStack("admin-shop"));

            StringParser parser = string -> new ShopMessageParser(shop, string).parseLocation().get();

            ItemUtils.parse(stack, parser);

            Consumer<SimpleInventoryClickEvent> consumer = event -> {

                Player player = (Player) event.getWhoClicked();
                player.closeInventory();

                ShopViewer.this.shopManager.teleportToShop(shop, player);
            };
            return new ClickableItem.Builder("shop#" + shop.getIdentifier(), stack).withClickEvent(consumer).build();
        }

        @Override
        public void setOpenedPage(Pagination<Shop>.Page page) {
            this.page = page;
        }

        @Override
        public String getIdentifier() {
            return "shop_inventory#" + this.item.getIdentifier();
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
        public PageableContents<Shop> getContents() {
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
        public void setOpened(TreeInventory inventory) {}
    }

    public class MyShopsInventory extends EasyInventory implements PageableInventory<Shop>, TreeInventory {

        private PageableContents<Shop> contents;

        private Pagination<Shop> pagination;
        private Pagination<Shop>.Page page;

        private TreeInventory parent;

        private Inventory inventory;
        private InventorySection section;

        public MyShopsInventory(List<Shop> shops, InventorySection section) {
            super(ShopViewer.this.provider.getInventoryManager());

            this.section = section;

            this.pagination = new Pagination<>(shops, this.getSettings().countSlots());
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
        public Pagination<Shop> getPagination() {
            return this.pagination;
        }

        @Override
        public Pagination<Shop>.Page getOpenedPage() {
            return this.page;
        }

        @Override
        public PaginationSettings getSettings() {
            return this.section.getPaginationSection().getSettings();
        }

        @Override
        public ClickableItem getPageItem(Shop shop) {

            Consumer<SimpleInventoryClickEvent> consumer = event -> {

                Player player = (Player) event.getWhoClicked();

                player.closeInventory();

                ShopViewer.this.shopManager.teleportToShop(shop, player);
            };
            // Deserialized ItemStack seems conserved by Spigot in memory after the first deserialization.
            // to not do it again several times. So, if the item is modified, all the items which will be
            // retrieved after the first deserialization will also be modified.
            ItemStack original = this.section.getConfigurationSection().getItemStack("advanced-contents.shop");

            // So the solution is to retrieve the original ItemStack and to modify another instance of it.
            ItemStack stack = new ItemStack(original);

            StringParser parser = string -> new ShopMessageParser(shop, string).parseLocation().get();

            ItemUtils.parse(stack, parser);

            return new ClickableItem.Builder("shop_item", stack).withClickEvent(consumer).build();
        }

        @Override
        public void setOpenedPage(Pagination<Shop>.Page page) {
            this.page = page;
        }

        @Override
        public String getIdentifier() {
            return "my_shops_inventory";
        }

        @Override
        public String getTitle() {
            return this.section.getInventoryTitle();
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
        public PageableContents<Shop> getContents() {
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
        public void setOpened(TreeInventory inventory) {}
    }
}
