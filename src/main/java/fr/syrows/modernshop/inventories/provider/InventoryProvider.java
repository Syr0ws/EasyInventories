package fr.syrows.modernshop.inventories.provider;

import fr.syrows.easyinventories.configs.InventoryConfig;
import fr.syrows.easyinventories.configs.InventoryConfigManager;
import fr.syrows.easyinventories.configs.sections.InventorySection;
import fr.syrows.easyinventories.contents.InventoryManager;
import fr.syrows.modernshop.shops.ShopManager;
import fr.syrows.modernshop.shops.tools.ShopDeletionTool;
import fr.syrows.modernshop.ShopPlugin;
import fr.syrows.modernshop.categories.ShopCategoryManager;
import fr.syrows.modernshop.inventories.ShopDeleteInventory;
import fr.syrows.modernshop.inventories.categories.impl.ShopItemSelector;
import fr.syrows.modernshop.inventories.categories.impl.ShopViewer;
import org.bukkit.Bukkit;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class InventoryProvider {

    private static final String SHOP_MENUS_FILE = "menus.yml";

    private ShopPlugin plugin;

    private InventoryManager inventoryManager;
    private InventoryConfigManager configManager;

    public InventoryProvider(ShopPlugin plugin) {
        this.plugin = plugin;
    }

    public void init() {

        this.inventoryManager = new InventoryManager(this.plugin);

        this.setupConfigs();
    }

    private void setupConfigs() {

        this.configManager = new InventoryConfigManager(this.plugin);

        try {

            this.registerConfigs();

        } catch (IOException e) {

            e.printStackTrace();

            Bukkit.getPluginManager().disablePlugin(this.plugin);
        }
    }

    private void registerConfigs() throws IOException {

        Path shopsMenuPath = Paths.get(this.plugin.getDataFolder() + "/" + SHOP_MENUS_FILE);

        this.configManager.registerConfig("shop_menus", SHOP_MENUS_FILE, shopsMenuPath);
    }

    public ShopDeleteInventory getDeleteInventory(ShopDeletionTool deletion) {

        InventoryConfig config = this.configManager.getInventoryConfig("shop_menus");
        InventorySection section = config.getSection("shop-remove-inventory");

        return new ShopDeleteInventory(this.inventoryManager, section, deletion);
    }

    public ShopItemSelector getItemSelector(ShopCategoryManager manager) {
        return new ShopItemSelector(this, manager.getShopCategories());
    }

    public ShopViewer getShopViewer(ShopCategoryManager categoryManager, ShopManager shopManager) {
        return new ShopViewer(this, categoryManager, shopManager);
    }

    public InventoryManager getInventoryManager() {
        return this.inventoryManager;
    }

    public InventoryConfigManager getConfigManager() {
        return this.configManager;
    }
}
