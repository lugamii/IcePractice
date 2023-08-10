package de.kontux.icepractice.listeners.inventory;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class InventoryCloseListener implements Listener {
  @EventHandler
  public void onClose(InventoryCloseEvent event) {
    Player player = (Player)event.getPlayer();
    InventoryClickListener.OPEN_INVENTORIES.remove(player.getUniqueId());
  }
}
