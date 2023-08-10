package de.kontux.icepractice.configs.repositories.messages;

import de.kontux.icepractice.configs.files.MessageConfig;
import de.kontux.icepractice.configs.repositories.ChatColourPrefix;
import org.bukkit.ChatColor;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.file.FileConfiguration;

public class QueueMessageRepository {
  private FileConfiguration config = MessageConfig.get();
  
  private Configuration defaults = this.config.getDefaults();
  
  private boolean useColours = (new BasicMessageRepository()).useColours();
  
  private ChatColourPrefix prefix = new ChatColourPrefix();
  
  private ChatColor primary = this.prefix.getMainColour();
  
  private ChatColor secondary = this.prefix.getHighlightColour();
  
  public String getUnrankedJoinMessage(String kit) {
    String entry = getTextWithColour("messages.queue.join-unranked");
    if (entry == null)
      entry = this.defaults.getString("messages.queue.join-unranked"); 
    if (this.useColours)
      return this.primary + entry.replace("%kit%", this.secondary + kit + this.primary); 
    return entry.replace("%kit%", kit);
  }
  
  public String getRankedJoinMessage(String kit, int elo) {
    String entry = getTextWithColour("messages.queue.join-ranked");
    if (entry == null)
      entry = this.defaults.getString("messages.queue.join-ranked"); 
    if (this.useColours)
      return this.primary + entry.replace("%kit%", this.secondary + kit + this.primary).replace("%elo%", String.valueOf(elo)); 
    return entry.replace("%kit%", kit).replace("%elo%", String.valueOf(elo));
  }
  
  public String getPartyJoinMessage(String kit) {
    String entry = getTextWithColour("messages.queue.join-party");
    if (entry == null)
      entry = this.defaults.getString("messages.queue.join-party"); 
    if (this.useColours)
      return this.primary + entry.replace("%kit%", this.secondary + kit + this.primary); 
    return entry.replace("%kit%", kit);
  }
  
  public String getLeaveMessage() {
    String entry = getTextWithColour("messages.queue.left-queue");
    if (entry == null)
      entry = this.defaults.getString("messages.queue.left-queue"); 
    if (this.useColours)
      return this.primary + entry; 
    return entry;
  }
  
  public String getTextWithColour(String path) {
    String stringWithColour = this.config.getString(path);
    if (stringWithColour != null)
      stringWithColour = ChatColor.translateAlternateColorCodes('&', stringWithColour); 
    return stringWithColour;
  }
}
