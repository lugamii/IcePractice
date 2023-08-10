package de.kontux.icepractice.match.misc;

import de.kontux.icepractice.IcePracticePlugin;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class ComboHandler {
  private final List<Player> players;
  
  public ComboHandler(List<Player> players) {
    this.players = players;
  }
  
  public void setHitDelay() {
    Bukkit.getScheduler().scheduleSyncDelayedTask((Plugin)IcePracticePlugin.getInstance(), () -> {
          for (Player current : this.players)
            current.setMaximumNoDamageTicks(0); 
        });
  }
}
