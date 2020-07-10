package fr.syrows.modernshop;

import fr.syrows.modernshop.categories.ShopCategoryManager;
import fr.syrows.modernshop.commands.CommandModernShop;
import fr.syrows.modernshop.commands.CommandShops;
import fr.syrows.modernshop.economy.EconomyManager;
import fr.syrows.modernshop.inventories.provider.InventoryProvider;
import fr.syrows.modernshop.listeners.ShopBlockListeners;
import fr.syrows.modernshop.listeners.ShopCreationListeners;
import fr.syrows.modernshop.logs.CustomLogger;
import fr.syrows.modernshop.shops.ShopManager;
import fr.syrows.modernshop.teleportations.TeleportationManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.logging.Level;

public class ShopPlugin extends JavaPlugin {

    private static CustomLogger logger;

    private ShopCategoryManager categoryManager;
    private ShopManager shopManager;

    private EconomyManager economyManager;
    private TeleportationManager tpManager;

    private InventoryProvider inventoryProvider;

    @Override
    public void onEnable() {

        this.loadConfiguration();
        this.setupLogger();

        this.setupCategories();
        this.setupShops();

        this.setupEconomy();
        this.setupTeleportations();
        this.setupInventories();

        this.registerCommands();
        this.registerListeners();
    }

    @Override
    public void onDisable() {

        this.tpManager.end();
    }

    public void onReload() {

        this.reloadConfig();

        logger.broadcast(Level.INFO, "File 'config.yml' has been reloaded successfully.");

        this.inventoryProvider.getConfigManager().reloadAll();

        logger.broadcast(Level.INFO, "Inventory files have been reloaded successfully.");

        this.categoryManager.loadCategories();
    }

    private void loadConfiguration() {
        super.getConfig().options().copyDefaults(true);
        super.saveDefaultConfig();
    }

    private void registerCommands() {

        super.getCommand("shops").setExecutor(new CommandShops(this));
        super.getCommand("modernshop").setExecutor(new CommandModernShop(this));
    }

    private void registerListeners() {

        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new ShopCreationListeners(this), this);
        pm.registerEvents(new ShopBlockListeners(this), this);
    }

    private void setupLogger() {

        logger = new CustomLogger(this);

        try { logger.enable(); }
        catch (IOException e) { e.printStackTrace(); }
    }

    private void setupCategories() {

        this.categoryManager = new ShopCategoryManager(this);
        this.categoryManager.loadCategories();
    }

    private void setupShops() {
        this.shopManager = new ShopManager(this);
        this.shopManager.init();
    }

    private void setupEconomy() {

        this.economyManager = new EconomyManager();

        boolean economySetup = this.economyManager.setupEconomy();

        if(!economySetup) Bukkit.getPluginManager().disablePlugin(this);
    }

    private void setupInventories() {

        this.inventoryProvider = new InventoryProvider(this);
        this.inventoryProvider.init();
    }

    private void setupTeleportations() {
        this.tpManager = new TeleportationManager(this);
        this.tpManager.init();
    }

    public ShopCategoryManager getShopCategoryManager() {
        return this.categoryManager;
    }

    public ShopManager getShopManager() {
        return this.shopManager;
    }

    public TeleportationManager getTeleportationManager() {
        return this.tpManager;
    }

    public EconomyManager getEconomyManager() {
        return this.economyManager;
    }

    public InventoryProvider getInventoryProvider() {
        return this.inventoryProvider;
    }

    public static CustomLogger getCustomLogger() {
        return logger;
    }
}
