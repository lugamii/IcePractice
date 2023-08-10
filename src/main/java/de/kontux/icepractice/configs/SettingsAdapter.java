package de.kontux.icepractice.configs;

import de.kontux.icepractice.api.config.PluginSettings;
import org.bukkit.ChatColor;

public class SettingsAdapter implements PluginSettings {
  public ChatColor getPrimaryColour() {
    return Settings.PRIMARY;
  }
  
  public ChatColor getSecondaryColour() {
    return Settings.SECONDARY;
  }
}
