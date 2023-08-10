package de.kontux.icepractice.configs.repositories.messages;

import de.kontux.icepractice.api.config.MatchMessages;
import de.kontux.icepractice.configs.files.MessageConfig;
import de.kontux.icepractice.configs.repositories.ChatColourPrefix;
import org.bukkit.ChatColor;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class MatchMessageRepository implements MatchMessages {
  private final FileConfiguration config = MessageConfig.get();
  
  private final Configuration defaults = this.config.getDefaults();
  
  private final boolean useColours = (new BasicMessageRepository()).useColours();
  
  private final ChatColourPrefix prefix = new ChatColourPrefix();
  
  private final ChatColor primary = this.prefix.getMainColour();
  
  private final ChatColor secondary = this.prefix.getHighlightColour();
  
  public String getCooldownMessage(int cooldown) {
    String entry = getTextWithColour("messages.match.cooldown");
    if (entry == null)
      entry = this.defaults.getString("messages.match.cooldown"); 
    if (this.useColours)
      return this.primary + entry.replace("%cooldown%", this.secondary + String.valueOf(cooldown) + this.primary); 
    return entry.replace("%cooldown%", String.valueOf(cooldown));
  }
  
  public String getFFAStartingMessage(String kit) {
    String entry = getTextWithColour("messages.match.starting-ffa-match");
    if (entry == null)
      entry = this.defaults.getString("messages.match.starting-ffa-match"); 
    if (this.useColours)
      return this.primary + entry.replace("%kit%", this.secondary + kit + this.primary); 
    return entry.replace("%cooldown%", kit);
  }
  
  public String getStartMessage() {
    String entry = getTextWithColour("messages.match.match-started");
    if (entry == null)
      entry = this.defaults.getString("messages.match.match-started"); 
    if (this.useColours)
      return this.primary + entry; 
    return entry;
  }
  
  public String getStartingMessage(Player opponent, String kit) {
    String entry = getTextWithColour("messages.match.starting-match");
    if (entry == null)
      entry = this.defaults.getString("messages.match.starting-match"); 
    if (this.useColours)
      return this.primary + entry.replace("%opponent%", this.secondary + opponent.getDisplayName() + this.primary).replace("%kit%", this.secondary + kit + this.primary); 
    return entry.replace("%opponent%", opponent.getDisplayName()).replace("%kit%", kit);
  }
  
  public String getTeamStartingMessage(Player opponent, String kit) {
    String entry = getTextWithColour("messages.match.team-starting-match");
    if (entry == null)
      entry = this.defaults.getString("messages.match.team-starting-match"); 
    if (this.useColours)
      return this.primary + entry.replace("%opponent%", this.secondary + opponent.getDisplayName() + this.primary).replace("%kit%", this.secondary + kit + this.primary); 
    return entry.replace("%opponent%", opponent.getDisplayName()).replace("%kit%", kit);
  }
  
  public String getEndMessage() {
    String entry = getTextWithColour("messages.match.match-ended");
    if (entry == null)
      entry = this.defaults.getString("messages.match.match-ended"); 
    if (this.useColours)
      return this.primary + entry; 
    return entry;
  }
  
  public String getNoArenaMessage() {
    String entry = getTextWithColour("messages.match.no-arena");
    if (entry == null)
      entry = this.defaults.getString("messages.match.no-arena"); 
    if (this.useColours)
      return this.primary + entry; 
    return entry;
  }
  
  public String getComboMessage() {
    String entry = getTextWithColour("messages.match.combo-info");
    if (entry == null)
      entry = this.defaults.getString("messages.match.combo-info"); 
    if (this.useColours)
      return this.primary + entry; 
    return entry;
  }
  
  public String getNowSpectatingMessage(Player player) {
    String entry = getTextWithColour("messages.match.now-spectating");
    if (entry == null)
      entry = this.defaults.getString("messages.match.now-spectating"); 
    if (this.useColours)
      return this.primary + entry.replace("%player%", this.secondary + player.getDisplayName() + this.primary); 
    return entry.replace("%player%", player.getDisplayName());
  }
  
  public String getNoLongerSpectatingMessage(Player player) {
    String entry = getTextWithColour("messages.match.nolonger-spectating");
    if (entry == null)
      entry = this.defaults.getString("messages.match.nolonger-spectating"); 
    if (this.useColours)
      return this.primary + entry.replace("%player%", this.secondary + player.getDisplayName() + this.primary); 
    return entry.replace("%player%", player.getDisplayName());
  }
  
  public String getLeftSpectatingMessage() {
    String entry = getTextWithColour("messages.match.left-spectator");
    if (entry == null)
      entry = this.defaults.getString("messages.match.left-spectator"); 
    if (this.useColours)
      return this.primary + entry; 
    return entry;
  }
  
  public String getSoloSpectatorMessage(Player player1, Player player2, String kit) {
    String entry = getTextWithColour("messages.match.solo-spectator");
    if (entry == null)
      entry = this.defaults.getString("messages.match.solo-spectator"); 
    if (this.useColours)
      return this.primary + entry.replace("%player1%", this.secondary + player1.getDisplayName() + this.primary).replace("%player2%", this.secondary + player2.getDisplayName() + this.primary).replace("%kit%", this.secondary + kit + this.primary); 
    return entry.replace("%player1%", player1.getDisplayName()).replace("%player2%", player2.getDisplayName()).replace("%kit%", kit);
  }
  
  public String getTeamSpectatorMessage(Player team1, Player team2, String kit) {
    String entry = getTextWithColour("messages.match.team-spectator");
    if (entry == null)
      entry = this.defaults.getString("messages.match.team-spectator"); 
    if (this.useColours)
      return this.primary + entry.replace("%player1%", this.secondary + team1.getDisplayName() + this.primary).replace("%player2%", this.secondary + team2.getDisplayName() + this.primary).replace("%kit%", this.secondary + kit + this.primary); 
    return entry.replace("%player1%", team1.getDisplayName()).replace("%player2%", team2.getDisplayName()).replace("%kit%", kit);
  }
  
  public String getFFASpectatorMessage(String kit) {
    String entry = getTextWithColour("messages.match.ffa-spectator");
    if (entry == null)
      entry = this.defaults.getString("messages.match.ffa-spectator"); 
    if (this.useColours)
      return this.primary + entry.replace("%kit%", this.secondary + kit + this.primary); 
    return entry.replace("%cooldown%", kit);
  }
  
  public String getTeamFightDeathMessage(Player dead, Player killer) {
    String entry = getTextWithColour("messages.match.team-death");
    if (entry == null)
      entry = this.defaults.getString("messages.match.team-death"); 
    if (this.useColours)
      return this.primary + entry.replace("%dead%", this.secondary + dead.getDisplayName() + this.primary).replace("%killer%", this.secondary + killer.getDisplayName() + this.primary); 
    return entry.replace("%dead%", dead.getDisplayName()).replace("%killer%", killer.getDisplayName());
  }
  
  public String getTextWithColour(String path) {
    String stringWithColour = this.config.getString(path);
    if (stringWithColour != null)
      stringWithColour = ChatColor.translateAlternateColorCodes('&', stringWithColour); 
    return stringWithColour;
  }
}
