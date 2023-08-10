package de.kontux.icepractice.scoreboard;

import de.kontux.icepractice.IcePracticePlugin;
import de.kontux.icepractice.configs.Settings;
import de.kontux.icepractice.nms.NMSVersion;
import de.kontux.icepractice.userdata.PlayerDataManager;
import java.util.HashMap;
import java.util.Iterator;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

public abstract class AbstractScoreboard {
  protected static final boolean CHAR_LIMIT = (IcePracticePlugin.nmsVersion == NMSVersion.v1_7);
  
  private static final String TITLE = Settings.USE_COLOURS_FOR_BOARDS ? (Settings.PRIMARY + "§l" + Settings.SCOREBOARD_TITLE) : ("§l" + Settings.SCOREBOARD_TITLE);
  
  private final HashMap<Integer, String> scores;
  
  public AbstractScoreboard(HashMap<Integer, String> scores) {
    this.scores = scores;
  }
  
  public final void setBoard(Player player, Object... param) {
    Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
    Objective objective = scoreboard.registerNewObjective("dummy", "dummy");
    objective.setDisplayName(TITLE);
    if (showBoard(player)) {
      for (Integer integer : this.scores.keySet()) {
        int score = integer.intValue();
        String entry = this.scores.getOrDefault(Integer.valueOf(score), ChatColor.RED + "Wrongly set up");
        entry = applyPlaceholders(entry, param);
        objective.getScore(entry).setScore(score);
      } 
      objective.setDisplaySlot(DisplaySlot.SIDEBAR);
      player.setScoreboard(scoreboard);
    } 
  }
  
  protected abstract String applyPlaceholders(String paramString, Object... paramVarArgs);
  
  protected String applyCharacterLimit(String entry) {
    return entry.substring(0, 16);
  }
  
  protected String applyColourToPlaceHolder(String entry, String placeholder, String replacement) {
    entry = entry.replace(placeholder, Settings.SECONDARY + replacement + Settings.PRIMARY);
    return entry;
  }
  
  private boolean showBoard(Player player) {
    return PlayerDataManager.getSettingsData(player.getUniqueId()).isShowBoard();
  }
}
