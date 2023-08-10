package de.kontux.icepractice.listeners.item;

import de.kontux.icepractice.api.playerstates.PlayerState;
import de.kontux.icepractice.playermanagement.PlayerStates;
import de.kontux.icepractice.protocol.EntityHider;
import de.kontux.icepractice.registries.FightRegistry;
import de.kontux.icepractice.util.ConfigUtil;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class BlockBreakListener implements Listener {
  @EventHandler
  public void onBlockBreak(BlockBreakEvent event) {
    if (!ConfigUtil.useWorld(event.getPlayer().getWorld()))
      return; 
    Player player = event.getPlayer();
    if (player.getGameMode() == GameMode.CREATIVE)
      return; 
    if (PlayerStates.getInstance().getState(player) == PlayerState.MATCH) {
      if (!FightRegistry.getInstance().getFightByPlayer(player).getKit().isSpleef() && event.getBlock().getType() != Material.SNOW_BLOCK) {
        event.setCancelled(true);
      } else {
        for (ItemStack item : event.getBlock().getDrops()) {
          Item drop = event.getBlock().getWorld().dropItem(event.getBlock().getLocation(), item);
          for (Player observer : event.getBlock().getWorld().getPlayers()) {
            if (EntityHider.getInstance().canSee(observer, (Entity)player))
              EntityHider.getInstance().showEntity(observer, (Entity)drop); 
          } 
        } 
      } 
    } else {
      event.setCancelled(true);
    } 
  }
}
