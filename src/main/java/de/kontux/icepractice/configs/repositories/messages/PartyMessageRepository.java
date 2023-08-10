package de.kontux.icepractice.configs.repositories.messages;

import de.kontux.icepractice.configs.files.MessageConfig;
import de.kontux.icepractice.configs.repositories.ChatColourPrefix;
import org.bukkit.ChatColor;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class PartyMessageRepository {
  private FileConfiguration config = MessageConfig.get();
  
  private Configuration defaults = this.config.getDefaults();
  
  private boolean useColours = (new BasicMessageRepository()).useColours();
  
  private ChatColourPrefix prefix = new ChatColourPrefix();
  
  private ChatColor primary = this.prefix.getMainColour();
  
  private ChatColor secondary = this.prefix.getHighlightColour();
  
  public String getNoPartyMessage() {
    String entry = getTextWithColour("messages.party.no-party");
    if (entry == null)
      entry = this.defaults.getString("messages.party.no-party"); 
    if (this.useColours)
      return this.primary + entry; 
    return entry;
  }
  
  public String getCreateMessage() {
    String entry = getTextWithColour("messages.party.create");
    if (entry == null)
      entry = this.defaults.getString("messages.party.create"); 
    if (this.useColours)
      return this.primary + entry; 
    return entry;
  }
  
  public String getOpenMessage() {
    String entry = getTextWithColour("messages.party.open");
    if (entry == null)
      entry = this.defaults.getString("messages.party.open"); 
    if (this.useColours)
      return this.primary + entry; 
    return entry;
  }
  
  public String getCloseMessage() {
    String entry = getTextWithColour("messages.party.close");
    if (entry == null)
      entry = this.defaults.getString("messages.party.close"); 
    if (this.useColours)
      return this.primary + entry; 
    return entry;
  }
  
  public String getInvitedMessage(Player player) {
    String entry = getTextWithColour("messages.party.invited");
    if (entry == null)
      entry = this.defaults.getString("messages.party.invited"); 
    if (this.useColours)
      return this.primary + entry.replace("%player%", this.secondary + player.getPlayerListName() + this.primary); 
    return entry;
  }
  
  public String getInviteRequestMessage(Player player) {
    String entry = getTextWithColour("messages.party.invite-request");
    if (entry == null)
      entry = this.defaults.getString("messages.party.invite-request"); 
    if (this.useColours)
      return this.primary + entry.replace("%player%", this.secondary + player.getPlayerListName() + this.primary); 
    return entry;
  }
  
  public String getJoinedMessage(Player player) {
    String entry = getTextWithColour("messages.party.join");
    if (entry == null)
      entry = this.defaults.getString("messages.party.join"); 
    if (this.useColours)
      return this.primary + entry.replace("%player%", this.secondary + player.getPlayerListName() + this.primary); 
    return entry;
  }
  
  public String getLeftMessage(Player player) {
    String entry = getTextWithColour("messages.party.left");
    if (entry == null)
      entry = this.defaults.getString("messages.party.left"); 
    if (this.useColours)
      return this.primary + entry.replace("%player%", this.secondary + player.getPlayerListName() + this.primary); 
    return entry;
  }
  
  public String getNowLeaderMessage(Player player) {
    String entry = getTextWithColour("messages.party.now-leader");
    if (entry == null)
      entry = this.defaults.getString("messages.party.left"); 
    if (this.useColours)
      return this.primary + entry.replace("%player%", this.secondary + player.getPlayerListName() + this.primary); 
    return entry;
  }
  
  public String getKickedMessage(Player player) {
    String entry = getTextWithColour("messages.party.kicked");
    if (entry == null)
      entry = this.defaults.getString("messages.party.kicked"); 
    if (this.useColours)
      return this.primary + entry.replace("%player%", this.secondary + player.getPlayerListName() + this.primary); 
    return entry;
  }
  
  public String getOnlyLeaderMessage() {
    String entry = getTextWithColour("messages.party.only-leader");
    if (entry == null)
      entry = this.defaults.getString("messages.party.only-leader"); 
    if (this.useColours)
      return this.primary + entry; 
    return entry;
  }
  
  public String getNotEnoughPlayersMessage() {
    String entry = getTextWithColour("messages.party.not-enough-players");
    if (entry == null)
      entry = this.defaults.getString("messages.party.not-enough-players"); 
    if (this.useColours)
      return this.primary + entry; 
    return entry;
  }
  
  public String getNotInvitedMessage() {
    String entry = getTextWithColour("messages.party.not-invited");
    if (entry == null)
      entry = this.defaults.getString("messages.party.not-invited"); 
    if (this.useColours)
      return this.primary + entry; 
    return entry;
  }
  
  public String getSentRequestMessage(Player leader, String kit) {
    String entry = getTextWithColour("messages.party.sent-request");
    if (entry == null)
      entry = this.defaults.getString("messages.party.sent-request"); 
    if (this.useColours)
      return this.primary + entry.replace("%leader%", this.secondary + leader.getDisplayName() + this.primary).replace("%kit%", this.secondary + kit + this.primary); 
    return entry;
  }
  
  public String getReceivedRequestMessage(Player leader, String kit) {
    String entry = getTextWithColour("messages.party.received-request");
    if (entry == null)
      entry = this.defaults.getString("messages.party.received-request"); 
    if (this.useColours)
      return this.primary + entry.replace("%leader%", this.secondary + leader.getDisplayName() + this.primary).replace("%kit%", this.secondary + kit + this.primary); 
    return entry;
  }
  
  public String getTextWithColour(String path) {
    String stringWithColour = this.config.getString(path);
    if (stringWithColour != null)
      stringWithColour = ChatColor.translateAlternateColorCodes('&', stringWithColour); 
    return stringWithColour;
  }
}
