package fr.syrows.easyinventories.configs.sections.impl;

import fr.syrows.easyinventories.configs.sections.IConfigurationSection;
import fr.syrows.easyinventories.configs.sections.IMemorySection;
import fr.syrows.easyinventories.contents.items.PageItem;
import fr.syrows.easyinventories.tools.pagination.PaginationSettings;
import org.bukkit.configuration.ConfigurationSection;

public class PaginationSection extends IMemorySection {

    public PaginationSection(ConfigurationSection section) {
        super(section);
    }

    public ItemSection getPageItem(PageItem.PageType type, boolean replacement) {

        ConfigurationSection section = super.section.getConfigurationSection(type == PageItem.PageType.NEXT ? "next-page" : "previous-page");

        String path = type == PageItem.PageType.NEXT ? (replacement ? "no-next" : "next") : (replacement ? ("no-previous") : "previous");

        if(!section.contains(path))
            throw new NullPointerException(String.format("No item found at '%s.%s'.", section.getCurrentPath(), path));

        return new ItemSection(section.getConfigurationSection(path));
    }

    public PaginationSettings getSettings() {

        IConfigurationSection settings = super.getConfigurationSection("settings");

        int beginRow = settings.getInt("begin.row"), beginColumn = settings.getInt("begin.column");
        int endRow = settings.getInt("end.row"), endColumn = settings.getInt("end.column");

        int[] blacklisted;

        if(settings.contains("blacklist")) blacklisted = super.getIntArray("blacklist");
        else blacklisted = new int[0];

        return new PaginationSettings.Builder().values(beginRow, beginColumn, endRow, endColumn).blacklist(blacklisted).getSettings();
    }
}
