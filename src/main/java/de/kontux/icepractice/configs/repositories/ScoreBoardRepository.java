package de.kontux.icepractice.configs.repositories;

import de.kontux.icepractice.configs.Settings;
import de.kontux.icepractice.scoreboard.ScoreboardType;
import de.kontux.icepractice.util.ConfigUtil;
import java.util.HashMap;
import java.util.Set;
import org.bukkit.configuration.ConfigurationSection;

public class ScoreBoardRepository {
  public HashMap<Integer, String> getScores(ScoreboardType type) {
    String typeName = type.toString().toLowerCase();
    ConfigurationSection section = type.getConfigurationSection();
    Set<String> keys = section.getKeys(false);
    HashMap<Integer, String> board = new HashMap<>();
    byte lineAmount = 0;
    byte spaceAmount = 0;
    for (String key : keys) {
      int score = Integer.parseInt(key);
      String entry = ConfigUtil.getColouredString(section, key);
      if (entry.contains("%space%")) {
        if (spaceAmount == 0) {
          entry = entry.replace("%space%", " ");
        } else if (spaceAmount == 1) {
          entry = entry.replace("%space%", " §6");
        } else if (spaceAmount == 2) {
          entry = entry.replace("%space%", " §7");
        } else if (spaceAmount == 3) {
          entry = entry.replace("%space%", " §8");
        } 
        spaceAmount = (byte)(spaceAmount + 1);
      } else if (entry.contains("%line%")) {
        if (lineAmount == 0) {
          entry = entry.replace("%line%", "§7§m---------------");
        } else if (lineAmount == 1) {
          entry = entry.replace("%line%", "§7§m---------------§6");
        } else if (lineAmount == 2) {
          entry = entry.replace("%line%", "§7§m---------------§7");
        } else if (lineAmount == 3) {
          entry = entry.replace("%line%", "§7§m---------------§8");
        } 
        lineAmount = (byte)(lineAmount + 1);
      } 
      if (Settings.USE_COLOURS_FOR_BOARDS)
        entry = Settings.PRIMARY + entry; 
      board.put(Integer.valueOf(score), entry);
    } 
    return board;
  }
}
