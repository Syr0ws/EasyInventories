package fr.syrows.modernshop.shops;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public class ShopLocation {

    private String world;
    private int x, y, z;

    public ShopLocation(Location location) {

        this.world = location.getWorld().getName();

        this.x = location.getBlockX();
        this.y = location.getBlockY();
        this.z = location.getBlockZ();
    }

    public String getWorldName() {
        return this.world;
    }

    public World getWorld() {
        return Bukkit.getWorld(this.world);
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getZ() {
        return this.z;
    }

    public Location getLocation() {
        return new Location(this.getWorld(), this.x, this.y, this.z);
    }

    @Override
    public String toString() {
        return "ShopLocation{" +
                "world='" + world + '\'' +
                ", x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }
}
