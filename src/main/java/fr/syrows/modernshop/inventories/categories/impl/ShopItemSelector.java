package fr.syrows.modernshop.inventories.categories.impl;

import fr.syrows.easyinventories.configs.InventoryConfig;
import fr.syrows.easyinventories.configs.sections.InventorySection;
import fr.syrows.easyinventories.contents.InventoryManager;
import fr.syrows.easyinventories.contents.items.ClickableItem;
import fr.syrows.easyinventories.tools.CloseReason;
import fr.syrows.modernshop.categories.ShopCategory;
import fr.syrows.modernshop.inventories.provider.InventoryProvider;
import fr.syrows.modernshop.inventories.categories.CategoryContentInventory;
import fr.syrows.modernshop.inventories.categories.CategoryInventory;
import fr.syrows.modernshop.shops.items.ShopItem;
import org.bukkit.entity.Player;

import java.util.List;

public class ShopItemSelector {

    private InventoryProvider provider;
    private List<ShopCategory> categories;

    private ShopItem item;

    public ShopItemSelector(InventoryProvider provider, List<ShopCategory> categories) {
        this.provider = provider;
        this.categories = categories;
    }

    public void openInventory(Player player) {

        SCategoryInventory inventory = this.getCategoryInventory();

        inventory.open(player);
    }

    private SCategoryInventory getCategoryInventory() {

        InventoryConfig config = this.provider.getConfigManager().getInventoryConfig("shop_menus");

        InventorySection section = config.getSection("category-inventory");

        return new SCategoryInventory(this.categories, this.provider.getInventoryManager(), section);
    }

    private SCategoryContentInventory getCategoryContentInventory(ShopCategory category) {

        InventoryConfig config = this.provider.getConfigManager().getInventoryConfig("shop_menus");

        InventorySection section = config.getSection("category-content-inventory");

        return new SCategoryContentInventory(provider.getInventoryManager(), category, section);
    }

    public ShopItem getSelectedItem() {
        return this.item;
    }

    public class SCategoryInventory extends CategoryInventory {

        public SCategoryInventory(List<ShopCategory> categories, InventoryManager manager, InventorySection section) {
            super(categories, manager, section);
        }

        @Override
        public ClickableItem getPageItem(ShopCategory category) {

            ClickableItem clickable = super.getPageItem(category);

            clickable.setConsumer(event -> {

                Player player = (Player) event.getWhoClicked();

                SCategoryContentInventory inventory = ShopItemSelector.this.getCategoryContentInventory(category);

                super.open(player, inventory);
            });
            return clickable;
        }
    }

    public class SCategoryContentInventory extends CategoryContentInventory {

        public SCategoryContentInventory(InventoryManager manager, ShopCategory category, InventorySection section) {
            super(manager, category, section);
        }

        @Override
        public ClickableItem getPageItem(ShopItem item) {

            ClickableItem clickable = super.getPageItem(item);

            clickable.setConsumer(event -> {

                ShopItemSelector.this.item = item;

                Player player = (Player) event.getWhoClicked();

                player.closeInventory();
            });
            return clickable;
        }
    }
}
