package de.kontux.icepractice.listeners.inventory;

import de.kontux.icepractice.api.playerstates.PlayerState;
import de.kontux.icepractice.playermanagement.PlayerStates;
import de.kontux.icepractice.util.ConfigUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryDragEvent;

public class InventoryDragListener implements Listener {
  @EventHandler
  public void onDrag(InventoryDragEvent event) {
    if (!ConfigUtil.useWorld(event.getWhoClicked().getWorld()))
      return; 
    Player player = (Player)event.getWhoClicked();
    if (PlayerStates.getInstance().getState(player) != PlayerState.MATCH)
      event.setCancelled(true); 
  }
}
