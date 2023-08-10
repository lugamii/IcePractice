package de.kontux.icepractice.scoreboard.updaters.fight;

import de.kontux.icepractice.IcePracticePlugin;
import de.kontux.icepractice.match.Fight;
import de.kontux.icepractice.party.fights.FFAFight;
import de.kontux.icepractice.scoreboard.ScoreboardManager;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class FFAScoreBoardUpdaterFight extends FightScoreboardUpdater {
  private final List<Player> players;
  
  private final FFAFight fight;
  
  private int taskId;
  
  private int duration = 0;
  
  public FFAScoreBoardUpdaterFight(FFAFight fight) {
    super((Fight)fight);
    this.players = fight.getPlayers();
    this.fight = fight;
  }
  
  public void startUpdater() {
    for (Player current : this.players)
      ScoreboardManager.getInstance().setFFABoard(current, this.players.size(), this.fight.getKills(current), "0:00"); 
    this.taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask((Plugin)IcePracticePlugin.getInstance(), new Runnable() {
          public void run() {
            int minutes = FFAScoreBoardUpdaterFight.this.duration / 60;
            int seconds = FFAScoreBoardUpdaterFight.this.duration % 60;
            String durationText = String.format("%d:%02d", new Object[] { Integer.valueOf(minutes), Integer.valueOf(seconds) });
            for (Player current : FFAScoreBoardUpdaterFight.this.players)
              ScoreboardManager.getInstance().setFFABoard(current, FFAScoreBoardUpdaterFight.this.fight.getPlayers().size(), FFAScoreBoardUpdaterFight.this.fight.getKills(current), durationText); 
            FFAScoreBoardUpdaterFight.this.duration++;
          }
        },0L, 20L);
  }
  
  public void stopUpdater() {
    Bukkit.getScheduler().cancelTask(this.taskId);
  }
}
