package de.kontux.icepractice.scoreboard.types;

import de.kontux.icepractice.configs.Settings;
import de.kontux.icepractice.scoreboard.AbstractScoreboard;
import java.util.HashMap;
import java.util.List;
import org.bukkit.entity.Player;

public class SumoTeamEventScoreboard extends AbstractScoreboard {
  public SumoTeamEventScoreboard(HashMap<Integer, String> scores) {
    super(scores);
  }
  
  protected String applyPlaceholders(String entry, Object... object) {
    List<Player> team1 = (List<Player>)object[0];
    List<Player> team2 = (List<Player>)object[1];
    int remaining = ((Integer)object[2]).intValue();
    if (Settings.USE_COLOURS_FOR_BOARDS) {
      entry = applyColourToPlaceHolder(entry, "%player1%", ((Player)team1.get(0)).getDisplayName());
      entry = applyColourToPlaceHolder(entry, "%player2%", ((Player)team2.get(0)).getDisplayName());
      entry = applyColourToPlaceHolder(entry, "%players-left%", String.valueOf(remaining));
    } else {
      entry = entry.replace("%player1%", ((Player)team1.get(0)).getDisplayName());
      entry = entry.replace("%player2%", ((Player)team2.get(0)).getDisplayName());
      entry = entry.replace("%players-left%", String.valueOf(remaining));
    } 
    if (CHAR_LIMIT)
      entry = applyCharacterLimit(entry); 
    return entry;
  }
}
