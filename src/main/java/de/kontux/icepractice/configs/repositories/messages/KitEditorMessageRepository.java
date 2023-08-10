package de.kontux.icepractice.configs.repositories.messages;

import de.kontux.icepractice.configs.files.MessageConfig;
import de.kontux.icepractice.configs.repositories.ChatColourPrefix;
import org.bukkit.ChatColor;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.file.FileConfiguration;

public class KitEditorMessageRepository {
  private FileConfiguration config = MessageConfig.get();
  
  private Configuration defaults = this.config.getDefaults();
  
  private boolean useColours = (new BasicMessageRepository()).useColours();
  
  private ChatColourPrefix prefix = new ChatColourPrefix();
  
  private ChatColor primary = this.prefix.getMainColour();
  
  private ChatColor secondary = this.prefix.getHighlightColour();
  
  public String getNotSetupMessage() {
    String entry = getTextWithColour("messages.editor.not-setup");
    if (entry == null)
      entry = this.defaults.getString("messages.editor.not-setup"); 
    if (this.useColours)
      return this.primary + entry; 
    return entry;
  }
  
  public String getSetNamePromptMessage() {
    String entry = getTextWithColour("messages.editor.set-name-prompt");
    if (entry == null)
      entry = this.defaults.getString("messages.editor.set-name-prompt"); 
    if (this.useColours)
      return this.primary + entry; 
    return entry;
  }
  
  public String getSetNameMessage(String name) {
    String entry = getTextWithColour("messages.editor.set-name");
    if (entry == null)
      entry = this.defaults.getString("messages.editor.set-name"); 
    if (this.useColours)
      return this.primary + entry.replace("%name%", this.secondary + name + this.primary); 
    return entry.replace("%name%", name);
  }
  
  public String getSavedKitMessage(String kit) {
    String entry = getTextWithColour("messages.editor.save-kit");
    if (entry == null)
      entry = this.defaults.getString("messages.editor.save-kit"); 
    if (this.useColours)
      return this.primary + entry.replace("%kit%", this.secondary + kit + this.primary); 
    return entry.replace("%kit%", kit);
  }
  
  public String getDeleteMessage(String kitName) {
    String entry = getTextWithColour("messages.editor.delete-kit");
    if (entry == null)
      entry = this.defaults.getString("messages.editor.delete-kit"); 
    if (this.useColours)
      return this.primary + entry.replace("%kit%", this.secondary + kitName + this.primary); 
    return entry.replace("%kit%", kitName);
  }
  
  public String getTextWithColour(String path) {
    String stringWithColour = this.config.getString(path);
    if (stringWithColour != null)
      stringWithColour = ChatColor.translateAlternateColorCodes('&', stringWithColour); 
    return stringWithColour;
  }
}
