package de.kontux.icepractice.locations;

import de.kontux.icepractice.configs.files.ArenaConfig;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;

public class SpawnPointRepository {
  Location loadSpawn() {
    Location spawn;
    FileConfiguration config = ArenaConfig.getConfig();
    String worldName = config.getString("spawnpoint.world");
    if (worldName != null) {
      World world = Bukkit.getServer().getWorld(worldName);
      double x = config.getDouble("spawnpoint.x");
      double y = config.getDouble("spawnpoint.y");
      double z = config.getDouble("spawnpoint.z");
      float pitch = (float)config.getDouble("spawnpoint.pitch");
      float yaw = (float)config.getDouble("spawnpoint.yaw");
      spawn = new Location(world, x, y, z, yaw, pitch);
    } else {
      spawn = ((World)Bukkit.getWorlds().get(0)).getSpawnLocation();
      Bukkit.broadcastMessage(ChatColor.RED + "The spawnpoint of IcePractice has not been set yet. Set it using /iprac setspawn");
    } 
    return spawn;
  }
  
  void setSpawn(Location location) {
    FileConfiguration config = ArenaConfig.getConfig();
    config.set("spawnpoint.world", location.getWorld().getName());
    config.set("spawnpoint.x", Double.valueOf(location.getX()));
    config.set("spawnpoint.y", Double.valueOf(location.getY()));
    config.set("spawnpoint.z", Double.valueOf(location.getZ()));
    config.set("spawnpoint.pitch", Float.valueOf(location.getPitch()));
    config.set("spawnpoint.yaw", Float.valueOf(location.getYaw()));
    ArenaConfig.save();
  }
}
