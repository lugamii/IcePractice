package de.kontux.icepractice.listeners.fight;

import de.kontux.icepractice.IcePracticePlugin;
import de.kontux.icepractice.api.IcePracticeAPI;
import de.kontux.icepractice.api.event.fight.FightEndEvent;
import de.kontux.icepractice.match.Fight;
import java.util.List;

import de.kontux.icepractice.util.TaskUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.Plugin;

public class SumoListeners implements Listener {
  private final List<Player> players;
  
  private final Fight fight;
  
  public SumoListeners(List<Player> players, Fight fight) {
    this.players = players;
    this.fight = fight;
  }
  
  public void register() {
    Bukkit.getPluginManager().registerEvents(this, (Plugin)IcePracticePlugin.getInstance());
  }
  
  public void unregister() {
    this.players.clear();
    EntityDamageByEntityEvent.getHandlerList().unregister(this);
    PlayerMoveEvent.getHandlerList().unregister(this);
  }
  
  @EventHandler
  public void onDamage(EntityDamageByEntityEvent event) {
    if (!(event.getEntity() instanceof Player))
      return; 
    Player damaged = (Player)event.getEntity();
    if (this.players.contains(damaged))
      event.setDamage(0.0D); 
  }
  
  @EventHandler
  public void onWaterTouch(PlayerMoveEvent event) {
    if (!this.players.contains(event.getPlayer()))
      return; 
    if (event.getTo().getBlock().getType() == Material.WATER || event.getTo().getBlock().getType() == Material.STATIONARY_WATER) {
      Player loser = event.getPlayer();
      Player killer = null;
      EntityDamageEvent lastDamage = loser.getLastDamageCause();
      if (lastDamage instanceof EntityDamageByEntityEvent) {
        Entity killerEntity = ((EntityDamageByEntityEvent)lastDamage).getDamager();
        if (killerEntity instanceof Player)
          killer = (Player)killerEntity; 
      } 
      this.fight.killPlayer(loser, killer);
      this.players.remove(loser);
    } 
  }

  @EventHandler
  public void onEnd(FightEndEvent e) {
    List<Player> winners = e.getWinners();
    List<Player> losers = e.getLosers();
    Bukkit.getLogger().info("a match ended");
    for (Player plr : winners) {
      if (!plr.hasMetadata("NPC")) {
        TaskUtil.runLater(() -> IcePracticeAPI.getSpawnpointManager().teleportToSpawn(plr), 60L);
      }
    }
    for (Player loser : losers) {
      if (!loser.hasMetadata("NPC")) {
        TaskUtil.runLater(() -> IcePracticeAPI.getSpawnpointManager().teleportToSpawn(loser), 60L);
      }
    }
  }
}
