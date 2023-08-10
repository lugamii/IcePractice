package de.kontux.icepractice.configs.files;

import de.kontux.icepractice.IcePracticePlugin;
import java.io.File;
import java.io.IOException;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class JoinItemConfig {
  private static File file;
  
  private static FileConfiguration joinItemConfiguration;
  
  public static void load() {
    file = new File(Bukkit.getServer().getPluginManager().getPlugin("IcePractice").getDataFolder(), "joinitems.yml");
    if (!file.exists())
      IcePracticePlugin.getInstance().saveResource("joinitems.yml", true); 
    joinItemConfiguration = (FileConfiguration)YamlConfiguration.loadConfiguration(file);
  }
  
  public static FileConfiguration get() {
    return joinItemConfiguration;
  }
  
  public static void save() {
    try {
      joinItemConfiguration.save(file);
    } catch (IOException e) {
      e.printStackTrace();
    } 
  }
  
  public static void reload() {
    joinItemConfiguration = (FileConfiguration)YamlConfiguration.loadConfiguration(file);
  }
}
