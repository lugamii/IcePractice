package de.kontux.icepractice.scoreboard.types;

import de.kontux.icepractice.configs.Settings;
import de.kontux.icepractice.scoreboard.AbstractScoreboard;
import java.util.HashMap;
import org.bukkit.entity.Player;

public class KothScoreboard extends AbstractScoreboard {
  public KothScoreboard(HashMap<Integer, String> scores) {
    super(scores);
  }
  
  protected String applyPlaceholders(String entry, Object... object) {
    int team1Points = ((Integer)object[0]).intValue();
    int team2Points = ((Integer)object[1]).intValue();
    Player captain = (Player)object[2];
    String timeRemaining = (String)object[3];
    if (Settings.USE_COLOURS_FOR_BOARDS) {
      entry = applyColourToPlaceHolder(entry, "%own-points%", String.valueOf(team1Points));
      entry = applyColourToPlaceHolder(entry, "%opponent-points%", String.valueOf(team2Points));
      entry = applyColourToPlaceHolder(entry, "%captain%", captain.getDisplayName());
      entry = applyColourToPlaceHolder(entry, "%time-remaining%", timeRemaining);
    } else {
      entry = entry.replace("%own-points%", String.valueOf(team1Points));
      entry = entry.replace("%opponent-points%", String.valueOf(team2Points));
      entry = entry.replace("%captain%", captain.getDisplayName());
      entry = entry.replace("%time-remaining%", timeRemaining);
    } 
    if (CHAR_LIMIT)
      entry = applyCharacterLimit(entry); 
    return entry;
  }
}
