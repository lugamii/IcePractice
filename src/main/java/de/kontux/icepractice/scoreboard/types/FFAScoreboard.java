package de.kontux.icepractice.scoreboard.types;

import de.kontux.icepractice.IcePracticePlugin;
import de.kontux.icepractice.configs.Settings;
import de.kontux.icepractice.scoreboard.AbstractScoreboard;
import java.util.HashMap;
import org.bukkit.entity.Player;

public class FFAScoreboard extends AbstractScoreboard {
  public FFAScoreboard(HashMap<Integer, String> scores) {
    super(scores);
  }
  
  protected String applyPlaceholders(String entry, Object... object) {
    Player player = (Player)object[0];
    int alive = ((Integer)object[1]).intValue();
    int kills = ((Integer)object[2]).intValue();
    String duration = (String)object[3];
    if (Settings.USE_COLOURS_FOR_BOARDS) {
      entry = applyColourToPlaceHolder(entry, "%own-ping%", String.valueOf(IcePracticePlugin.getNmsHandler().getPing(player)));
      entry = applyColourToPlaceHolder(entry, "%alive%", String.valueOf(alive));
      entry = applyColourToPlaceHolder(entry, "%kills%", String.valueOf(kills));
      entry = applyColourToPlaceHolder(entry, "%duration%", duration);
    } else {
      entry = entry.replace("%own-ping%", String.valueOf(IcePracticePlugin.getNmsHandler().getPing(player)));
      entry = entry.replace("%alive%", String.valueOf(alive));
      entry = entry.replace("%kills%", String.valueOf(kills));
      entry = entry.replace("%duration%", duration);
    } 
    if (CHAR_LIMIT)
      entry = applyCharacterLimit(entry); 
    return entry;
  }
}
