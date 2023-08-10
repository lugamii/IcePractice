package de.kontux.icepractice.tournaments;

import de.kontux.icepractice.IcePracticePlugin;
import de.kontux.icepractice.configs.Settings;
import de.kontux.icepractice.configs.repositories.messages.EventMessageRepository;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class EventBroadcast {
  private static final int MINUTES = IcePracticePlugin.getInstance().getConfig().isInt("config.events.time-before-expireCooldown") ? IcePracticePlugin.getInstance().getConfig().getInt("config.events.time-before-expireCooldown") : 2;
  
  private final Tournament tournament;
  
  private int taskId;
  
  private int passedSeconds = 0;
  
  public EventBroadcast(Tournament tournament) {
    this.tournament = tournament;
  }
  
  public void startBroadcast() {
    this.taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask((Plugin)IcePracticePlugin.getInstance(), () -> {
          this.tournament.updateStartingBoard();
          if (this.passedSeconds / 60 >= MINUTES) {
            Bukkit.getScheduler().cancelTask(this.taskId);
            this.tournament.startMatchCircuit();
            return;
          } 
          if (this.passedSeconds % 10 == 0) {
            TextComponent button = new TextComponent(Settings.SECONDARY + (new EventMessageRepository()).getBroadcastMessage(this.tournament.getHost(), this.tournament.getName()) + ChatColor.GREEN + " [Click to join]");
            button.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/event join " + this.tournament.getId()));
            button.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, (new ComponentBuilder(ChatColor.GREEN + "Click to join!")).create()));
            Bukkit.spigot().broadcast((BaseComponent)button);
          } 
          this.passedSeconds++;
        },0L, 20L);
  }
  
  public String getFormattedTime() {
    int secondsLeft = MINUTES * 60 - this.passedSeconds;
    int minutes = secondsLeft / 60;
    int seconds = secondsLeft % 60;
    return String.format("%d:%02d", new Object[] { Integer.valueOf(minutes), Integer.valueOf(seconds) });
  }
  
  public void stopBroadcast() {
    Bukkit.getScheduler().cancelTask(this.taskId);
  }
}
