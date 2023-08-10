package de.kontux.icepractice.scoreboard.updaters.fight;

import de.kontux.icepractice.IcePracticePlugin;
import de.kontux.icepractice.match.Fight;
import de.kontux.icepractice.match.types.TeamFight;
import de.kontux.icepractice.scoreboard.ScoreboardManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class TeamFightScoreBoardUpdaterFight extends FightScoreboardUpdater {
  private final TeamFight fight;
  
  public TeamFightScoreBoardUpdaterFight(TeamFight fight) {
    super((Fight)fight);
    this.fight = fight;
  }
  
  public void startUpdater() {
    for (Player current : this.fight.getPlayers())
      ScoreboardManager.getInstance().setTeamFightBoard(current, this.fight.getTeam1().size(), this.fight.getTeam2().size(), "0:00"); 
    for (Player current : this.fight.getSpectators())
      ScoreboardManager.getInstance().setTeamFightBoard(current, this.fight.getTeam1().size(), this.fight.getTeam2().size(), "0:00"); 
    this.taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask((Plugin)IcePracticePlugin.getInstance(), new Runnable() {
          public void run() {
            int minutes = TeamFightScoreBoardUpdaterFight.this.duration / 60;
            int seconds = TeamFightScoreBoardUpdaterFight.this.duration % 60;
            String durationText = String.format("%d:%02d", new Object[] { Integer.valueOf(minutes), Integer.valueOf(seconds) });
            for (Player current : TeamFightScoreBoardUpdaterFight.this.fight.getPlayers())
              ScoreboardManager.getInstance().setTeamFightBoard(current, TeamFightScoreBoardUpdaterFight.this.fight.getTeam1().size(), TeamFightScoreBoardUpdaterFight.this.fight.getTeam2().size(), durationText); 
            for (Player current : TeamFightScoreBoardUpdaterFight.this.fight.getSpectators())
              ScoreboardManager.getInstance().setTeamFightBoard(current, TeamFightScoreBoardUpdaterFight.this.fight.getTeam1().size(), TeamFightScoreBoardUpdaterFight.this.fight.getTeam2().size(), durationText); 
            TeamFightScoreBoardUpdaterFight.this.duration++;
          }
        },0L, 20L);
  }
}
