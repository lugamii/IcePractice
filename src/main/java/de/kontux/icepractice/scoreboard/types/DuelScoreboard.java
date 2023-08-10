package de.kontux.icepractice.scoreboard.types;

import de.kontux.icepractice.IcePracticePlugin;
import de.kontux.icepractice.configs.Settings;
import de.kontux.icepractice.scoreboard.AbstractScoreboard;
import java.util.HashMap;
import org.bukkit.entity.Player;

public class DuelScoreboard extends AbstractScoreboard {
  public DuelScoreboard(HashMap<Integer, String> scores) {
    super(scores);
  }
  
  protected String applyPlaceholders(String entry, Object... object) {
    Player player = (Player)object[0];
    Player opponent = (Player)object[1];
    String duration = (String)object[2];
    if (Settings.USE_COLOURS_FOR_BOARDS) {
      entry = applyColourToPlaceHolder(entry, "%opponent%", opponent.getDisplayName());
      entry = applyColourToPlaceHolder(entry, "%own-ping%", String.valueOf(IcePracticePlugin.getNmsHandler().getPing(player)));
      entry = applyColourToPlaceHolder(entry, "%opponent-ping%", String.valueOf(IcePracticePlugin.getNmsHandler().getPing(opponent)));
      entry = applyColourToPlaceHolder(entry, "%duration%", duration);
    } else {
      entry = entry.replace("%opponent%", opponent.getDisplayName());
      entry = entry.replace("%own-ping%", String.valueOf(IcePracticePlugin.getNmsHandler().getPing(player)));
      entry = entry.replace("%opponent-ping%", String.valueOf(IcePracticePlugin.getNmsHandler().getPing(opponent)));
      entry = entry.replace("%duration%", duration);
    } 
    if (CHAR_LIMIT)
      entry = applyCharacterLimit(entry); 
    return entry;
  }
}
