package fr.syrows.inventories.configs;

import java.io.IOException;
import java.util.HashMap;

public class InventoryConfigManager {

    private HashMap<String, InventoryConfig> files = new HashMap<>();

    public void registerConfig(String identifier, InventoryConfig file) {

        if(this.files.containsKey(identifier))
            throw new UnsupportedOperationException(String.format("An InventoryFile with the id '%s' already exists.", identifier));

        this.files.put(identifier, file);
    }

    public void unregisterConfig(String identifier) {
        this.files.remove(identifier);
    }

    public boolean isConfigRegistered(String identifier) {
        return this.files.containsKey(identifier);
    }

    public InventoryConfig getInventoryConfig(String identifier) {

        if(!this.isConfigRegistered(identifier))
            throw new NullPointerException(String.format("No InventoryFile found with the id '%s'.", identifier));

        return this.files.get(identifier);
    }

    public void reloadAll() {

        for(InventoryConfig file : this.files.values()) {

            try {

                file.loadConfig();

            } catch (IOException e) {

                e.printStackTrace();
            }
        }
    }
}
