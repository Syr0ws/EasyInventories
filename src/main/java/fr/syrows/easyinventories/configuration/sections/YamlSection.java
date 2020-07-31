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

    ItemStack getItemStack(String path);

    ItemSection getItemSection(String path);

    PaginationSection getPaginationSection(String path);

    @Override
    YamlSection getParent();

    @Override
    YamlSection getSection(String path);
}
