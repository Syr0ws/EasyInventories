package fr.syrows.easyinventories;

import fr.syrows.easyinventories.contents.InventoryManager;
import org.bukkit.plugin.java.JavaPlugin;

public class EasyInventoriesPlugin extends JavaPlugin {

    private InventoryManager inventoryManager;

    @Override
    public void onEnable() {

        this.inventoryManager = new InventoryManager(this);
    }

    public InventoryManager getInventoryManager() {
        return this.inventoryManager;
    }
}
