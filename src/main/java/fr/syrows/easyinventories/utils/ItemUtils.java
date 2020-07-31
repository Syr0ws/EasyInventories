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

package fr.syrows.easyinventories.utils;

import fr.syrows.easyinventories.tools.StringParser;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.stream.Collectors;

public class ItemUtils {

    public static void parseAll(ItemStack stack, StringParser parser) {

        ItemUtils.parseName(stack, parser);
        ItemUtils.parseLore(stack, parser);
    }

    public static void parseName(ItemStack stack, StringParser parser) {

        if(!stack.hasItemMeta()) return;

        ItemMeta meta = stack.getItemMeta();

        if(meta.hasDisplayName()) {

            String displayName = parser.apply(meta.getDisplayName());
            meta.setDisplayName(displayName);

            stack.setItemMeta(meta);
        }
    }

    public static void parseLore(ItemStack stack, StringParser parser) {

        if(!stack.hasItemMeta()) return;

        ItemMeta meta = stack.getItemMeta();

        if(meta.hasLore()) {

            List<String> lore = meta.getLore().stream().map(parser::apply).collect(Collectors.toList());
            meta.setLore(lore);

            stack.setItemMeta(meta);
        }
    }
}
