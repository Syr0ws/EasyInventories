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

import fr.syrows.easyinventories.contents.containers.InventorySort;
import fr.syrows.easyinventories.creators.InventoryCreator;
import fr.syrows.easyinventories.inventories.SimpleInventory;
import fr.syrows.easyinventories.tools.CloseReason;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.stream.Collectors;

public interface InventoryManager {

    void openInventory(Player player, SimpleInventory inventory);

    void closeInventory(Player player, CloseReason reason);

    boolean hasOpenedInventory(Player player);

    InventoryCreator findCreator(InventorySort sort);

    SimpleInventory getOpenedInventory(Player player);

    List<SimpleInventory> getInventories();

    List<Player> getViewers();

    default List<Player> getViewers(String id) {
        return this.getViewers().stream()
                .filter(viewer -> this.getOpenedInventory(viewer).getIdentifier().equals(id))
                .collect(Collectors.toList());
    }

    default List<Player> getViewers(SimpleInventory inventory) {
        return this.getViewers().stream()
                .filter(viewer -> this.getOpenedInventory(viewer).equals(inventory))
                .collect(Collectors.toList());
    }

    default List<Player> getViewers(Class<? extends SimpleInventory> clazz) {
        return this.getViewers().stream()
                .filter(viewer -> this.getOpenedInventory(viewer).getClass().equals(clazz))
                .collect(Collectors.toList());
    }

    default <T extends SimpleInventory> List<T> getInventories(Class<T> clazz) {
        return this.getInventories().stream()
                .filter(inventory -> inventory.getClass().equals(clazz))
                .map(clazz::cast)
                .collect(Collectors.toList());
    }
}
