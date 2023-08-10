package de.kontux.icepractice.configs.repositories.messages;

import de.kontux.icepractice.configs.files.MessageConfig;
import de.kontux.icepractice.configs.repositories.ChatColourPrefix;
import org.bukkit.ChatColor;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class EventMessageRepository {
  private FileConfiguration config = MessageConfig.get();
  
  private Configuration defaults = this.config.getDefaults();
  
  private boolean useColours = (new BasicMessageRepository()).useColours();
  
  private ChatColourPrefix prefix = new ChatColourPrefix();
  
  private ChatColor primary = this.prefix.getMainColour();
  
  private ChatColor secondary = this.prefix.getHighlightColour();
  
  public String getNoPermMessage() {
    String entry = getTextWithColour("messages.events.no-permission");
    if (entry == null)
      entry = this.defaults.getString("messages.events.no-permission"); 
    if (this.useColours)
      return this.primary + entry; 
    return entry;
  }
  
  public String getBroadcastMessage(Player host, String event) {
    String entry = getTextWithColour("messages.events.broadcast-message");
    if (entry == null)
      entry = this.defaults.getString("messages.events.broadcast-message"); 
    if (this.useColours)
      return this.primary + entry.replace("%player%", this.secondary + host.getDisplayName() + this.primary).replace("%event%", this.secondary + event + this.primary); 
    return entry.replace("%player%", host.getDisplayName()).replace("%event%", event);
  }
  
  public String getJoinMessage(String player, int playersLeft) {
    String entry = getTextWithColour("messages.events.join");
    if (entry == null)
      entry = this.defaults.getString("messages.events.join"); 
    if (this.useColours)
      return this.primary + entry.replace("%player%", this.secondary + player + this.primary).replace("%players-left%", this.secondary + String.valueOf(playersLeft) + this.primary); 
    return entry.replace("%player%", player).replace("%players-left%", String.valueOf(playersLeft));
  }
  
  public String getLeaveMessage(String player, int playersLeft) {
    String entry = getTextWithColour("messages.events.leave");
    if (entry == null)
      entry = this.defaults.getString("messages.events.leave"); 
    if (this.useColours)
      return this.primary + entry.replace("%player%", this.secondary + player + this.primary).replace("%players-left%", this.secondary + String.valueOf(playersLeft) + this.primary); 
    return entry.replace("%player%", player).replace("%players-left%", String.valueOf(playersLeft));
  }
  
  public String getPasswordMessage() {
    String entry = getTextWithColour("messages.events.password");
    if (entry == null)
      entry = this.defaults.getString("messages.events.password"); 
    if (this.useColours)
      return this.primary + entry; 
    return entry;
  }
  
  public String getStartMessage() {
    String entry = getTextWithColour("messages.events.expireCooldown");
    if (entry == null)
      entry = this.defaults.getString("messages.events.expireCooldown"); 
    if (this.useColours)
      return this.primary + entry; 
    return entry;
  }
  
  public String getNotEnoughPlayersMessage() {
    String entry = getTextWithColour("messages.events.not-enough-players");
    if (entry == null)
      entry = this.defaults.getString("messages.events.not-enough-players"); 
    if (this.useColours)
      return this.primary + entry; 
    return entry;
  }
  
  public String getWrongPasswordMessage() {
    String entry = getTextWithColour("messages.events.wrong-password");
    if (entry == null)
      entry = this.defaults.getString("messages.events.wrong-password"); 
    if (this.useColours)
      return this.primary + entry; 
    return entry;
  }
  
  public String getEventFullMessage() {
    String entry = getTextWithColour("messages.events.event-full");
    if (entry == null)
      entry = this.defaults.getString("messages.events.event-full"); 
    if (this.useColours)
      return this.primary + entry; 
    return entry;
  }
  
  public String getHostMessage(String event, int id) {
    String entry = getTextWithColour("messages.events.host-message");
    if (entry == null)
      entry = this.defaults.getString("messages.events.host-message"); 
    if (this.useColours)
      return this.primary + entry.replace("%event%", this.secondary + event + this.primary).replace("%id%", this.secondary + String.valueOf(id) + this.primary); 
    return entry.replace("%event", event).replace("%id%", String.valueOf(id));
  }
  
  public String getWinnerMessage(String player, String event) {
    String entry = getTextWithColour("messages.events.winner-broadcast");
    if (entry == null)
      entry = this.defaults.getString("messages.events.winner-broadcast"); 
    if (this.useColours)
      return this.primary + entry.replace("%player%", this.secondary + player + this.primary).replace("%event%", this.secondary + event + this.primary); 
    return entry.replace("%player%", player).replace("%event%", event);
  }
  
  public String getEliminatedMessage(String winner, String loser, int playerLeft) {
    String entry = getTextWithColour("messages.events.eliminated");
    if (entry == null)
      entry = this.defaults.getString("messages.events.eliminated"); 
    if (this.useColours)
      return this.primary + entry.replace("%winner%", this.secondary + winner + this.primary).replace("%loser%", this.secondary + loser + this.primary).replace("%players-left%", String.valueOf(playerLeft)); 
    return entry.replace("%winner%", winner).replace("%loser%", loser).replace("%players-left%", String.valueOf(playerLeft));
  }
  
  public String getStartingMatchMessage(String player1, String player2) {
    String entry = getTextWithColour("messages.events.starting-match");
    if (entry == null)
      entry = this.defaults.getString("messages.events.starting-match"); 
    if (this.useColours)
      return this.primary + entry.replace("%player1%", this.secondary + player1 + this.primary).replace("%player2%", this.secondary + player2 + this.primary); 
    return entry.replace("%player1%", player1).replace("%player2%", player2);
  }
  
  public String getTextWithColour(String path) {
    String stringWithColour = this.config.getString(path);
    if (stringWithColour != null)
      stringWithColour = ChatColor.translateAlternateColorCodes('&', stringWithColour); 
    return stringWithColour;
  }
}
