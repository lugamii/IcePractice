package de.kontux.icepractice.configs;

import de.kontux.icepractice.IcePracticePlugin;
import java.io.File;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public enum Config {
  ARENA("arena.yml"),
  KITS("kits.yml"),
  JOIN_ITEMS("joinitems.yml"),
  MESSAGES("messages.yml"),
  PLAYER_DATA("playerdata.yml");
  
  private final FileConfiguration config;
  
  Config(String resourcePath) {
    File file = new File(IcePracticePlugin.getFolder(), resourcePath);
    if (!file.exists()) {
      Bukkit.getServer().getPluginManager().getPlugin("IcePractice").saveResource(resourcePath, true);
      file = new File(Bukkit.getServer().getPluginManager().getPlugin("IcePractice").getDataFolder(), resourcePath);
    } 
    this.config = (FileConfiguration)YamlConfiguration.loadConfiguration(file);
  }
  
  public final FileConfiguration getConfig() {
    return this.config;
  }
}
