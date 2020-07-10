package fr.syrows.modernshop.teleportations;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Teleportation {

    private Player player;
    private Location location;

    private String message;
    private long time;

    public Teleportation(Player player, Location location, String message, long time) {
        this.player = player;
        this.location = location;
        this.message = message;
        this.time = time;
    }

    public Player getPlayer() {
        return this.player;
    }

    public Location getLocation() {
        return this.location;
    }

    public String getMessage() {
        return this.message;
    }

    public long getTime() {
        return this.time;
    }
}
