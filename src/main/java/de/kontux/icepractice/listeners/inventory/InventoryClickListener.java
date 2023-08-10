package de.kontux.icepractice.listeners.inventory;

import de.kontux.icepractice.api.gui.InventoryGui;
import de.kontux.icepractice.api.playerstates.PlayerState;
import de.kontux.icepractice.items.join.JoinItemManager;
import de.kontux.icepractice.playermanagement.PlayerStates;
import de.kontux.icepractice.util.ConfigUtil;
import java.util.HashMap;
import java.util.UUID;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class InventoryClickListener implements Listener {
  public static final HashMap<UUID, InventoryGui> OPEN_INVENTORIES = new HashMap<>();
  
  @EventHandler
  public void onInventoryClick(InventoryClickEvent event) {
    if (!ConfigUtil.useWorld(event.getWhoClicked().getWorld()))
      return; 
    if (event.getCurrentItem() == null || event.getCurrentItem().getType() == Material.AIR || !event.getCurrentItem().hasItemMeta())
      return; 
    UUID uuid = event.getWhoClicked().getUniqueId();
    ItemStack item = event.getCurrentItem();
    if (OPEN_INVENTORIES.containsKey(uuid)) {
      event.setCancelled(true);
      ((InventoryGui)OPEN_INVENTORIES.get(uuid)).runAction(item);
      return;
    } 
    Player player = (Player)event.getWhoClicked();
    PlayerState state = PlayerStates.getInstance().getState(player);
    if (state == PlayerState.IDLE || state == PlayerState.EVENT || state == PlayerState.SPECTATING) {
      if (player.getGameMode() != GameMode.CREATIVE) {
        event.setCancelled(true);
        return;
      } 
      if (JoinItemManager.getInstance().getJoinItem(item, event.getSlot()) != null)
        event.setCancelled(true); 
    } 
  }
}
