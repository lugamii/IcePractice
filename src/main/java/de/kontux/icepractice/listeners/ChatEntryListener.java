package de.kontux.icepractice.listeners;

import de.kontux.icepractice.util.ChatEntryUser;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;

public class ChatEntryListener implements Listener {
  private final Player player;
  
  private final ChatEntryUser instance;
  
  public ChatEntryListener(Player player, ChatEntryUser instance) {
    this.player = player;
    this.instance = instance;
  }
  
  @EventHandler
  public void onChatMessage(PlayerChatEvent event) {
    Player p = event.getPlayer();
    if (p.equals(this.player)) {
      this.instance.setText(event.getMessage());
      PlayerChatEvent.getHandlerList().unregister(this);
      event.setCancelled(true);
    } 
  }
}
