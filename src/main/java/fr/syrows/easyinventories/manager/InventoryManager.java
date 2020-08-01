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

package fr.syrows.easyinventories.manager;

import fr.syrows.easyinventories.inventories.SimpleInventory;
import fr.syrows.easyinventories.tools.CloseReason;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.stream.Collectors;

public interface InventoryManager {

    /**
     * Open an inventory to a player.
     * If the specified player has an opened inventory, it will be closed
     * before opening the new one.
     *
     * @param player the player to whom open the inventory.
     * @param inventory the inventory to open.
     *
     * @throws NullPointerException if the Bukkit inventory stored in the inventory is null.
     */

    void openInventory(Player player, SimpleInventory inventory);

    /**
     * Close the opened inventory of a player by specifying a reason.
     * To prevent graphic bugs, the Player#updateInventory() method is
     * used after the closure of the inventory.
     *
     * @param player the player to whom close his opened inventory.
     * @param reason the reason of the closure.
     */
    void closeInventory(Player player, CloseReason reason);

    /**
     * Allow to check if a player has an opened inventory.
     *
     * @param player the player to check.
     *
     * @return true if the specified player has an opened inventory or else false.
     */
    boolean hasOpenedInventory(Player player);

    /**
     * Returns the opened inventory of a player.
     *
     * @param player the player to whom get the opened inventory.
     *
     * @return an object of type SimpleInventory if a player has an opened inventory or else null.
     */
    SimpleInventory getOpenedInventory(Player player);

    /**
     * Get all the opened inventories.
     *
     * @return a List of SimpleInventory objects.
     */
    List<SimpleInventory> getInventories();

    /**
     * Get all the players that have an opened inventory.
     *
     * @return a List of Player objects.
     */
    List<Player> getViewers();

    /**
     * Get all the players that have an opened inventory with the id specified.
     *
     * @param id the id of the inventory.
     *
     * @return a List of Player objects.
     */
    default List<Player> getViewers(String id) {
        return this.getViewers().stream()
                .filter(viewer -> this.getOpenedInventory(viewer).getId().equals(id))
                .collect(Collectors.toList());
    }

    /**
     * Get all the players whom have the specified inventory opened.
     *
     * @param inventory an instance of the inventory to get.
     *
     * @return a List of Player objects.
     */
    default List<Player> getViewers(SimpleInventory inventory) {
        return this.getViewers().stream()
                .filter(viewer -> this.getOpenedInventory(viewer).equals(inventory))
                .collect(Collectors.toList());
    }

    /**
     * Get all the players whom have an opened inventory whose class corresponds
     * to he one specified.
     *
     * @param clazz the class of the inventory to get.
     *
     * @return a List of Player objects.
     */
    default List<Player> getViewers(Class<? extends SimpleInventory> clazz) {
        return this.getViewers().stream()
                .filter(viewer -> this.getOpenedInventory(viewer).getClass().equals(clazz))
                .collect(Collectors.toList());
    }

    /**
     * Get all the inventories for a given type.
     *
     * @param clazz the class of the inventory to get.
     * @param <T> the type of the inventory to get.
     *
     * @return a List of objects of type T.
     */
    default <T extends SimpleInventory> List<T> getInventories(Class<T> clazz) {
        return this.getInventories().stream()
                .filter(inventory -> inventory.getClass().equals(clazz))
                .map(clazz::cast)
                .collect(Collectors.toList());
    }
}
