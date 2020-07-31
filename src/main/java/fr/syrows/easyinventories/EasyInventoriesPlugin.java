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

package fr.syrows.easyinventories;

import fr.syrows.easyinventories.manager.DefaultInventoryManager;
import fr.syrows.easyinventories.manager.InventoryManager;
import org.bukkit.plugin.java.JavaPlugin;

public class EasyInventoriesPlugin extends JavaPlugin {

    private InventoryManager inventoryManager;

    @Override
    public void onEnable() {
        this.inventoryManager = new DefaultInventoryManager(this);
    }

    public InventoryManager getInventoryManager() {
        return this.inventoryManager;
    }
}
