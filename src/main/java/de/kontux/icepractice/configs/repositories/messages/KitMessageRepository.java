package de.kontux.icepractice.configs.repositories.messages;

import de.kontux.icepractice.configs.Config;
import de.kontux.icepractice.configs.Settings;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.file.FileConfiguration;

public class KitMessageRepository {
  private final FileConfiguration config = Config.MESSAGES.getConfig();
  
  private final Configuration defaults = this.config.getDefaults();
  
  private final boolean useColours = (new BasicMessageRepository()).useColours();
  
  public String getNotExistMessage() {
    String entry = getTextWithColour("messages.kits.notexist");
    if (entry == null)
      entry = this.defaults.getString("messages.kits.notexist"); 
    if (this.useColours)
      return Settings.PRIMARY + entry; 
    return entry;
  }
  
  public String getKitCreateMessage(String kitName) {
    String entry = getTextWithColour("messages.kits.create");
    if (entry == null)
      entry = this.defaults.getString("messages.kits.create"); 
    if (this.useColours)
      return Settings.PRIMARY + entry.replace("%kit%", Settings.SECONDARY + kitName + Settings.PRIMARY); 
    return entry.replace("%kit%", kitName);
  }
  
  public String getKitSetInvMessage(String kitName) {
    String entry = getTextWithColour("messages.kits.setinv");
    if (entry == null)
      entry = this.defaults.getString("messages.kits.setinv"); 
    if (this.useColours)
      return Settings.PRIMARY + entry.replace("%kit%", Settings.SECONDARY + kitName + Settings.PRIMARY); 
    return entry.replace("%kit%", kitName);
  }
  
  public String getKitDeleteMessage(String kitName) {
    String entry = getTextWithColour("messages.kits.delete");
    if (entry == null)
      entry = this.defaults.getString("messages.kits.delete"); 
    if (this.useColours)
      return Settings.PRIMARY + entry.replace("%kit%", Settings.SECONDARY + kitName + Settings.PRIMARY); 
    return entry.replace("%kit%", kitName);
  }
  
  public String getSumoTrueMessage(String kitName) {
    String entry = getTextWithColour("messages.kits.sumo-true");
    if (entry == null)
      entry = this.defaults.getString("messages.kits.sumo-true"); 
    if (this.useColours)
      return Settings.PRIMARY + entry.replace("%kit%", Settings.SECONDARY + kitName + Settings.PRIMARY); 
    return entry.replace("%kit%", kitName);
  }
  
  public String getSumoFalseMessage(String kitName) {
    String entry = getTextWithColour("messages.kits.sumo-false");
    if (entry == null)
      entry = this.defaults.getString("messages.kits.sumo-false"); 
    if (this.useColours)
      return Settings.PRIMARY + entry.replace("%kit%", Settings.SECONDARY + kitName + Settings.PRIMARY); 
    return entry.replace("%kit%", kitName);
  }
  
  public String getComboTrueMessage(String kitName) {
    String entry = getTextWithColour("messages.kits.combo-true");
    if (entry == null)
      entry = this.defaults.getString("messages.kits.combo-true"); 
    if (this.useColours)
      return Settings.PRIMARY + entry.replace("%kit%", Settings.SECONDARY + kitName + Settings.PRIMARY); 
    return entry.replace("%kit%", kitName);
  }
  
  public String getComboFalseMessage(String kitName) {
    String entry = getTextWithColour("messages.kits.combo-false");
    if (entry == null)
      entry = this.defaults.getString("messages.kits.combo-false"); 
    if (this.useColours)
      return Settings.PRIMARY + entry.replace("%kit%", Settings.SECONDARY + kitName + Settings.PRIMARY); 
    return entry.replace("%kit%", kitName);
  }
  
  public String getGiveMessage(String kitName) {
    String entry = getTextWithColour("messages.kits.give-kit");
    if (entry == null)
      entry = this.defaults.getString("messages.kits.give-kit"); 
    if (this.useColours)
      return Settings.PRIMARY + entry.replace("%kit%", Settings.SECONDARY + kitName + Settings.PRIMARY); 
    return entry.replace("%kit%", kitName);
  }
  
  public String getRankedTrueMessage(String kitName) {
    String entry = getTextWithColour("messages.kits.ranked-true");
    if (entry == null)
      entry = this.defaults.getString("messages.kits.ranked-true"); 
    if (this.useColours)
      return Settings.PRIMARY + entry.replace("%kit%", Settings.SECONDARY + kitName + Settings.PRIMARY); 
    return entry.replace("%kit%", kitName);
  }
  
  public String getRankedFalseMessage(String kitName) {
    String entry = getTextWithColour("messages.kits.ranked-false");
    if (entry == null)
      entry = this.defaults.getString("messages.kits.ranked-false"); 
    if (this.useColours)
      return Settings.PRIMARY + entry.replace("%kit%", Settings.SECONDARY + kitName + Settings.PRIMARY); 
    return entry.replace("%kit%", kitName);
  }
  
  public String getIconChangeMessage(String kitName, Material item) {
    String entry = getTextWithColour("messages.kits.icon-change");
    if (entry == null)
      entry = this.defaults.getString("messages.kits.icon-change"); 
    if (this.useColours)
      return Settings.PRIMARY + entry.replace("%kit%", Settings.SECONDARY + kitName + Settings.PRIMARY).replace("%item%", Settings.SECONDARY + item.name() + Settings.PRIMARY); 
    return entry.replace("%kit%", kitName).replace("%item%", item.name());
  }
  
  public String getCooldownMessage(String cooldown) {
    String entry = getTextWithColour("messages.kits.cooldown");
    if (entry == null)
      entry = this.defaults.getString("messages.kits.cooldown"); 
    if (this.useColours)
      return Settings.PRIMARY + entry.replace("%cooldown%", Settings.SECONDARY + cooldown + Settings.PRIMARY); 
    return entry.replace("%cooldown%", cooldown);
  }
  
  public String getTextWithColour(String path) {
    String stringWithColour = this.config.getString(path);
    if (stringWithColour != null)
      stringWithColour = ChatColor.translateAlternateColorCodes('&', stringWithColour); 
    return stringWithColour;
  }
}
