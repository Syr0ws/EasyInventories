package fr.syrows;

import fr.syrows.inventories.InventoryManager;
import fr.syrows.inventories.configs.InventoryConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class EasyInventoriesPlugin extends JavaPlugin {

    private InventoryManager inventoryManager;
    private InventoryConfigManager configManager;

    @Override
    public void onEnable() {

        this.inventoryManager = new InventoryManager(this);
        this.configManager = new InventoryConfigManager();

        // To remove.
        Bukkit.getPluginManager().registerEvents(new Test(this), this);
    }

    public InventoryManager getInventoryManager() {
        return this.inventoryManager;
    }

    public InventoryConfigManager getConfigManager() {
        return this.configManager;
    }
}
