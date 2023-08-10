package de.kontux.icepractice.arenas;

import de.kontux.icepractice.api.arena.IcePracticeArena;
import de.kontux.icepractice.configs.files.ArenaConfig;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public class Arena implements IcePracticeArena {
  protected final String name;
  
  protected Location pos1;
  
  protected Location pos2;
  
  protected Location center;
  
  protected boolean sumo;
  
  protected boolean spleef;
  
  protected boolean hcf;
  
  protected boolean build;
  
  protected boolean inUse;
  
  protected ConfigurationSection section;
  
  Arena(String name) {
    this.name = name;
    this.section = ArenaConfig.getConfig().isConfigurationSection("Arenas." + name) ? ArenaConfig.getConfig().getConfigurationSection("Arenas." + name) : ArenaConfig.getConfig().createSection("Arenas." + name);
  }
  
  public void create(Player player) {
    this.section.set("name", this.name);
    this.pos1 = player.getLocation();
    this.pos2 = player.getLocation();
    this.center = player.getLocation();
    this.sumo = false;
    this.spleef = false;
    this.hcf = false;
    ArenaConfig.save();
  }
  
  public boolean loadFromConfig() {
    String worldName = this.section.getString("pos1.World");
    if (worldName == null)
      return false; 
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
    float pos2Pitch = (float)this.section.getDouble("pos2.Coordinates.Pitch");
    float pos2Yaw = (float)this.section.getDouble("pos2.Coordinates.Yaw");
    this.pos2 = new Location(world, pos2X, pos2Y, pos2Z, pos2Yaw, pos2Pitch);
    double centerX = this.section.getDouble("center.Coordinates.X");
    double centerY = this.section.getDouble("center.Coordinates.Y");
    double centerZ = this.section.getDouble("center.Coordinates.Z");
    float centerPitch = (float)this.section.getDouble("center.Coordinates.Pitch");
    float centerYaw = (float)this.section.getDouble("center.Coordinates.Yaw");
    this.center = new Location(world, centerX, centerY, centerZ, centerYaw, centerPitch);
    this.sumo = this.section.getBoolean("sumo");
    this.hcf = this.section.getBoolean("hcf");
    this.spleef = this.section.getBoolean("spleef");
    this.build = (!this.section.isBoolean("build") || this.section.getBoolean("build"));
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
    this.section.set("center.Coordinates.X", Double.valueOf(this.center.getX()));
    this.section.set("center.Coordinates.Y", Double.valueOf(this.center.getY()));
    this.section.set("center.Coordinates.Z", Double.valueOf(this.center.getZ()));
    this.section.set("center.Coordinates.Pitch", Float.valueOf(this.center.getPitch()));
    this.section.set("center.Coordinates.Yaw", Float.valueOf(this.center.getYaw()));
    this.section.set("sumo", Boolean.valueOf(this.sumo));
    this.section.set("hcf", Boolean.valueOf(this.hcf));
    this.section.set("spleef", Boolean.valueOf(this.spleef));
    this.section.set("build", Boolean.valueOf(this.build));
    ArenaConfig.save();
  }
  
  public void delete() {
    ArenaConfig.getConfig().set(this.section.getCurrentPath(), null);
    ArenaConfig.save();
  }
  
  public String getName() {
    return this.name;
  }
  
  public Location getPos1() {
    return this.pos1;
  }
  
  public void setPos1(Location pos1) {
    this.pos1 = pos1;
    saveToConfig();
  }
  
  public Location getPos2() {
    return this.pos2;
  }
  
  public void setPos2(Location pos2) {
    this.pos2 = pos2;
    saveToConfig();
  }
  
  public Location getCenter() {
    return this.center;
  }
  
  public void setCenter(Location center) {
    this.center = center;
    saveToConfig();
  }
  
  public boolean isSumo() {
    return this.sumo;
  }
  
  public void setSumo(boolean sumo) {
    this.sumo = sumo;
    saveToConfig();
  }
  
  public boolean isSpleef() {
    return this.spleef;
  }
  
  public void setSpleef(boolean spleef) {
    this.spleef = spleef;
    saveToConfig();
  }
  
  public boolean isHcf() {
    return this.hcf;
  }
  
  public void setHcf(boolean hcf) {
    this.hcf = hcf;
    saveToConfig();
  }
  
  public boolean isBuild() {
    return this.build;
  }
  
  public void setBuild(boolean build) {
    this.build = build;
    saveToConfig();
  }
  
  public boolean isInUse() {
    return this.inUse;
  }
  
  public void setInUse(boolean b) {
    this.inUse = b;
  }
}
