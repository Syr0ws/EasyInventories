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
import java.util.Set;

public interface ConfigurationSection {

    /**
     * Get the requested String by path.
     *
     * @param path the path of the String to get.
     * @return the requested String.
     *
     * @throws NullPointerException if the path was found.
     */
    String getString(String path);

    /**
     * Get the requested byte by path.
     *
     * @param path the path of the byte to get.
     * @return the requested byte.
     *
     * @throws NullPointerException if the path was found.
     */
    byte getByte(String path);

    /**
     * Get the requested short by path.
     *
     * @param path the path of the short to get.
     * @return the requested short.
     *
     * @throws NullPointerException if the path was found.
     */
    short getShort(String path);

    /**
     * Get the requested int by path.
     *
     * @param path the path of the int to get.
     * @return the requested int.
     *
     * @throws NullPointerException if the path was found.
     */
    int getInt(String path);

    /**
     * Get the requested long by path.
     *
     * @param path the path of the long to get.
     * @return the requested long.
     *
     * @throws NullPointerException if the path was found.
     */
    long getLong(String path);

    /**
     * Get the requested float by path.
     *
     * @param path the path of the float to get.
     * @return the requested float.
     *
     * @throws NullPointerException if the path was found.
     */
    float getFloat(String path);

    /**
     * Get the requested double by path.
     *
     * @param path the path of the double to get.
     * @return the requested double.
     *
     * @throws NullPointerException if the path was found.
     */
    double getDouble(String path);

    /**
     * Get the requested boolean by path.
     *
     * @param path the path of the boolean to get.
     * @return the requested boolean.
     *
     * @throws NullPointerException if the path was found.
     */
    boolean getBoolean(String path);

    /**
     * Get the requested List of String by path.
     *
     * @param path the path of the List of String to get.
     * @return the requested List of String.
     *
     * @throws NullPointerException if the path was found.
     */
    List<String> getStringList(String path);

    /**
     * Get the requested List of Integer by path.
     *
     * @param path the path of the List of Integer to get.
     * @return the requested List of Integer.
     *
     * @throws NullPointerException if the path was found.
     */
    List<Integer> getIntegerList(String path);

    /**
     * Get the requested List of Long by path.
     *
     * @param path the path of the List of Long to get.
     * @return the requested List of Long.
     *
     * @throws NullPointerException if the path was found.
     */
    List<Long> getLongList(String path);

    /**
     * Get the requested List of Double by path.
     *
     * @param path the path of the List of Double to get.
     * @return the requested List of Double.
     *
     * @throws NullPointerException if the path was found.
     */
    List<Double> getDoubleList(String path);

    /**
     * Get the requested List of Float by path.
     *
     * @param path the path of the List of Float to get.
     * @return the requested List of Float.
     *
     * @throws NullPointerException if the path was found.
     */
    List<Float> getFloatList(String path);

    /**
     * Get the requested Object by path.
     *
     * @param path the path of the Object to get.
     * @return the requested Object.
     *
     * @throws NullPointerException if the path was found.
     */
    Object get(String path);

    /**
     * Get the path of the current section.
     *
     * @return a String that is the path of the current section.
     */
    String getCurrentPath();

    /**
     * Check if a path is set.
     *
     * @param path the path to check.
     * @return true if the path is set or else false.
     */
    boolean isSet(String path);

    boolean isString(String path);

    boolean isInt(String path);

    boolean isByte(String path);

    boolean isShort(String path);

    boolean isLong(String path);

    boolean isFloat(String path);

    boolean isDouble(String path);

    boolean isBoolean(String path);

    boolean isList(String path);

    boolean isSection(String path);

    Set<String> getPaths();

    Set<String> getSubPaths();

    /**
     * Get the requested ConfigurationSection by path.
     *
     * @param path the path of the ConfigurationSection to get.
     * @return the requested ConfigurationSection.
     *
     * @throws NullPointerException if the path was found.
     */
    ConfigurationSection getSection(String path);

    ConfigurationSection getRoot();

    /**
     * Get the parent of this section.
     *
     * @return the parent of the section if it has one or else null.
     */
    ConfigurationSection getParent();
}
