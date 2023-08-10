package de.kontux.icepractice.listeners.player;

import de.kontux.icepractice.api.user.WorldTime;
import de.kontux.icepractice.userdata.PlayerDataManager;
import de.kontux.icepractice.util.ConfigUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

public class WorldChangeListener implements Listener {
  @EventHandler
  public void onWorldChange(PlayerChangedWorldEvent event) {
    if (!ConfigUtil.useWorld(event.getPlayer().getWorld()))
      return; 
    WorldTime time = PlayerDataManager.getSettingsData(event.getPlayer().getUniqueId()).getWorldTime();
    event.getPlayer().setPlayerTime(time.getTimeCode(), false);
  }
}
