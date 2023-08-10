package de.kontux.icepractice.scoreboard;

import de.kontux.icepractice.IcePracticePlugin;
import org.bukkit.configuration.ConfigurationSection;

public enum ScoreboardType {
  IDLE, DUEL, PARTY, TEAMFIGHT, FFA, KOTH, STARTINGEVENT, SUMOSOLOEVENT, SUMOTEAMEVENT, QUEUE;
  
  public ConfigurationSection getConfigurationSection() {
    ConfigurationSection section = IcePracticePlugin.getInstance().getConfig().getConfigurationSection("config.scoreboards." + name().toLowerCase());
    if (section == null)
      section = IcePracticePlugin.getInstance().getConfig().createSection("config.scoreboards." + name().toLowerCase()); 
    return section;
  }
}
