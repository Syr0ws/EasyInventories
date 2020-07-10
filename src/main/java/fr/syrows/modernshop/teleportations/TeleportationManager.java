package fr.syrows.modernshop.teleportations;

import fr.syrows.modernshop.ShopPlugin;
import fr.syrows.modernshop.utils.parsers.MessageParser;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;

public class TeleportationManager {

    private ShopPlugin plugin;
    private TeleportationTask task;
    private ArrayList<Teleportation> teleportations;

    public TeleportationManager(ShopPlugin plugin) {
        this.plugin = plugin;
        this.teleportations = new ArrayList<>();
    }

    public void init() {

        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new TeleportationListeners(), this.plugin);

        this.task = new TeleportationTask();
        this.task.start();
    }

    public void end() {
        this.task.stop();
    }

    public void teleport(Player player, Location location, String message) {

        FileConfiguration config = this.plugin.getConfig();
        ConfigurationSection section = config.getConfigurationSection("teleportation");

        int cooldown = section.getInt("cooldown");

        if(cooldown != 0 && !player.hasPermission("modernshop.teleportation.cooldown.bypass")) {

            long time = System.currentTimeMillis() + (cooldown * 1000);

            Teleportation teleportation = new Teleportation(player, location, message, time);

            this.teleportations.add(teleportation);

            String cooldownMessage = section.getString("cooldown-message").replace("%time%", Integer.toString(cooldown));

            player.sendMessage(MessageParser.parseColors(cooldownMessage));

        } else {

            player.teleport(location);
            player.sendMessage(MessageParser.parseColors(message));
        }
    }

    public void confirm(Teleportation teleportation) {

        this.teleportations.remove(teleportation);

        Player player = teleportation.getPlayer();

        player.teleport(teleportation.getLocation());
        player.sendMessage(MessageParser.parseColors(teleportation.getMessage()));
    }

    public void cancel(Teleportation teleportation) {

        this.teleportations.remove(teleportation);

        FileConfiguration config = this.plugin.getConfig();
        Player player = teleportation.getPlayer();

        player.sendMessage(MessageParser.parseColors(config.getString("teleportation.cancel-message")));
    }

    public Teleportation getTeleportation(Player player) {
        return this.teleportations.stream().filter(tp -> tp.getPlayer().equals(player)).findFirst().orElse(null);
    }

    public boolean hasWaitingTeleportation(Player player) {
        return this.teleportations.stream().anyMatch(tp -> tp.getPlayer().equals(player));
    }

    private class TeleportationTask implements Runnable {

        private BukkitTask task;

        @Override
        public void run() {

            ArrayList<Teleportation> teleportations = new ArrayList<>(TeleportationManager.this.teleportations);

            for(Teleportation teleportation : teleportations) {

                long time = System.currentTimeMillis();

                if(time >= teleportation.getTime()) TeleportationManager.this.confirm(teleportation);
            }
        }

        public void start() {

            if(this.isRunning())
                throw new UnsupportedOperationException("Task already running.");

            this.task = Bukkit.getScheduler().runTaskTimer(TeleportationManager.this.plugin, this, 0L, 10L);
        }

        public void stop() {

            if(!this.isRunning()) return;

            this.task.cancel();
            this.task = null;
        }

        public boolean isRunning() {
            return this.task != null && !this.task.isCancelled();
        }
    }

    private class TeleportationListeners implements Listener {

        @EventHandler
        public void onPlayerMove(PlayerMoveEvent event) {

            Player player = event.getPlayer();

            if(!TeleportationManager.this.hasWaitingTeleportation(player)) return;

            Location to = event.getTo(), from = event.getFrom();

            if(to.distanceSquared(from) >= 0.01) this.cancelTeleportation(player);
        }

        @EventHandler
        public void onPlayerQuit(PlayerQuitEvent event) {

            Player player = event.getPlayer();

            if(TeleportationManager.this.hasWaitingTeleportation(player)) this.cancelTeleportation(player);
        }

        private void cancelTeleportation(Player player) {
            Teleportation teleportation = TeleportationManager.this.getTeleportation(player);
            TeleportationManager.this.cancel(teleportation);
        }
    }
}
