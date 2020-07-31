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

import java.util.List;

public interface ConfigurationSection {

    String getString(String path);

    byte getByte(String path);

    short getShort(String path);

    int getInt(String path);

    long getLong(String path);

    float getFloat(String path);

    double getDouble(String path);

    boolean getBoolean(String path);

    List<String> getStringList(String path);

    List<Integer> getIntegerList(String path);

    List<Long> getLongList(String path);

    List<Double> getDoubleList(String path);

    List<Float> getListFloat(String path);

    Object get(String path);

    String getCurrentPath();

    boolean isSet(String path);

    ConfigurationSection getSection(String path);

    ConfigurationSection getParent();
}
