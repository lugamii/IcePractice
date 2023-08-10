package de.kontux.icepractice.listeners.item;

import de.kontux.icepractice.api.playerstates.PlayerState;
import de.kontux.icepractice.playermanagement.PlayerStates;
import de.kontux.icepractice.protocol.EntityHider;
import de.kontux.icepractice.util.ConfigUtil;
import org.bukkit.GameMode;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

public class ItemDropListener implements Listener {
  @EventHandler
  public void onItemDrop(PlayerDropItemEvent event) {
    if (!ConfigUtil.useWorld(event.getPlayer().getWorld()))
      return; 
    Player player = event.getPlayer();
    PlayerState state = PlayerStates.getInstance().getState(player);
    if (player.getGameMode() == GameMode.CREATIVE) {
      EntityHider.getInstance().showEntity(player, (Entity)event.getItemDrop());
      return;
    } 
    if (state != PlayerState.MATCH && state != PlayerState.STARTING_MATCH) {
      event.setCancelled(true);
    } else {
      for (Player current : event.getItemDrop().getWorld().getPlayers()) {
        if (EntityHider.getInstance().canSee(current, (Entity)player))
          EntityHider.getInstance().showEntity(current, (Entity)event.getItemDrop()); 
      } 
    } 
  }
}
