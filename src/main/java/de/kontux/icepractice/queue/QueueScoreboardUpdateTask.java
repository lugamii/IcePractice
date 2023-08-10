package de.kontux.icepractice.queue;

import de.kontux.icepractice.IcePracticePlugin;
import de.kontux.icepractice.scoreboard.ScoreboardManager;
import de.kontux.icepractice.util.LangUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class QueueScoreboardUpdateTask {
  private final Player player;
  
  private final String kit;
  
  private int seconds;
  
  public QueueScoreboardUpdateTask(Player player, String kit) {
    this.player = player;
    this.kit = kit;
  }
  
  public int start() {
    this.seconds = 0;
    return Bukkit.getScheduler().scheduleSyncRepeatingTask((Plugin)IcePracticePlugin.getInstance(), () -> {
          String time = LangUtil.formatSeconds(this.seconds);
          ScoreboardManager.getInstance().setQueueBoard(this.player, this.kit, time);
          this.seconds++;
        },0L, 20L);
  }
}
