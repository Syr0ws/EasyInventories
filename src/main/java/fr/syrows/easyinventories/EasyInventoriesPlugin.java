package fr.syrows.easyinventories;

import fr.syrows.easyinventories.manager.DefaultInventoryManager;
import fr.syrows.easyinventories.manager.InventoryManager;
import org.bukkit.plugin.java.JavaPlugin;

public class EasyInventoriesPlugin extends JavaPlugin {

    private InventoryManager inventoryManager;

    @Override
    public void onEnable() {
        this.inventoryManager = new DefaultInventoryManager(this);
    }

    public InventoryManager getInventoryManager() {
        return this.inventoryManager;
    }
}
