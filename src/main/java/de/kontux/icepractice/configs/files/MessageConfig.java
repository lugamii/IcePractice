package de.kontux.icepractice.configs.files;

import de.kontux.icepractice.IcePracticePlugin;
import java.io.File;
import java.io.IOException;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class MessageConfig {
  private static File file;
  
  private static FileConfiguration messageConfiguration;
  
  public static void load() {
    file = new File(IcePracticePlugin.getInstance().getDataFolder(), "messages.yml");
    if (!file.exists())
      IcePracticePlugin.getInstance().saveResource("messages.yml", true); 
    messageConfiguration = YamlConfiguration.loadConfiguration(file);
  }
  
  public static FileConfiguration get() {
    return messageConfiguration;
  }
  
  public static void save() {
    try {
      messageConfiguration.save(file);
    } catch (IOException e) {
      e.printStackTrace();
    } 
  }
  
  public static void reload() {
    messageConfiguration = YamlConfiguration.loadConfiguration(file);
  }
}
