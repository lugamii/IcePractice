package de.kontux.icepractice.listeners.player;

import de.kontux.icepractice.api.user.WorldTime;
import de.kontux.icepractice.configs.Settings;
import de.kontux.icepractice.database.SQLRepository;
import de.kontux.icepractice.locations.SpawnPointHandler;
import de.kontux.icepractice.scoreboard.ScoreboardManager;
import de.kontux.icepractice.userdata.PlayerDataManager;
import de.kontux.icepractice.userdata.PlayerDataRepository;
import de.kontux.icepractice.util.ConfigUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListeners implements Listener {
  private final PlayerDataRepository repo;
  
  public JoinListeners(SQLRepository sqlRepo) {
    this.repo = new PlayerDataRepository(sqlRepo);
  }
  
  @EventHandler
  public void onPlayerJoin(PlayerJoinEvent event) {
    if (!ConfigUtil.useWorld(event.getPlayer().getWorld()))
      return; 
    event.setJoinMessage(null);
    Player player = event.getPlayer();
    PlayerDataManager.addPlayer(player);
    this.repo.addUser(player.getUniqueId(), player.getName());
    WorldTime time = PlayerDataManager.getSettingsData(player.getUniqueId()).getWorldTime();
    player.setPlayerTime(time.getTimeCode(), false);
    if (Settings.TP_ON_JOIN)
      SpawnPointHandler.teleportToSpawn(player); 
    for (Player current : Bukkit.getOnlinePlayers())
      ScoreboardManager.getInstance().setIdleBoard(current); 
  }
}
