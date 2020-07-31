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

import fr.syrows.configuration.ConfigKey;
import fr.syrows.configuration.FileConfigProvider;

import java.util.HashMap;
import java.util.Map;

public class YamlInventoryConfigProvider implements FileConfigProvider<YamlInventoryConfiguration> {

    private Map<ConfigKey, YamlInventoryConfiguration> configs = new HashMap<>();

    @Override
    public void registerConfig(ConfigKey key, YamlInventoryConfiguration config) {

        if(this.isRegistered(key))
            throw new UnsupportedOperationException("Config already registered.");

        this.configs.put(key, config);
    }

    @Override
    public void unregisterConfig(ConfigKey key) {
        this.configs.remove(key);
    }

    @Override
    public boolean isRegistered(ConfigKey key) {
        return this.configs.containsKey(key);
    }

    @Override
    public YamlInventoryConfiguration getConfig(ConfigKey key) {

        if(!this.isRegistered(key))
            throw new NullPointerException("Config not registered.");

        return this.configs.get(key);
    }

    public void reloadAll() {
        this.configs.values().forEach(YamlInventoryConfiguration::load);
    }
}
