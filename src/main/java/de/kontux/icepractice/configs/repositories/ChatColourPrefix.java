package de.kontux.icepractice.configs.repositories;

import de.kontux.icepractice.IcePracticePlugin;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

public class ChatColourPrefix {
  FileConfiguration config = IcePracticePlugin.getInstance().getConfig();
  
  public ChatColor getMainColour() {
    String colour = this.config.getString("config.colours.primary");
    return ChatColor.valueOf(colour);
  }
  
  public ChatColor getHighlightColour() {
    String colour = this.config.getString("config.colours.secondary");
    return ChatColor.valueOf(colour);
  }
}
