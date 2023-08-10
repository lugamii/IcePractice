package de.kontux.icepractice.scoreboard.types;

import de.kontux.icepractice.configs.Settings;
import de.kontux.icepractice.registries.FightRegistry;
import de.kontux.icepractice.scoreboard.AbstractScoreboard;
import java.util.HashMap;
import org.bukkit.Bukkit;

public class IdleScoreboard extends AbstractScoreboard {
  public IdleScoreboard(HashMap<Integer, String> scores) {
    super(scores);
  }
  
  protected String applyPlaceholders(String entry, Object... params) {
    if (Settings.USE_COLOURS_FOR_BOARDS) {
      entry = applyColourToPlaceHolder(entry, "%online-players%", String.valueOf(Bukkit.getOnlinePlayers().size()));
      entry = applyColourToPlaceHolder(entry, "%players-inmatch%", String.valueOf(FightRegistry.getInstance().getAllPlayers().size()));
    } else {
      entry = entry.replace("%online-players%", String.valueOf(Bukkit.getOnlinePlayers().size()));
      entry = entry.replace("%players-inmatch%", String.valueOf(FightRegistry.getInstance().getAllPlayers().size()));
    } 
    return entry;
  }
}
