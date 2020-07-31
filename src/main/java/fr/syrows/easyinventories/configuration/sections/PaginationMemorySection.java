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
import fr.syrows.easyinventories.contents.pagination.PageItem;
import fr.syrows.easyinventories.contents.pagination.PaginationSettings;
import fr.syrows.easyinventories.inventories.impl.PageableInventory;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class PaginationMemorySection extends YamlMemorySection implements PaginationSection {

    public PaginationMemorySection(ConfigurationSection section) {
        super(section);
    }

    @Override
    public PageItem getPageItem(PageableInventory<?> inventory, PageItem.PageType type, boolean replacement) {

        ItemSection section = super.getItemSection(type == PageItem.PageType.NEXT ? "next-page" : "previous-page");

        String path = type == PageItem.PageType.NEXT ? (replacement ? "no-next" : "next") : (replacement ? ("no-previous") : "previous");

        ItemStack stack = section.getItemStack(path);
        int slot = section.getDefaultSlot();

        return new PageItem(inventory, type, stack, slot);
    }

    @Override
    public PaginationSettings getSettings() {

        YamlSection settings = super.getSection("settings");

        int beginRow = settings.getInt("begin.row"), beginColumn = settings.getInt("begin.column");
        int endRow = settings.getInt("end.row"), endColumn = settings.getInt("end.column");

        int[] blacklisted;

        if(settings.isSet("blacklist")) {

            List<Integer> integers = settings.getIntegerList("blacklist");

            blacklisted = ListConverter.getIntArray(integers);

        } else blacklisted = new int[0];

        return new PaginationSettings.Builder().values(beginRow, beginColumn, endRow, endColumn).blacklist(blacklisted).getSettings();
    }
}
