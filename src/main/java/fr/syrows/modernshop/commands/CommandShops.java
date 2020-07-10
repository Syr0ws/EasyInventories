package fr.syrows.modernshop.commands;

import fr.syrows.modernshop.ShopPlugin;
import fr.syrows.modernshop.categories.ShopCategoryManager;
import fr.syrows.modernshop.components.EasyTextComponent;
import fr.syrows.modernshop.inventories.provider.InventoryProvider;
import fr.syrows.modernshop.shops.ShopManager;
import fr.syrows.modernshop.utils.parsers.MessageParser;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class CommandShops implements CommandExecutor {

    private ShopPlugin plugin;

    public CommandShops(ShopPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        FileConfiguration config = this.plugin.getConfig();
        ConfigurationSection section = config.getConfigurationSection("shops-command");

        if(!(sender instanceof Player)) {
            sender.sendMessage(MessageParser.parseColors(section.getString("not-a-player")));
            return true;
        }

        Player player = (Player) sender;

        if(!player.hasPermission("modernshop.command.shops")) {
            player.sendMessage(MessageParser.parseColors(section.getString("no-permission")));
            return true;
        }

        if(args.length != 0) {
            player.spigot().sendMessage(this.getUsage(section));
            return true;
        }
        player.sendMessage(MessageParser.parseColors(section.getString("message")));

        ShopCategoryManager categoryManager = this.plugin.getShopCategoryManager();
        ShopManager shopManager = this.plugin.getShopManager();

        InventoryProvider provider = this.plugin.getInventoryProvider();
        provider.getShopViewer(categoryManager, shopManager).openInventory(player);

        return true;
    }

    private TextComponent getUsage(ConfigurationSection section) {

        ConfigurationSection usage = section.getConfigurationSection("usage");

        EasyTextComponent component = new EasyTextComponent().getFromYaml(usage);
        component.runCommand("shops");

        return component.getComponent();
    }
}
