package de.kontux.icepractice.scoreboard.updaters.fight;

import de.kontux.icepractice.match.Fight;
import org.bukkit.Bukkit;

public abstract class FightScoreboardUpdater {
  public int duration;
  
  protected Fight fight;
  
  protected int taskId;
  
  public FightScoreboardUpdater(Fight fight) {
    this.fight = fight;
  }
  
  public abstract void startUpdater();
  
  public void stopUpdater() {
    Bukkit.getScheduler().cancelTask(this.taskId);
  }
}
