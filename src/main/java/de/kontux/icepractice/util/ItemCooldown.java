package de.kontux.icepractice.util;

import de.kontux.icepractice.IcePracticePlugin;
import de.kontux.icepractice.listeners.fight.ProjectileListener;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class ItemCooldown {
  private int taskId;
  
  private int secondsLeft;
  
  private int ticksLeft;
  
  public void startCooldown(Player player, int totalSeconds) {
    ProjectileListener.ON_COOLDOWN.put(player.getUniqueId(), this);
    int totalTicks = totalSeconds * 20;
    this.secondsLeft = totalSeconds;
    this.ticksLeft = totalTicks;
    this.taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask((Plugin)IcePracticePlugin.getInstance(), () -> {
          if (this.secondsLeft > 0) {
            player.setLevel(this.secondsLeft);
            player.setExp(this.ticksLeft / totalTicks);
            this.ticksLeft--;
            if (this.ticksLeft % 20 == 0)
              this.secondsLeft--; 
          } else {
            player.setLevel(0);
            player.setExp(0.0F);
            ProjectileListener.ON_COOLDOWN.remove(player.getUniqueId());
            Bukkit.getScheduler().cancelTask(this.taskId);
            player.sendMessage(ChatColor.YELLOW + "You can now use enderpearls again.");
          } 
        },0L, 1L);
  }
  
  public int getSecondsLeft() {
    return this.secondsLeft;
  }
}
