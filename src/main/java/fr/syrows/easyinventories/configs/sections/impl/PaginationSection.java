package fr.syrows.easyinventories.configs.sections.impl;

import fr.syrows.easyinventories.configs.sections.IConfigurationSection;
import fr.syrows.easyinventories.configs.sections.IMemorySection;
import fr.syrows.easyinventories.contents.pagination.PageItem;
import fr.syrows.easyinventories.inventories.impl.PageableInventory;
import fr.syrows.easyinventories.contents.pagination.PaginationSettings;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

public class PaginationSection extends IMemorySection {

    public PaginationSection(ConfigurationSection section) {
        super(section);
    }

    public PageItem getPageItem(PageableInventory<?> inventory, PageItem.PageType type, boolean replacement) {

        ItemSection section = super.getItemSection(type == PageItem.PageType.NEXT ? "next-page" : "previous-page");

        String path = type == PageItem.PageType.NEXT ? (replacement ? "no-next" : "next") : (replacement ? ("no-previous") : "previous");

        ItemStack stack = section.getItemStack(path);
        int slot = section.getDefaultSlot();

        return new PageItem(inventory, type, stack, slot);
    }

    public PaginationSettings getSettings() {

        IConfigurationSection settings = super.getConfigurationSection("settings");

        int beginRow = settings.getInt("begin.row"), beginColumn = settings.getInt("begin.column");
        int endRow = settings.getInt("end.row"), endColumn = settings.getInt("end.column");

        int[] blacklisted;

        if(settings.contains("blacklist")) blacklisted = settings.getIntArray("blacklist");
        else blacklisted = new int[0];

        return new PaginationSettings.Builder().values(beginRow, beginColumn, endRow, endColumn).blacklist(blacklisted).getSettings();
    }
}
