package de.kontux.icepractice.listeners.fight;

import de.kontux.icepractice.api.match.IcePracticeFight;
import de.kontux.icepractice.api.match.misc.FightStatistics;
import de.kontux.icepractice.match.types.TeamFight;
import de.kontux.icepractice.playermanagement.PlayerStates;
import de.kontux.icepractice.protocol.EntityHider;
import de.kontux.icepractice.registries.FightRegistry;
import de.kontux.icepractice.util.ConfigUtil;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class DamageListener implements Listener {
  @EventHandler
  public void onDamage(EntityDamageEvent event) {
    if (!ConfigUtil.useWorld(event.getEntity().getWorld()))
      return; 
    if (event.getEntity() instanceof Player) {
      Player player = (Player)event.getEntity();
      Player attacker = getDamager(event);
      IcePracticeFight fight = FightRegistry.getInstance().getFightByPlayer(player);
      if (shouldCancel(player, attacker, fight)) {
        event.setCancelled(true);
      } else if (attacker != null) {
        handleMatchStatistics(attacker);
      }
      if (fight.getKit().isCombo()) {
        player.setNoDamageTicks(1);
      }
      if (player.getHealth() - event.getFinalDamage() <= 0.0D) {
        if (fight == null)
          return; 
        fight.killPlayer(player, attacker);
        event.setDamage(0.0D);
        player.setHealth(20.0D);
      } 
    } 
  }
  
  private boolean shouldCancel(Player victim, Player attacker, IcePracticeFight fight) {
    if (attacker == null)
      return isInvincible(victim); 
    return (isInvincible(victim) || isInvincible(attacker) || handleEntityHider(victim, (Entity)attacker) || isInSameTeam(victim, attacker, fight));
  }
  
  private Player getDamager(EntityDamageEvent e) {
    if (e instanceof EntityDamageByEntityEvent) {
      EntityDamageByEntityEvent event = (EntityDamageByEntityEvent)e;
      if (event.getDamager() instanceof Player)
        return (Player)event.getDamager(); 
      if (event.getDamager() instanceof Projectile) {
        Projectile projectile = (Projectile)event.getDamager();
        if (projectile.getShooter() instanceof Player)
          return (Player)projectile.getShooter(); 
      } 
    } 
    return null;
  }
  
  private boolean isInSameTeam(Player player1, Player player2, IcePracticeFight fight) {
    if (fight instanceof TeamFight) {
      TeamFight teamFight = (TeamFight)fight;
      return ((teamFight.getTeam1().contains(player1) && teamFight.getTeam1().contains(player2)) || (teamFight.getTeam2().contains(player1) && teamFight.getTeam2().contains(player2)));
    } 
    return false;
  }
  
  private boolean isInvincible(Player player) {
    return !PlayerStates.getInstance().getState(player).isPvp();
  }
  
  private boolean handleEntityHider(Player player, Entity damager) {
    if (damager instanceof ThrownPotion) {
      ThrownPotion potion = (ThrownPotion)damager;
      return EntityHider.getInstance().canSee(player, (Entity)potion);
    } 
    return false;
  }
  
  private void handleMatchStatistics(Player player) {
    FightStatistics ms = FightRegistry.getInstance().getMatchStatistics(player);
    if (ms != null)
      ms.addHit(player); 
  }
}
