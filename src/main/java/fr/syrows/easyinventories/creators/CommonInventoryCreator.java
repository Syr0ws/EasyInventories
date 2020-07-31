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

package fr.syrows.easyinventories.creators;

import fr.syrows.easyinventories.contents.containers.InventorySort;
import fr.syrows.easyinventories.inventories.SimpleInventory;
import fr.syrows.easyinventories.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;

import java.util.Arrays;
import java.util.List;

import static fr.syrows.easyinventories.contents.containers.InventorySortType.*;

public class CommonInventoryCreator implements InventoryCreator {

    private static final List<InventorySort> supported = Arrays.asList(ENDER_CHEST, SHULKER_BOX, DROPPER, DISPENSER, HOPPER);

    @Override
    public Inventory getInventory(SimpleInventory inventory) {

        String title = Utils.parseColors(inventory.getTitle());

        InventorySort sort = inventory.getContainer();

        return Bukkit.createInventory(null, sort.getInventoryType(), title);
    }

    @Override
    public boolean isSupported(InventorySort sort) {
        return CommonInventoryCreator.supported.contains(sort);
    }
}
