package de.kontux.icepractice.listeners.fight;

import de.kontux.icepractice.IcePracticePlugin;
import de.kontux.icepractice.api.match.misc.FightStatistics;
import de.kontux.icepractice.api.playerstates.PlayerState;
import de.kontux.icepractice.playermanagement.PlayerStates;
import de.kontux.icepractice.protocol.EntityHider;
import de.kontux.icepractice.registries.FightRegistry;
import de.kontux.icepractice.util.ConfigUtil;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.projectiles.ProjectileSource;

public class PotionListener implements Listener {
  @EventHandler
  public void onPotionUse(PotionSplashEvent event) {
    if (!ConfigUtil.useWorld(event.getEntity().getWorld()))
      return; 
    ProjectileSource shooter = event.getEntity().getShooter();
    if (shooter instanceof Player) {
      Location loc = event.getPotion().getLocation();
      for (Player player : event.getEntity().getWorld().getPlayers()) {
        if (EntityHider.getInstance().canSee(player, (Entity)shooter)) {
          (EntityHider.getInstance()).nextParticle = (Player)shooter;
          IcePracticePlugin.getNmsHandler().sendWorldEventPacket(player, 2002, loc.getX(), loc.getY(), loc.getZ(), event.getPotion().getItem().getData().getData());
          continue;
        } 
        event.setIntensity((LivingEntity)player, 0.0D);
      } 
      if (PlayerStates.getInstance().getState((Player)shooter) == PlayerState.MATCH) {
        FightStatistics ms = FightRegistry.getInstance().getMatchStatistics((Player)event.getEntity().getShooter());
        ms.addPotion((Player)shooter, event.getIntensity((LivingEntity)shooter));
      } 
    } 
  }
}
