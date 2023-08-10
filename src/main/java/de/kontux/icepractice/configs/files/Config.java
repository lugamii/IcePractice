package de.kontux.icepractice.configs.files;

import de.kontux.icepractice.IcePracticePlugin;
import java.io.File;
import java.io.IOException;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class Config {
  private final String resourcePath;
  
  private FileConfiguration config;
  
  private File file;
  
  public Config(String resourcePath) {
    this.resourcePath = resourcePath;
  }
  
  public void load(Plugin plugin) {
    this.file = new File(plugin.getDataFolder(), this.resourcePath);
    if (!this.file.exists()) {
      plugin.saveResource(this.resourcePath, true);
      this.file = new File(plugin.getDataFolder(), this.resourcePath);
    } 
    this.config = (FileConfiguration)YamlConfiguration.loadConfiguration(this.file);
  }
  
  public FileConfiguration getConfig() {
    if (this.config == null)
      throw new IllegalStateException(this.resourcePath + " hasn't been loaded yet and cannot be accessed."); 
    return this.config;
  }
  
  public void save(IcePracticePlugin plugin) {
    try {
      this.config.save(this.file);
    } catch (IOException e) {
      plugin.log(ChatColor.RED + "Failed to save " + this.resourcePath + "! Cause: " + e.getMessage());
    } 
  }
}
