package de.kontux.icepractice.listeners.player;

import de.kontux.icepractice.IcePracticePlugin;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.Plugin;

public class PlayerFreeze implements Listener {
  private final List<Player> players;
  
  public PlayerFreeze(List<Player> players) {
    this.players = players;
  }
  
  public void register() {
    Bukkit.getPluginManager().registerEvents(this, (Plugin)IcePracticePlugin.getInstance());
  }
  
  public void unregister() {
    PlayerMoveEvent.getHandlerList().unregister(this);
  }
  
  @EventHandler
  public void onMove(PlayerMoveEvent event) {
    if (this.players.contains(event.getPlayer()))
      event.getPlayer().teleport(event.getFrom()); 
  }
}
