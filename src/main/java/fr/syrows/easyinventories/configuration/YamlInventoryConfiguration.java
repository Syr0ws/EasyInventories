/*
 * Copyright 2020 Syr0ws
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package fr.syrows.easyinventories.configuration;

import fr.syrows.configuration.FileConfiguration;
import fr.syrows.easyinventories.configuration.sections.YamlMemorySection;
import org.bukkit.configuration.InvalidConfigurationException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

public class YamlInventoryConfiguration extends YamlMemorySection implements FileConfiguration {

    private org.bukkit.configuration.file.FileConfiguration config;
    private ConfigDataContainer container;

    private YamlInventoryConfiguration(ConfigDataContainer container) {
        this.container = container;
    }

    @Override
    public void load() {

        if(this.container.hasPath() && this.container.hasResourceData()) {

            this.saveDefaultConfig();
            this.loadFromFile();

        } else if(this.container.hasPath()) {

            this.loadFromFile();

        } else if(this.container.hasResourceData()) {

            this.loadFromResource();

        } else throw new NullPointerException("Cannot load config.");
    }

    @Override
    public void save() {

        if(!this.container.hasPath())
            throw new NullPointerException("No path specified.");

        try { this.config.save(this.container.getPath().toFile());
        } catch (IOException e) { e.printStackTrace(); }
    }

    @Override
    public void addDefaultValue(String path, Object object) {
        this.config.addDefault(path, object);
    }

    @Override
    public void addDefaultValues(Map<String, Object> values) {
        this.config.addDefaults(values);
    }

    private void saveDefaultConfig() {

        if(!this.container.hasResourceData())
            throw new NullPointerException("No default config.");

        if(!this.container.hasPath())
            throw new NullPointerException("No path specified.");

        Path path = this.container.getPath();

        if(Files.exists(path)) return;

        try { this.writeToFile();
        } catch (IOException e) { e.printStackTrace(); }
    }

    private void loadFromResource() {

        if(!this.container.hasResourceData())
            throw new NullPointerException("No default config.");

        this.config = new org.bukkit.configuration.file.YamlConfiguration();
        super.section = this.config.getRoot(); // Initializing root configuration.

        try { this.config.loadFromString(this.container.getResourceData());
        } catch (InvalidConfigurationException e) { e.printStackTrace(); }
    }

    private void loadFromFile() {

        if(!this.container.hasPath())
            throw new NullPointerException("No path specified.");

        Path path = this.container.getPath();

        this.config = org.bukkit.configuration.file.YamlConfiguration.loadConfiguration(path.toFile());
        super.section = this.config.getRoot(); // Initializing root configuration.
    }

    private void writeToFile() throws IOException {

        Path path = this.container.getPath();
        String data = this.container.getResourceData();

        if(path.getParent() != null) Files.createDirectories(path.getParent());

        Files.createFile(path);
        Files.write(path, data.getBytes());
    }

    public static YamlInventoryConfiguration newConfiguration(InputStream stream) {

        YamlInventoryConfiguration configuration = null;

        try {

            String data = YamlInventoryConfiguration.getData(stream);

            ConfigDataContainer container = new ConfigDataContainer(data);

            configuration = new YamlInventoryConfiguration(container);
            configuration.loadFromResource();

        } catch (IOException e) {

            e.printStackTrace();
        }
        return configuration;
    }

    public static YamlInventoryConfiguration newConfiguration(Path path) {

        ConfigDataContainer container = new ConfigDataContainer(path);

        YamlInventoryConfiguration configuration = new YamlInventoryConfiguration(container);
        configuration.loadFromFile();

        return configuration;
    }

    public static YamlInventoryConfiguration newConfiguration(InputStream stream, Path path) {

        YamlInventoryConfiguration configuration = null;

        try {

            String data = YamlInventoryConfiguration.getData(stream);

            ConfigDataContainer container = new ConfigDataContainer(data, path);

            configuration = new YamlInventoryConfiguration(container);
            configuration.saveDefaultConfig();
            configuration.loadFromFile();

        } catch (IOException e) {

            e.printStackTrace();
        }
        return configuration;
    }

    private static String getData(InputStream stream) throws IOException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

        StringBuilder sb = new StringBuilder();

        String l;
        while((l = reader.readLine()) != null) sb.append(l).append("\n");

        return sb.toString();
    }
}
