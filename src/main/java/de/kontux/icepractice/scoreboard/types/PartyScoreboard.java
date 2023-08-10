package de.kontux.icepractice.scoreboard.types;

import de.kontux.icepractice.configs.Settings;
import de.kontux.icepractice.scoreboard.AbstractScoreboard;
import java.util.HashMap;
import org.bukkit.entity.Player;

public class PartyScoreboard extends AbstractScoreboard {
  public PartyScoreboard(HashMap<Integer, String> scores) {
    super(scores);
  }
  
  protected String applyPlaceholders(String entry, Object... object) {
    Player leader = (Player)object[0];
    int members = ((Integer)object[1]).intValue();
    if (Settings.USE_COLOURS_FOR_BOARDS) {
      entry = applyColourToPlaceHolder(entry, "%party-leader%", leader.getDisplayName());
      entry = applyColourToPlaceHolder(entry, "%party-members%", String.valueOf(members));
    } else {
      entry = entry.replace("%party-leader%", leader.getDisplayName());
      entry = entry.replace("%party-members%", String.valueOf(members));
    } 
    if (CHAR_LIMIT)
      entry = applyCharacterLimit(entry); 
    return entry;
  }
}
