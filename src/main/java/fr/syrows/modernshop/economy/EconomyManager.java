package fr.syrows.modernshop.economy;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;

import java.util.UUID;

public class EconomyManager {

    private Economy economy;

    public boolean setupEconomy() {

        if(this.economy != null) return true;

        Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("Vault");

        if(plugin == null) return false;

        RegisteredServiceProvider<Economy> rsp = Bukkit.getServer().getServicesManager().getRegistration(Economy.class);

        if(rsp == null) return false;

        this.economy = rsp.getProvider();

        return true;
    }

    private OfflinePlayer getOfflinePlayer(UUID uuid) {

        OfflinePlayer player = Bukkit.getOfflinePlayer(uuid);

        if(player == null)
            throw new NullPointerException(String.format("No OfflinePlayer found with the uuid '%s'.", uuid.toString()));

        return player;
    }

    public EconomyResponse addMoney(UUID uuid, double amount) {

        if(amount < 0)
            throw new IllegalArgumentException("Money must be positive");

        OfflinePlayer player = this.getOfflinePlayer(uuid);

        return this.economy.depositPlayer(player, amount);
    }

    public EconomyResponse removeMoney(UUID uuid, double amount) {

        if(amount < 0)
            throw new IllegalArgumentException("Money must be positive");

        OfflinePlayer player = this.getOfflinePlayer(uuid);

        return this.economy.withdrawPlayer(player, amount);
    }

    public boolean hasMoney(UUID uuid, double amount) {

        OfflinePlayer player = this.getOfflinePlayer(uuid);

        return this.economy.getBalance(player) >= amount;
    }

    public double getMoney(UUID uuid) {

        OfflinePlayer player = this.getOfflinePlayer(uuid);

        return this.economy.getBalance(player);
    }
}
