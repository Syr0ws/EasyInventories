package fr.syrows.easyinventories.configs.sections;

import fr.syrows.easyinventories.configs.tools.InventoryItem;
import fr.syrows.easyinventories.contents.items.PageItem;
import fr.syrows.easyinventories.tools.pagination.PaginationSettings;
import fr.syrows.easyinventories.utils.Utils;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

public class PaginationSection {

    private ConfigurationSection section;

    public PaginationSection(ConfigurationSection section) {
        this.section = section;
    }

    public InventoryItem getPageItem(PageItem.PageType type, boolean replacement) {

        ConfigurationSection section = this.section.getConfigurationSection(type == PageItem.PageType.NEXT ? "next-page" : "previous-page");

        String path = type == PageItem.PageType.NEXT ? (replacement ? "no-next" : "next") : (replacement ? ("no-previous") : "previous");

        if(!section.contains(path))
            throw new NullPointerException(String.format("No item found at '%s.%s'.", section.getCurrentPath(), path));

        ItemStack stack = section.getItemStack(path);
        int slot = section.getInt("slot");

        return new InventoryItem(stack, slot);
    }

    public PaginationSettings getSettings() {

        ConfigurationSection settings = this.section.getConfigurationSection("settings");

        int beginRow = settings.getInt("begin.row"), beginColumn = settings.getInt("begin.column");
        int endRow = settings.getInt("end.row"), endColumn = settings.getInt("end.column");

        int[] blacklisted;

        if(settings.contains("blacklist")) blacklisted = Utils.getIntArray(settings.getIntegerList("blacklist"));
        else blacklisted = new int[0];

        return new PaginationSettings.Builder().values(beginRow, beginColumn, endRow, endColumn).blacklist(blacklisted).getSettings();
    }

    public ConfigurationSection getConfigurationSection() {
        return this.section;
    }
}
