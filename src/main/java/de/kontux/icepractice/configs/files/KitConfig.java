package de.kontux.icepractice.configs.files;

import de.kontux.icepractice.IcePracticePlugin;
import java.io.File;
import java.io.IOException;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class KitConfig {
  private static File file;
  
  private static FileConfiguration kitFile;
  
  public static void load() {
    file = new File(Bukkit.getServer().getPluginManager().getPlugin("IcePractice").getDataFolder(), "kits.yml");
    if (!file.exists()) {
      try {
        file.createNewFile();
      } catch (IOException e) {
        e.printStackTrace();
      } 
      IcePracticePlugin.getInstance().saveResource("kits.yml", true);
    } 
    kitFile = (FileConfiguration)YamlConfiguration.loadConfiguration(file);
  }
  
  public static FileConfiguration get() {
    return kitFile;
  }
  
  public static void save() {
    try {
      kitFile.save(file);
    } catch (IOException e) {
      e.printStackTrace();
    } 
  }
  
  public static void reload() {
    kitFile = (FileConfiguration)YamlConfiguration.loadConfiguration(file);
  }
}
