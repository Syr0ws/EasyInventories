package fr.syrows;

import fr.syrows.inventories.InventoryManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class EasyInventoriesPlugin extends JavaPlugin {

    private InventoryManager manager;

    @Override
    public void onEnable() {
        this.manager = new InventoryManager(this);

        // To remove.
        Bukkit.getPluginManager().registerEvents(new Test(this), this);
    }

    public InventoryManager getDefaultManager() {
        return this.manager;
    }
}
