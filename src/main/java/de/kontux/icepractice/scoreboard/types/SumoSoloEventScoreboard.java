package de.kontux.icepractice.scoreboard.types;

import de.kontux.icepractice.configs.Settings;
import de.kontux.icepractice.listeners.interaction.InteractListener;
import de.kontux.icepractice.scoreboard.AbstractScoreboard;
import java.util.HashMap;
import org.bukkit.entity.Player;

public class SumoSoloEventScoreboard extends AbstractScoreboard {
  public SumoSoloEventScoreboard(HashMap<Integer, String> scores) {
    super(scores);
  }
  
  protected String applyPlaceholders(String entry, Object... object) {
    Player player1 = (Player)object[0];
    Player player2 = (Player)object[1];
    int remaining = ((Integer)object[2]).intValue();
    if (Settings.USE_COLOURS_FOR_BOARDS) {
      entry = applyColourToPlaceHolder(entry, "%player1%", player1.getDisplayName());
      entry = applyColourToPlaceHolder(entry, "%player2%", player2.getDisplayName());
      entry = applyColourToPlaceHolder(entry, "%cps1%", String.valueOf(InteractListener.getCps(player1)));
      entry = applyColourToPlaceHolder(entry, "%cps2%", String.valueOf(InteractListener.getCps(player2)));
      entry = applyColourToPlaceHolder(entry, "%players-left%", String.valueOf(remaining));
    } else {
      entry = entry.replace("%player1%", player1.getDisplayName());
      entry = entry.replace("%player2%", player2.getDisplayName());
      entry = entry.replace("%cps1%", String.valueOf(InteractListener.getCps(player1)));
      entry = entry.replace("%cps2%", String.valueOf(InteractListener.getCps(player2)));
      entry = entry.replace("%players-left%", String.valueOf(remaining));
    } 
    if (CHAR_LIMIT)
      entry = applyCharacterLimit(entry); 
    return entry;
  }
}
