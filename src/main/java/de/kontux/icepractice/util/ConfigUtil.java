package de.kontux.icepractice.util;

import de.kontux.icepractice.configs.Settings;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

public class ConfigUtil {
  public static String getColouredString(FileConfiguration config, String path) {
    if (path == null)
      return null; 
    String entry = config.getString(path);
    if (entry != null)
      return ChatColor.translateAlternateColorCodes('&', entry); 
    return null;
  }
  
  public static String getColouredString(ConfigurationSection config, String path) {
    if (path == null)
      return null; 
    String entry = config.getString(path);
    if (entry != null)
      return ChatColor.translateAlternateColorCodes('&', entry); 
    return null;
  }
  
  public static boolean useWorld(World world) {
    if (Settings.BLACKLISTED_WORLDS == null)
      return true; 
    return !Settings.BLACKLISTED_WORLDS.contains(world.getName());
  }
}
