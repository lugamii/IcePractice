package de.kontux.icepractice.scoreboard.types;

import de.kontux.icepractice.configs.Settings;
import de.kontux.icepractice.scoreboard.AbstractScoreboard;
import java.util.HashMap;

public class StartingEventScoreboard extends AbstractScoreboard {
  public StartingEventScoreboard(HashMap<Integer, String> scores) {
    super(scores);
  }
  
  protected String applyPlaceholders(String entry, Object... object) {
    String timeLeft = (String)object[0];
    int participants = ((Integer)object[1]).intValue();
    String kitName = (String)object[2];
    if (Settings.USE_COLOURS_FOR_BOARDS) {
      entry = applyColourToPlaceHolder(entry, "%time-left%", timeLeft);
      entry = applyColourToPlaceHolder(entry, "%players-left%", String.valueOf(participants));
      entry = applyColourToPlaceHolder(entry, "%participants%", String.valueOf(participants));
      entry = applyColourToPlaceHolder(entry, "%kit%", kitName);
    } else {
      entry = entry.replace("%time-left%", timeLeft);
      entry = entry.replace("%players-left%", String.valueOf(participants));
      entry = entry.replace("%participants%", String.valueOf(participants));
      entry = entry.replace("%kit%", kitName);
    } 
    if (CHAR_LIMIT)
      entry = applyCharacterLimit(entry); 
    return entry;
  }
}
