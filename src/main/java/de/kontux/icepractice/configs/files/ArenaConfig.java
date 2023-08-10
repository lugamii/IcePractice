package de.kontux.icepractice.configs.files;

import de.kontux.icepractice.IcePracticePlugin;
import java.io.File;
import java.io.IOException;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class ArenaConfig {
  private static File file;
  
  private static FileConfiguration arenaConfiguration;
  
  public static void load() {
    file = new File(Bukkit.getServer().getPluginManager().getPlugin("IcePractice").getDataFolder(), "arena.yml");
    if (!file.exists())
      IcePracticePlugin.getInstance().saveResource("arena.yml", true); 
    arenaConfiguration = (FileConfiguration)YamlConfiguration.loadConfiguration(file);
  }
  
  public static FileConfiguration getConfig() {
    return arenaConfiguration;
  }
  
  public static void save() {
    try {
      arenaConfiguration.save(file);
    } catch (IOException e) {
      e.printStackTrace();
    } 
  }
  
  public static void reload() {
    arenaConfiguration = (FileConfiguration)YamlConfiguration.loadConfiguration(file);
  }
}
