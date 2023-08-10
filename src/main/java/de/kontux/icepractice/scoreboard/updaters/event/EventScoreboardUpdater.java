package de.kontux.icepractice.scoreboard.updaters.event;

import de.kontux.icepractice.IcePracticePlugin;
import de.kontux.icepractice.tournaments.Tournament;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public abstract class EventScoreboardUpdater {
  protected final Tournament tournament;
  
  private int taskId;
  
  public EventScoreboardUpdater(Tournament tournament) {
    this.tournament = tournament;
  }
  
  public void start() {
    this.taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask((Plugin)IcePracticePlugin.getInstance(), getTask(), 0L, 20L);
  }
  
  protected abstract Runnable getTask();
  
  public void stop() {
    Bukkit.getScheduler().cancelTask(this.taskId);
  }
}
