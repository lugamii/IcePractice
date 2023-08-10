package de.kontux.icepractice.configs.repositories.messages;

import de.kontux.icepractice.configs.files.MessageConfig;
import de.kontux.icepractice.configs.repositories.ChatColourPrefix;
import org.bukkit.ChatColor;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class BasicMessageRepository {
  private final FileConfiguration config = MessageConfig.get();
  
  private final Configuration defaults = this.config.getDefaults();
  
  private final ChatColourPrefix prefix = new ChatColourPrefix();
  
  public boolean useColours() {
    return this.config.getBoolean("messages.use-default-colours");
  }
  
  public String getHasNotSentRequest(String player) {
    String entry = getTextWithColour("messages.general.no-duel-request");
    if (entry == null)
      entry = this.defaults.getString("messages.general.no-duel-request"); 
    if (useColours())
      return this.prefix.getMainColour() + entry.replace("%player%", this.prefix.getHighlightColour() + player + this.prefix.getMainColour()); 
    return entry.replace("%player%", player);
  }
  
  public String getRequestMessage(Player player, String kit) {
    String entry = getTextWithColour("messages.general.request");
    if (entry == null)
      entry = this.defaults.getString("messages.general.request"); 
    if (useColours())
      return this.prefix.getMainColour() + entry.replace("%player%", this.prefix.getHighlightColour() + player.getDisplayName() + this.prefix.getMainColour()).replace("%kit%", this.prefix.getHighlightColour() + kit + this.prefix.getMainColour()); 
    return entry.replace("%player%", player.getDisplayName()).replace("%kit%", kit);
  }
  
  public String getSendRequestMessage(Player opponent, String kit) {
    String entry = getTextWithColour("messages.general.send-request");
    if (entry == null)
      entry = this.defaults.getString("messages.general.send-request"); 
    if (useColours())
      return this.prefix.getMainColour() + entry.replace("%player%", this.prefix.getHighlightColour() + opponent.getDisplayName() + this.prefix.getMainColour()).replace("%kit%", this.prefix.getHighlightColour() + kit + this.prefix.getMainColour()); 
    return entry.replace("%player%", opponent.getDisplayName()).replace("%kit%", kit);
  }
  
  public String getAcceptedRequest(Player player) {
    String entry = getTextWithColour("messages.general.accepted-request");
    if (entry == null)
      entry = this.defaults.getString("messages.general.accepted-request"); 
    if (useColours())
      return this.prefix.getMainColour() + entry.replace("%player%", this.prefix.getHighlightColour() + player.getDisplayName() + this.prefix.getMainColour()); 
    return entry.replace("%player%", player.getDisplayName());
  }
  
  public String getTextWithColour(String path) {
    String stringWithColour = this.config.getString(path);
    if (stringWithColour != null)
      stringWithColour = ChatColor.translateAlternateColorCodes('&', stringWithColour); 
    return stringWithColour;
  }
}
