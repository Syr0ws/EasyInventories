package fr.syrows.modernshop.commands;

import fr.syrows.modernshop.ShopPlugin;
import fr.syrows.modernshop.components.EasyTextComponent;
import fr.syrows.modernshop.utils.parsers.MessageParser;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

public class CommandModernShop implements CommandExecutor {

    private ShopPlugin plugin;

    public CommandModernShop(ShopPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        FileConfiguration config = this.plugin.getConfig();
        ConfigurationSection section = config.getConfigurationSection("modernshop-command");

        if(!(sender.hasPermission("modernshop.command.modernshop"))) {
            sender.sendMessage(MessageParser.parseColors(section.getString("no-permission")));
            return true;
        }

        if(args.length != 1 || !args[0].equalsIgnoreCase("reload")) {
            sender.spigot().sendMessage(this.getUsage(section));
            return true;
        }
        this.plugin.onReload();

        sender.sendMessage(MessageParser.parseColors(section.getString("reloaded")));
        return true;
    }

    private TextComponent getUsage(ConfigurationSection section) {

        ConfigurationSection usage = section.getConfigurationSection("usage");

        EasyTextComponent component = new EasyTextComponent().getFromYaml(usage);
        component.runCommand("modernshop reload");

        return component.getComponent();
    }
}
