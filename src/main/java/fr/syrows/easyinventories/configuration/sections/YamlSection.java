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

package fr.syrows.easyinventories.configuration.sections;

import fr.syrows.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

public interface YamlSection extends ConfigurationSection {

    /**
     * Get the requested ItemStack by path.
     *
     * @param path the path of the itemStack to get.
     * @return the ItemStack requested.
     * @throws NullPointerException if the path was found.
     */
    ItemStack getItemStack(String path);

    /**
     * Get the requested ItemSection by path.
     *
     * @param path the path of the ItemSection to get.
     * @return the ItemSection requested.
     * @throws NullPointerException if the path was found.
     */
    ItemSection getItemSection(String path);

    /**
     * Get the requested PaginationSection by path.
     *
     * @param path the path of the PaginationSection to get.
     * @return the PaginationSection requested.
     * @throws NullPointerException if the path was found.
     */
    PaginationSection getPaginationSection(String path);

    /**
     * Get the parent of this section as YamlSection.
     *
     * @return the parent of the section if it has one or else null.
     */
    @Override
    YamlSection getParent();

    /**
     * Get the requested YamlSection by path.
     *
     * @param path the path of the YamlSection to get.
     * @return the requested YamlSection.
     *
     * @throws NullPointerException if the path was found.
     */
    @Override
    YamlSection getSection(String path);
}
