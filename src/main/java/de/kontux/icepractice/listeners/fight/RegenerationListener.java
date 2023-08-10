package de.kontux.icepractice.listeners.fight;

import de.kontux.icepractice.api.match.IcePracticeFight;
import de.kontux.icepractice.api.playerstates.PlayerState;
import de.kontux.icepractice.playermanagement.PlayerStates;
import de.kontux.icepractice.registries.FightRegistry;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityRegainHealthEvent;

public class RegenerationListener implements Listener {
  @EventHandler
  public void onRegen(EntityRegainHealthEvent event) {
    if (event.getEntity() instanceof Player) {
      Player player = (Player)event.getEntity();
      if (PlayerStates.getInstance().getState(player) != PlayerState.MATCH)
        return; 
      IcePracticeFight fight = FightRegistry.getInstance().getFightByPlayer(player);
      if (fight != null && !fight.getKit().allowRegen())
        event.setCancelled(true); 
    } 
  }
}
