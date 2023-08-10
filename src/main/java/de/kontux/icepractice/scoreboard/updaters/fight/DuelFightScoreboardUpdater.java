package de.kontux.icepractice.scoreboard.updaters.fight;

import de.kontux.icepractice.IcePracticePlugin;
import de.kontux.icepractice.match.Fight;
import de.kontux.icepractice.scoreboard.ScoreboardManager;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class DuelFightScoreboardUpdater extends FightScoreboardUpdater {
  private final List<Player> players;
  
  private int duration = 0;
  
  public DuelFightScoreboardUpdater(Fight fight) {
    super(fight);
    this.players = fight.getPlayers();
  }
  
  public void startUpdater() {
    for (Player current : this.players)
      ScoreboardManager.getInstance().setDuelBoard(current, this.fight.getNext(current), "0:00"); 
    this.taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask((Plugin)IcePracticePlugin.getInstance(), new Runnable() {
          public void run() {
            int minutes = DuelFightScoreboardUpdater.this.duration / 60;
            int seconds = DuelFightScoreboardUpdater.this.duration % 60;
            String durationText = String.format("%d:%02d", new Object[] { Integer.valueOf(minutes), Integer.valueOf(seconds) });
            for (Player current : DuelFightScoreboardUpdater.this.players)
              ScoreboardManager.getInstance().setDuelBoard(current, DuelFightScoreboardUpdater.this.fight.getNext(current), durationText); 
            DuelFightScoreboardUpdater.this.duration++;
          }
        },0L, 20L);
  }
}
