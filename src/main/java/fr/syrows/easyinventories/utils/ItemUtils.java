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
