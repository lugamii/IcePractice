package de.kontux.icepractice.listeners.player;

import de.kontux.icepractice.api.playerstates.PlayerState;
import de.kontux.icepractice.playermanagement.PlayerStates;
import de.kontux.icepractice.util.ConfigUtil;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class FoodListener implements Listener {
  @EventHandler
  public void onSaturationChange(FoodLevelChangeEvent event) {
    if (!ConfigUtil.useWorld(event.getEntity().getWorld()))
      return; 
    PlayerState state = PlayerStates.getInstance().getState((Player)event.getEntity());
    if (state != PlayerState.MATCH)
      event.setFoodLevel(20); 
  }
  
  @EventHandler
  public void onConsume(PlayerItemConsumeEvent event) {
    if (!ConfigUtil.useWorld(event.getPlayer().getWorld()))
      return; 
    ItemStack item = event.getItem();
    if (!item.hasItemMeta())
      return; 
    String name = item.getItemMeta().getDisplayName();
    if (name.equals(ChatColor.GOLD + "Golden Head")) {
      Player player = event.getPlayer();
      player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 120, 3));
      player.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 2400, 1));
    } 
  }
}
