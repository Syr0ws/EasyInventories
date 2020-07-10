package fr.syrows.easyinventories.utils;

import fr.syrows.easyinventories.tools.StringParser;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.stream.Collectors;

public class ItemUtils {

    public static ItemStack parse(ItemStack stack, StringParser parser) {

        if(!stack.hasItemMeta()) return stack;

        ItemMeta meta = stack.getItemMeta();

        if(meta.hasDisplayName()) {

            String displayName = parser.apply(meta.getDisplayName());
            meta.setDisplayName(displayName);
        }

        if(meta.hasLore()) {

            List<String> lore = meta.getLore().stream().map(parser::apply).collect(Collectors.toList());
            meta.setLore(lore);
        }
        stack.setItemMeta(meta);

        return stack;
    }
}
