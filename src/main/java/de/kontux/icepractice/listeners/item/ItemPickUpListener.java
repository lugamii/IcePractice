package de.kontux.icepractice.listeners.item;

import de.kontux.icepractice.api.playerstates.PlayerState;
import de.kontux.icepractice.playermanagement.PlayerStates;
import de.kontux.icepractice.protocol.EntityHider;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;

public class ItemPickUpListener implements Listener {
  @EventHandler
  public void onPickUp(PlayerPickupItemEvent event) {
    Player player = event.getPlayer();
    if ((PlayerStates.getInstance().getState(player) != PlayerState.MATCH && PlayerStates.getInstance().getState(player) != PlayerState.STARTING_MATCH) || !EntityHider.getInstance().isVisible(player, event.getItem().getEntityId()))
      event.setCancelled(true); 
  }
}
