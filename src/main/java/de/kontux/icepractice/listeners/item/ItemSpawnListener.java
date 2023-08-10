package de.kontux.icepractice.listeners.item;

import de.kontux.icepractice.IcePracticePlugin;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.plugin.Plugin;

public class ItemSpawnListener implements Listener {
  private final IcePracticePlugin plugin;
  
  public ItemSpawnListener(IcePracticePlugin plugin) {
    this.plugin = plugin;
  }
  
  @EventHandler
  public void onItemSpawn(ItemSpawnEvent event) {
    Bukkit.getScheduler().runTaskLater((Plugin)this.plugin, () -> event.getEntity().remove(), 200L);
  }
}
