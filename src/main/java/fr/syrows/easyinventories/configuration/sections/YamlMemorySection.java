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

import fr.syrows.easyinventories.configuration.YamlInventoryConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.NumberConversions;

import java.util.List;

public class YamlMemorySection implements YamlSection {

    protected org.bukkit.configuration.ConfigurationSection section;

    public YamlMemorySection() {

        if(!(this instanceof YamlInventoryConfiguration))
            throw new UnsupportedOperationException("Not a YAML configuration.");
    }

    public YamlMemorySection(org.bukkit.configuration.ConfigurationSection section) {
        this.section = section;
    }

    @Override
    public String getString(String path) {

        this.validatePath(path);

        return this.section.getString(path);
    }

    @Override
    public byte getByte(String path) {

        Object object = this.get(path);

        this.validateNumber(path, object);

        return NumberConversions.toByte(object);
    }

    @Override
    public short getShort(String path) {

        Object object = this.get(path);

        this.validateNumber(path, object);

        return NumberConversions.toShort(object);
    }

    @Override
    public int getInt(String path) {

        this.validatePath(path);

        return this.section.getInt(path);
    }

    @Override
    public long getLong(String path) {

        this.validatePath(path);

        return this.section.getLong(path);
    }

    @Override
    public float getFloat(String path) {

        Object object = this.get(path);

        this.validateNumber(path, object);

        return NumberConversions.toFloat(object);
    }

    @Override
    public double getDouble(String path) {
        this.validatePath(path);
        return this.section.getDouble(path);
    }

    @Override
    public boolean getBoolean(String path) {

        this.validatePath(path);

        return this.section.getBoolean(path);
    }

    @Override
    public List<String> getStringList(String path) {

        this.validatePath(path);

        return this.section.getStringList(path);
    }

    @Override
    public List<Integer> getIntegerList(String path) {

        this.validatePath(path);

        return this.section.getIntegerList(path);
    }

    @Override
    public List<Long> getLongList(String path) {

        this.validatePath(path);

        return this.section.getLongList(path);
    }

    @Override
    public List<Double> getDoubleList(String path) {

        this.validatePath(path);

        return this.section.getDoubleList(path);
    }

    @Override
    public List<Float> getListFloat(String path) {

        this.validatePath(path);

        return this.section.getFloatList(path);
    }

    @Override
    public Object get(String path) {

        this.validatePath(path);

        return this.section.get(path);
    }

    @Override
    public String getCurrentPath() {
        return this.section.getCurrentPath();
    }

    @Override
    public boolean isSet(String path) {

        this.validatePath(path);

        return this.section.isSet(path);
    }

    @Override
    public YamlSection getSection(String path) {

        this.validatePath(path);

        return new YamlMemorySection(this.section.getConfigurationSection(path));
    }

    @Override
    public YamlSection getParent() {
        return new YamlMemorySection(this.section.getParent());
    }

    @Override
    public ItemStack getItemStack(String path) {

        this.validatePath(path);

        ItemStack stack = this.section.getItemStack(path);

        return stack.clone();
    }

    @Override
    public ItemSection getItemSection(String path) {

        this.validatePath(path);

        return new ItemMemorySection(this.section.getConfigurationSection(path));
    }

    @Override
    public PaginationSection getPaginationSection(String path) {

        this.validatePath(path);

        return new PaginationMemorySection(this.section.getConfigurationSection(path));
    }

    protected void validatePath(String path) {

        if(!this.section.contains(path))
            throw new NullPointerException(String.format("Path '%s.%s' not found.", this.getCurrentPath(), path));
    }

    protected void validateNumber(String path, Object object) {

        if(!(object instanceof Number))
            throw new UnsupportedOperationException(String.format("Object at '%s.%s' is not a number.", this.getCurrentPath(), path));
    }
}
