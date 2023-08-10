package de.kontux.icepractice.listeners.fight;

import de.kontux.icepractice.api.playerstates.PlayerState;
import de.kontux.icepractice.playermanagement.PlayerStates;
import de.kontux.icepractice.protocol.EntityHider;
import de.kontux.icepractice.registries.FightRegistry;
import de.kontux.icepractice.util.ConfigUtil;
import de.kontux.icepractice.util.ItemCooldown;
import java.util.HashMap;
import java.util.UUID;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.ItemStack;

public class ProjectileListener implements Listener {
  public static final HashMap<UUID, ItemCooldown> ON_COOLDOWN = new HashMap<>();
  
  @EventHandler
  public void onPearlLaunch(ProjectileLaunchEvent event) {
    if (!ConfigUtil.useWorld(event.getEntity().getWorld()))
      return; 
    if (event.getEntity().getShooter() instanceof Player) {
      Player player = (Player)event.getEntity().getShooter();
      PlayerState state = PlayerStates.getInstance().getState(player);
      handleEntityHider(player, event.getEntity());
      if (state != PlayerState.MATCH && state != PlayerState.STARTING_MATCH)
        return; 
      if (event.getEntityType() == EntityType.ENDER_PEARL) {
        if (ON_COOLDOWN.containsKey(player.getUniqueId())) {
          event.setCancelled(true);
          ItemStack item = player.getInventory().getItemInHand();
          item.setAmount(item.getAmount() + 1);
          player.getInventory().setItem(player.getInventory().getHeldItemSlot(), item);
          player.sendMessage(ChatColor.RED + "You can't use enderpearls for another " + ((ItemCooldown)ON_COOLDOWN.get(player.getUniqueId())).getSecondsLeft() + " seconds.");
          return;
        } 
        int cooldown = FightRegistry.getInstance().getFightByPlayer(player).getKit().getPearlCooldown();
        if (cooldown > 0)
          (new ItemCooldown()).startCooldown(player, cooldown); 
      } 
    } 
  }
  
  private void handleEntityHider(Player shooter, Projectile projectile) {
    for (Player observer : projectile.getWorld().getPlayers()) {
      if (EntityHider.getInstance().canSee(observer, (Entity)shooter)) {
        EntityHider.getInstance().showEntity(observer, (Entity)projectile);
        continue;
      } 
      EntityHider.getInstance().hideEntity(observer, (Entity)projectile);
    } 
  }
}
