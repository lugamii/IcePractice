package de.kontux.icepractice.listeners.item;

import de.kontux.icepractice.api.playerstates.PlayerState;
import de.kontux.icepractice.playermanagement.PlayerStates;
import de.kontux.icepractice.queue.Queue;
import de.kontux.icepractice.registries.FightRegistry;
import de.kontux.icepractice.registries.PartyRegistry;
import de.kontux.icepractice.util.ConfigUtil;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

public class BlockPlaceListener implements Listener {
  @EventHandler
  public void onBlockPlace(BlockPlaceEvent event) {
    if (!ConfigUtil.useWorld(event.getPlayer().getWorld()))
      return; 
    Player player = event.getPlayer();
    if (event.getPlayer().getGameMode() == GameMode.CREATIVE || player.getItemInHand().getType() == null || player.getItemInHand().getType() == Material.AIR)
      return; 
    PlayerState state = PlayerStates.getInstance().getState(player);
    if (state == PlayerState.MATCH || state == PlayerState.STARTING_MATCH) {
      if (!FightRegistry.getInstance().getFightByPlayer(player).getKit().isBuild())
        event.setCancelled(true); 
    } else {
      ItemStack item = event.getItemInHand();
      event.setCancelled(true);
      if (Queue.getItem().isSimilar(item)) {
        Queue.removeFromQueue(player, Queue.getRanked(player));
      } else if (PartyRegistry.isInParty(player) && 
        item.getType() == Material.REDSTONE && item.getItemMeta().getDisplayName().equals("§cLeave Party")) {
        PartyRegistry.getPartyByPlayer(player).leavePlayer(player);
      } 
    } 
  }
}
