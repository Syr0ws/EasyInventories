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

import org.bukkit.inventory.ItemStack;

public interface ItemSection extends YamlSection {

    /**
     * Get the requested ItemStack using default path. The default path ends with '.item'.
     *
     * @return the requested ItemStack.
     * @throws NullPointerException if the default path was found.
     */
    ItemStack getDefaultItem();

    /**
     * Get the requested slot using default path. The default path ends with '.slot'.
     *
     * @return the requested slot.
     * @throws NullPointerException if the default path was found.
     */
    int getDefaultSlot();

    /**
     * Get the requested slots using default path. The default path ends with '.slots'.
     *
     * @return the requested slots.
     * @throws NullPointerException if the default path was found.
     */
    int[] getDefaultSlots();

    /**
     * Get the requested slot by path.
     *
     * @param path the path of the slot to get.
     * @return the requested slot.
     * @throws NullPointerException if the path was found.
     */
    int getSlot(String path);

    /**
     * Get the requested array of slots by path.
     * @param path the path of the array of slots to get.
     * @return the requested array of slots.
     * @throws NullPointerException if the path was found.
     */
    int[] getSlots(String path);
}
