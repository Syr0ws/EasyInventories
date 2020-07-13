package fr.syrows.easyinventories.configs;

import fr.syrows.easyinventories.configs.api.ConfigContainer;
import org.bukkit.plugin.Plugin;

import java.io.IOException;
import java.util.HashMap;

public class InventoryConfigManager {

    private HashMap<String, InventoryConfigContainer> configs = new HashMap<>();

    public void registerConfig(String identifier, InventoryConfigContainer container) {

        if(this.configs.containsKey(identifier))
            throw new UnsupportedOperationException(String.format("An InventoryConfig with the id '%s' already exists.", identifier));

        this.configs.put(identifier, container);

        container.reloadConfig();
    }

    public void unregisterConfig(String identifier) {
        this.configs.remove(identifier);
    }

    public boolean isConfigRegistered(String identifier) {
        return this.configs.containsKey(identifier);
    }

    public InventoryConfigContainer getInventoryConfig(String identifier) {

        if(!this.isConfigRegistered(identifier))
            throw new NullPointerException(String.format("No InventoryFile found with the id '%s'.", identifier));

        return this.configs.get(identifier);
    }

    public void reloadAll() {
        this.configs.values().forEach(ConfigContainer::reloadConfig);
    }
}
