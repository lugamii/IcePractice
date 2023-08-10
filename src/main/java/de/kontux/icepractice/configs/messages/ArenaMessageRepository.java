package de.kontux.icepractice.configs.messages;

import de.kontux.icepractice.configs.files.MessageConfig;
import de.kontux.icepractice.configs.repositories.ChatColourPrefix;
import de.kontux.icepractice.configs.repositories.messages.BasicMessageRepository;
import org.bukkit.ChatColor;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.file.FileConfiguration;

public class ArenaMessageRepository {
  private final FileConfiguration config = MessageConfig.get();
  
  private final Configuration defaults = this.config.getDefaults();
  
  private final boolean useColours = (new BasicMessageRepository()).useColours();
  
  private final ChatColourPrefix prefix = new ChatColourPrefix();
  
  public String getArenaCreateMessage(String arenaName) {
    String entry = getTextWithColour("messages.arenas.create");
    if (entry == null)
      entry = this.defaults.getString("messages.arenas.create"); 
    if (this.useColours)
      return this.prefix.getMainColour() + entry.replace("%name%", this.prefix.getHighlightColour() + arenaName + this.prefix.getMainColour()); 
    return entry.replace("%name%", arenaName);
  }
  
  public String getArenaPos1Message(String arenaName) {
    String entry = getTextWithColour("messages.arenas.pos1");
    if (entry == null)
      entry = this.defaults.getString("messages.arenas.pos1"); 
    if (this.useColours)
      return this.prefix.getMainColour() + entry.replace("%name%", this.prefix.getHighlightColour() + arenaName + this.prefix.getMainColour()); 
    return entry.replace("%name%", arenaName);
  }
  
  public String getArenaPos2Message(String arenaName) {
    String entry = getTextWithColour("messages.arenas.pos2");
    if (entry == null)
      entry = this.defaults.getString("messages.arena-pos2"); 
    if (this.useColours)
      return this.prefix.getMainColour() + entry.replace("%name%", this.prefix.getHighlightColour() + arenaName + this.prefix.getMainColour()); 
    return entry.replace("%name%", arenaName);
  }
  
  public String getArenaDeleteMessage(String arenaName) {
    String entry = getTextWithColour("messages.arenas.delete");
    if (entry == null)
      entry = this.defaults.getString("messages.arenas.delete"); 
    if (this.useColours)
      return this.prefix.getMainColour() + entry.replace("%name%", this.prefix.getHighlightColour() + arenaName + this.prefix.getMainColour()); 
    return entry.replace("%name%", arenaName);
  }
  
  public String getArenaCenterMessage(String arenaName) {
    String entry = getTextWithColour("messages.arenas.center");
    if (entry == null)
      entry = this.defaults.getString("messages.arenas.center"); 
    if (this.useColours)
      return this.prefix.getMainColour() + entry.replace("%name%", this.prefix.getHighlightColour() + arenaName + this.prefix.getMainColour()); 
    return entry.replace("%name%", arenaName);
  }
  
  public String getArenaSumoTrueMessage(String arenaName) {
    String entry = getTextWithColour("messages.arenas.sumo-true");
    if (entry == null)
      entry = this.defaults.getString("messages.arena-sumo-true"); 
    if (this.useColours)
      return this.prefix.getMainColour() + entry.replace("%name%", this.prefix.getHighlightColour() + arenaName + this.prefix.getMainColour()); 
    return entry.replace("%name%", arenaName);
  }
  
  public String getArenaSpleefTrueMessage(String arenaName) {
    String entry = getTextWithColour("messages.arenas.spleef-true");
    if (entry == null)
      entry = this.defaults.getString("messages.arena.spleef-true"); 
    if (this.useColours)
      return this.prefix.getMainColour() + entry.replace("%name%", this.prefix.getHighlightColour() + arenaName + this.prefix.getMainColour()); 
    return entry.replace("%name%", arenaName);
  }
  
  public String getArenaSpleefFalseMessage(String arenaName) {
    String entry = getTextWithColour("messages.arenas.spleef-false");
    if (entry == null)
      entry = this.defaults.getString("messages.arena.spleef-false"); 
    if (this.useColours)
      return this.prefix.getMainColour() + entry.replace("%name%", this.prefix.getHighlightColour() + arenaName + this.prefix.getMainColour()); 
    return entry.replace("%name%", arenaName);
  }
  
  public String getArenaBuildTrueMessage(String arenaName) {
    String entry = getTextWithColour("messages.arenas.build-true");
    if (entry == null)
      entry = this.defaults.getString("messages.arena.build-true"); 
    if (this.useColours)
      return this.prefix.getMainColour() + entry.replace("%name%", this.prefix.getHighlightColour() + arenaName + this.prefix.getMainColour()); 
    return entry.replace("%name%", arenaName);
  }
  
  public String getArenaBuildFalseMessage(String arenaName) {
    String entry = getTextWithColour("messages.arenas.build-false");
    if (entry == null)
      entry = this.defaults.getString("messages.arena.build-false"); 
    if (this.useColours)
      return this.prefix.getMainColour() + entry.replace("%name%", this.prefix.getHighlightColour() + arenaName + this.prefix.getMainColour()); 
    return entry.replace("%name%", arenaName);
  }
  
  public String getArenaHcfTrueMessage(String arenaName) {
    String entry = getTextWithColour("messages.arenas.hcf-true");
    if (entry == null)
      entry = this.defaults.getString("messages.arena.hcf-true"); 
    if (this.useColours)
      return this.prefix.getMainColour() + entry.replace("%name%", this.prefix.getHighlightColour() + arenaName + this.prefix.getMainColour()); 
    return entry.replace("%name%", arenaName);
  }
  
  public String getArenaHcfFalseMessage(String arenaName) {
    String entry = getTextWithColour("messages.arenas.hcf-false");
    if (entry == null)
      entry = this.defaults.getString("messages.arena.hcf-false"); 
    if (this.useColours)
      return this.prefix.getMainColour() + entry.replace("%name%", this.prefix.getHighlightColour() + arenaName + this.prefix.getMainColour()); 
    return entry.replace("%name%", arenaName);
  }
  
  public String getArenaSumoFalseMessage(String arenaName) {
    String entry = getTextWithColour("messages.arenas.sumo-false");
    if (entry == null)
      entry = this.defaults.getString("messages.arena-sumo-false"); 
    if (this.useColours)
      return this.prefix.getMainColour() + entry.replace("%name%", this.prefix.getHighlightColour() + arenaName + this.prefix.getMainColour()); 
    return entry.replace("%name%", arenaName);
  }
  
  public String getAlreadyExistsMessage() {
    String entry = getTextWithColour("messages.arenas.already-exists");
    if (entry == null)
      entry = this.defaults.getString("messages.arenas.already-exists"); 
    if (this.useColours)
      return this.prefix.getMainColour() + entry; 
    return entry;
  }
  
  public String getTextWithColour(String path) {
    String stringWithColour = this.config.getString(path);
    if (stringWithColour != null)
      stringWithColour = ChatColor.translateAlternateColorCodes('&', stringWithColour); 
    return stringWithColour;
  }
}
