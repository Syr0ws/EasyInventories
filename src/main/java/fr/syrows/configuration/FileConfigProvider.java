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

package fr.syrows.configuration;

public interface FileConfigProvider<T extends FileConfiguration> {

    /**
     * Register a configuration using an access key which will be used to
     * access it later.
     *
     * @param key the key to access the configuration.
     * @param config the configuration to register.
     */
    void registerConfig(ConfigKey key, T config);

    /**
     * Unregister a configuration using its access key. If the configuration
     * doesn't exists, no action will be performed.
     *
     * @param key the key to access the configuration.
     */
    void unregisterConfig(ConfigKey key);

    /**
     * Check if a configuration is registered by using its access key.
     * @param key the key to access the configuration.
     *
     * @return true if the configuration pointed by the key is registered
     *         or else false.
     */
    boolean isRegistered(ConfigKey key);

    /**
     * Get a configuration using its access key.
     *
     * @param key the key to access the configuration.
     *
     * @return a T object is the configuration is registered or else null.
     */
    T getConfig(ConfigKey key);
}
