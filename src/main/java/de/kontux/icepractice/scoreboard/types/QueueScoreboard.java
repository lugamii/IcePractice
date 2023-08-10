package de.kontux.icepractice.scoreboard.types;

import de.kontux.icepractice.configs.Settings;
import de.kontux.icepractice.scoreboard.AbstractScoreboard;
import java.util.HashMap;

public class QueueScoreboard extends AbstractScoreboard {
  public QueueScoreboard(HashMap<Integer, String> scores) {
    super(scores);
  }
  
  protected String applyPlaceholders(String entry, Object... param) {
    String kit = (String)param[0];
    String time = (String)param[1];
    if (Settings.USE_COLOURS_FOR_BOARDS) {
      entry = applyColourToPlaceHolder(entry, "%kit%", kit);
      entry = applyColourToPlaceHolder(entry, "%time%", time);
    } else {
      entry = entry.replace("%kit%", kit);
      entry = entry.replace("%time%", time);
    } 
    return entry;
  }
}
