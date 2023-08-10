package de.kontux.icepractice.arenas;

import de.kontux.icepractice.IcePracticePlugin;
import de.kontux.icepractice.configs.files.ArenaConfig;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public class SumoEventArena extends Arena {
  SumoEventArena(String name) {
    super(name);
    this.section = ArenaConfig.getConfig().isConfigurationSection("SumoEventArenas." + name) ? ArenaConfig.getConfig().getConfigurationSection("SumoEventArenas." + name) : ArenaConfig.getConfig().createSection("SumoEventArenas." + name);
  }
  
  public boolean loadFromConfig() {
    String worldName = this.section.getString("pos1.World");
    if (worldName == null) {
      IcePracticePlugin.getInstance().getLogger().warning("The world of the sumo event arena " + this.name + " does not exist. This arena will not be used.");
      return false;
    } 
    World world = Bukkit.getServer().getWorld(worldName);
    double pos1X = this.section.getDouble("pos1.Coordinates.X");
    double pos1Y = this.section.getDouble("pos1.Coordinates.Y");
    double pos1Z = this.section.getDouble("pos1.Coordinates.Z");
    float pos1Pitch = (float)this.section.getDouble("pos1.Coordinates.Pitch");
    float pos1Yaw = (float)this.section.getDouble("pos1.Coordinates.Yaw");
    this.pos1 = new Location(world, pos1X, pos1Y, pos1Z, pos1Yaw, pos1Pitch);
    double pos2X = this.section.getDouble("pos2.Coordinates.X");
    double pos2Y = this.section.getDouble("pos2.Coordinates.Y");
    double pos2Z = this.section.getDouble("pos2.Coordinates.Z");
    float pos2Pitch = (float)this.section.getDouble("pos2.Pitch");
    float pos2Yaw = (float)this.section.getDouble("pos2.Yaw");
    this.pos2 = new Location(world, pos2X, pos2Y, pos2Z, pos2Yaw, pos2Pitch);
    double centerX = this.section.getDouble("spawn.Coordinates.X");
    double centerY = this.section.getDouble("spawn.Coordinates.Y");
    double centerZ = this.section.getDouble("spawn.Coordinates.Z");
    float centerPitch = (float)this.section.getDouble("spawn.Coordinates.Pitch");
    float centerYaw = (float)this.section.getDouble("spawn.Coordinates.Yaw");
    this.center = new Location(world, centerX, centerY, centerZ, centerYaw, centerPitch);
    this.sumo = true;
    return true;
  }
  
  public void saveToConfig() {
    this.section.set("pos1.World", this.pos1.getWorld().getName());
    this.section.set("pos1.Coordinates.X", Double.valueOf(this.pos1.getX()));
    this.section.set("pos1.Coordinates.Y", Double.valueOf(this.pos1.getY()));
    this.section.set("pos1.Coordinates.Z", Double.valueOf(this.pos1.getZ()));
    this.section.set("pos1.Coordinates.Pitch", Float.valueOf(this.pos1.getPitch()));
    this.section.set("pos1.Coordinates.Yaw", Float.valueOf(this.pos1.getYaw()));
    this.section.set("pos2.Coordinates.X", Double.valueOf(this.pos2.getX()));
    this.section.set("pos2.Coordinates.Y", Double.valueOf(this.pos2.getY()));
    this.section.set("pos2.Coordinates.Z", Double.valueOf(this.pos2.getZ()));
    this.section.set("pos2.Coordinates.Pitch", Float.valueOf(this.pos2.getPitch()));
    this.section.set("pos2.Coordinates.Yaw", Float.valueOf(this.pos2.getYaw()));
    this.section.set("spawn.Coordinates.X", Double.valueOf(this.center.getX()));
    this.section.set("spawn.Coordinates.Y", Double.valueOf(this.center.getY()));
    this.section.set("spawn.Coordinates.Z", Double.valueOf(this.center.getZ()));
    this.section.set("spawn.Coordinates.Pitch", Float.valueOf(this.center.getPitch()));
    this.section.set("spawn.Coordinates.Yaw", Float.valueOf(this.center.getYaw()));
    this.section.set("sumo", Boolean.valueOf(true));
    ArenaConfig.save();
  }
}
