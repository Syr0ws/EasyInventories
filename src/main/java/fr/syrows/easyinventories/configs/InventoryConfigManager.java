package fr.syrows.easyinventories.configs;

import fr.syrows.easyinventories.utils.FileUtils;
import org.bukkit.plugin.Plugin;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;

public class InventoryConfigManager {

    private Plugin plugin;
    private HashMap<String, InventoryConfig> configs;

    public InventoryConfigManager(Plugin plugin) {
        this.plugin = plugin;
        this.configs = new HashMap<>();
    }

    public void registerConfig(String identifier, String resourcePath, Path path) throws IOException {

        if(this.configs.containsKey(identifier))
            throw new UnsupportedOperationException(String.format("An InventoryFile with the id '%s' already exists.", identifier));

        FileUtils.copyFromResources(this.plugin, path, resourcePath);

        this.configs.put(identifier, new InventoryConfig(path));
    }

    public void unregisterConfig(String identifier) {
        this.configs.remove(identifier);
    }

    public boolean isConfigRegistered(String identifier) {
        return this.configs.containsKey(identifier);
    }

    public InventoryConfig getInventoryConfig(String identifier) {

        if(!this.isConfigRegistered(identifier))
            throw new NullPointerException(String.format("No InventoryFile found with the id '%s'.", identifier));

        return this.configs.get(identifier);
    }

    public void reloadAll() {
        this.configs.values().forEach(InventoryConfig::reloadConfig);
    }
}
