package de.kontux.icepractice.configs.files;

import java.io.File;
import java.io.IOException;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class PlayerConfig {
  private static File file;
  
  private static FileConfiguration playerConfiguration;
  
  public static void load() {
    file = new File(Bukkit.getServer().getPluginManager().getPlugin("IcePractice").getDataFolder(), "playerdata.yml");
    if (!file.exists())
      try {
        file.createNewFile();
      } catch (IOException e) {
        e.printStackTrace();
      }  
    playerConfiguration = (FileConfiguration)YamlConfiguration.loadConfiguration(file);
  }
  
  public static FileConfiguration get() {
    return playerConfiguration;
  }
  
  public static void save() {
    try {
      playerConfiguration.save(file);
    } catch (IOException e) {
      e.printStackTrace();
    } 
  }
  
  public static void reload() {
    playerConfiguration = (FileConfiguration)YamlConfiguration.loadConfiguration(file);
  }
}
