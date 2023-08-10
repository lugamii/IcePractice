package de.kontux.icepractice.kiteditor;

import de.kontux.icepractice.configs.Settings;
import de.kontux.icepractice.configs.files.ArenaConfig;
import java.util.HashSet;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class KitEditorRepository {
  private final FileConfiguration config = ArenaConfig.getConfig();
  
  public static boolean isSign(Block sign) {
    FileConfiguration config = ArenaConfig.getConfig();
    String worldName = ArenaConfig.getConfig().getString("editor.sign.world");
    if (worldName != null) {
      World world = Bukkit.getWorld(worldName);
      double x = config.getDouble("editor.sign.x");
      double y = config.getDouble("editor.sign.y");
      double z = config.getDouble("editor.sign.z");
      Location originalLocation = new Location(world, x, y, z);
      return sign.getLocation().equals(originalLocation);
    } 
    return false;
  }
  
  public static boolean isChest(Block chest) {
    FileConfiguration config = ArenaConfig.getConfig();
    String worldName = config.getString("editor.chest.world");
    if (worldName != null) {
      World world = Bukkit.getWorld(worldName);
      double x = config.getDouble("editor.chest.x");
      double y = config.getDouble("editor.chest.y");
      double z = config.getDouble("editor.chest.z");
      Location originalLocation = new Location(world, x, y, z);
      return chest.getLocation().equals(originalLocation);
    } 
    return false;
  }
  
  public static boolean isAnvil(Block anvil) {
    FileConfiguration config = ArenaConfig.getConfig();
    String worldName = config.getString("editor.anvil.world");
    if (worldName != null) {
      World world = Bukkit.getWorld(worldName);
      double x = config.getDouble("editor.anvil.x");
      double y = config.getDouble("editor.anvil.y");
      double z = config.getDouble("editor.anvil.z");
      Location originalLocation = new Location(world, x, y, z);
      return (anvil.getLocation().equals(originalLocation) && anvil.getType() == Material.ANVIL);
    } 
    return false;
  }
  
  public void setChest(Player player) {
    Block block = player.getTargetBlock((HashSet<Byte>) null, 100);
    if (block.getType() == Material.CHEST) {
      String world = block.getWorld().getName();
      double x = block.getX();
      double y = block.getY();
      double z = block.getZ();
      this.config.set("editor.chest.world", world);
      this.config.set("editor.chest.x", Double.valueOf(x));
      this.config.set("editor.chest.y", Double.valueOf(y));
      this.config.set("editor.chest.z", Double.valueOf(z));
      ArenaConfig.save();
      player.sendMessage(Settings.PRIMARY + "You set the editor chest.");
    } else {
      player.sendMessage("§cYou need to look at a chest to set the editor chest!");
    } 
  }
  
  public void setSign(Player player) {
    Block block = player.getTargetBlock((HashSet<Byte>) null, 100);
    if (block.getType() == Material.SIGN_POST || block.getType() == Material.SIGN || block.getType() == Material.WALL_SIGN) {
      String world = block.getWorld().getName();
      double x = block.getX();
      double y = block.getY();
      double z = block.getZ();
      this.config.set("editor.sign.world", world);
      this.config.set("editor.sign.x", Double.valueOf(x));
      this.config.set("editor.sign.y", Double.valueOf(y));
      this.config.set("editor.sign.z", Double.valueOf(z));
      ArenaConfig.save();
      player.sendMessage(Settings.PRIMARY + "You set the editor sign.");
    } else {
      player.sendMessage("§cYou need to look at a sign to set the editor sign.");
    } 
  }
  
  public void setAnvil(Player player) {
    Block block = player.getTargetBlock((HashSet<Byte>) null, 100);
    if (block.getType() == Material.ANVIL) {
      String world = block.getWorld().getName();
      double x = block.getX();
      double y = block.getY();
      double z = block.getZ();
      this.config.set("editor.anvil.world", world);
      this.config.set("editor.anvil.x", Double.valueOf(x));
      this.config.set("editor.anvil.y", Double.valueOf(y));
      this.config.set("editor.anvil.z", Double.valueOf(z));
      ArenaConfig.save();
      player.sendMessage(Settings.PRIMARY + "You set the editor anvil.");
    } else {
      player.sendMessage("§cYou need to look at an anvil to set the editor anvil.");
    } 
  }
  
  public Location getLocation() {
    String worldName = this.config.getString("editor.location.world");
    if (worldName != null) {
      World world = Bukkit.getWorld(worldName);
      double x = this.config.getDouble("editor.location.x");
      double y = this.config.getDouble("editor.location.y");
      double z = this.config.getDouble("editor.location.z");
      float yaw = (float)this.config.getDouble("editor.location.yaw");
      float pitch = (float)this.config.getDouble("editor.location.pitch");
      return new Location(world, x, y, z, yaw, pitch);
    } 
    return null;
  }
  
  public void setLocation(Player player) {
    Location location = player.getLocation();
    String world = location.getWorld().getName();
    double x = location.getX();
    double y = location.getY();
    double z = location.getZ();
    float yaw = location.getYaw();
    float pitch = location.getPitch();
    this.config.set("editor.location.world", world);
    this.config.set("editor.location.x", Double.valueOf(x));
    this.config.set("editor.location.y", Double.valueOf(y));
    this.config.set("editor.location.z", Double.valueOf(z));
    this.config.set("editor.location.yaw", Float.valueOf(yaw));
    this.config.set("editor.location.pitch", Float.valueOf(pitch));
    ArenaConfig.save();
    player.sendMessage(Settings.PRIMARY + "You set the editor location.");
  }
}
