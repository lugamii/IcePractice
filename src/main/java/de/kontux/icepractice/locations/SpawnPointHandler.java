package de.kontux.icepractice.locations;

import de.kontux.icepractice.api.playerstates.PlayerState;
import de.kontux.icepractice.items.join.JoinItemManager;
import de.kontux.icepractice.playermanagement.PlayerStates;
import de.kontux.icepractice.protocol.EntityHider;
import de.kontux.icepractice.scoreboard.ScoreboardManager;
import de.kontux.icepractice.userdata.PlayerDataManager;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

public class SpawnPointHandler {
  private static Location spawn = (new SpawnPointRepository()).loadSpawn();
  
  public static void teleportToSpawn(Player player) {
    if (player == null || !player.isOnline())
      return; 
    player.setFlying(false);
    player.setAllowFlight(false);
    player.setNoDamageTicks(20);
    player.setMaximumNoDamageTicks(20);
    player.setHealth(player.getMaxHealth());
    player.setSaturation(20.0F);
    player.setFoodLevel(20);
    player.setFireTicks(0);
    player.teleport(spawn);
    PlayerStates.getInstance().setState(player, PlayerState.IDLE);
    for (int i = 0; i < 40; i++)
      player.getInventory().clear(i); 
    for (PotionEffect effect : player.getActivePotionEffects())
      player.removePotionEffect(effect.getType()); 
    JoinItemManager.getInstance().giveItems(player);
    boolean showPlayers = PlayerDataManager.getSettingsData(player.getUniqueId()).isShowPlayers();
    for (Player current : player.getWorld().getPlayers()) {
      boolean currentShowPlayers = PlayerDataManager.getSettingsData(current.getUniqueId()).isShowPlayers();
      if (currentShowPlayers) {
        EntityHider.getInstance().showEntity(current, (Entity)player);
      } else {
        EntityHider.getInstance().hideEntity(current, (Entity)player);
      } 
      if (showPlayers) {
        EntityHider.getInstance().showEntity(player, (Entity)current);
        continue;
      } 
      EntityHider.getInstance().hideEntity(player, (Entity)current);
    } 
    ScoreboardManager.getInstance().setIdleBoard(player);
  }
  
  public static Location getSpawn() {
    return spawn;
  }
  
  public static void setSpawn(Location location) {
    SpawnPointRepository repo = new SpawnPointRepository();
    repo.setSpawn(location);
    spawn = repo.loadSpawn();
  }
}
