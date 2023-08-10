package de.kontux.icepractice.listeners.player;

import de.kontux.icepractice.IcePracticePlugin;
import de.kontux.icepractice.api.match.IcePracticeFight;
import de.kontux.icepractice.protocol.EntityHider;
import de.kontux.icepractice.queue.Queue;
import de.kontux.icepractice.registries.FightRegistry;
import de.kontux.icepractice.registries.PartyRegistry;
import de.kontux.icepractice.scoreboard.ScoreboardManager;
import de.kontux.icepractice.userdata.PlayerDataManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;

public class QuitListener implements Listener {
  private final IcePracticePlugin plugin;
  
  public QuitListener(IcePracticePlugin plugin) {
    this.plugin = plugin;
  }
  
  @EventHandler
  public void onQuit(PlayerQuitEvent event) {
    event.setQuitMessage(null);
    handleQuit(event.getPlayer());
  }
  
  @EventHandler
  public void onKick(PlayerKickEvent event) {
    handleQuit(event.getPlayer());
  }
  
  private void handleQuit(Player player) {
    EntityHider.getInstance().removePlayer(player);
    if (Queue.isInQueue(player)) {
      Queue.removeFromQueue(player, Queue.getRanked(player));
    } else if (FightRegistry.getInstance().getAllPlayers().contains(player)) {
      IcePracticeFight fight = FightRegistry.getInstance().getFightByPlayer(player);
      fight.killPlayer(player, null);
    } 
    if (PartyRegistry.isInParty(player))
      PartyRegistry.getPartyByPlayer(player).leavePlayer(player); 
    PlayerDataManager.removePlayer(player);
  }
  
  private void updateScoreboards() {
    Bukkit.getScheduler().runTaskLater((Plugin)this.plugin, () -> {
          for (Player current : Bukkit.getOnlinePlayers())
            ScoreboardManager.getInstance().setIdleBoard(current); 
        },1L);
  }
}
