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

import fr.syrows.easyinventories.configuration.util.ListConverter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class ItemMemorySection extends YamlMemorySection implements ItemSection {

    public ItemMemorySection(ConfigurationSection section) {
        super(section);
    }

    @Override
    public ItemStack getDefaultItem() {

        if(!super.section.contains("item"))
            throw new NullPointerException(String.format("Path '%s.item' not found.", super.section.getCurrentPath()));

        return this.getItemStack("item");
    }

    @Override
    public int getSlot(String path) {
        return super.getInt(path);
    }

    @Override
    public int getDefaultSlot() {

        super.validatePath("slot");

        return this.getSlot("slot");
    }

    @Override
    public int[] getSlots(String path) {

        List<Integer> list = super.getIntegerList(path);

        return ListConverter.getIntArray(list);
    }

    @Override
    public int[] getDefaultSlots() {

        int[] slots = new int[1];

        if(super.section.contains("slot")) slots[0] = this.getSlot("slot");
        else if(super.section.contains("slots")) slots = this.getSlots("slots");
        else throw new NullPointerException(String.format("No slot found at '%s'.", super.section.getCurrentPath()));

        return slots;
    }
}
