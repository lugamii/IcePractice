package de.kontux.icepractice.commands.sumoeventarenasubcommands;

import de.kontux.icepractice.arenas.SumoEventArena;
import de.kontux.icepractice.arenas.SumoEventArenaManager;
import de.kontux.icepractice.configs.Settings;
import java.util.List;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class SumoEventArenaListCommand implements SumoEventArenaSubcommand {
  private final Player player;
  
  public SumoEventArenaListCommand(Player player) {
    this.player = player;
  }
  
  public void execute() {
    List<SumoEventArena> arenas = SumoEventArenaManager.getInstance().getSumoEventArenas();
    this.player.sendMessage(Settings.SECONDARY + "All Sumo Event Arenas:");
    for (SumoEventArena arena : arenas) {
      TextComponent msg = new TextComponent(Settings.PRIMARY + " - " + arena.getName());
      msg.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/sarena tp " + arena.getName()));
      msg.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, (new ComponentBuilder(ChatColor.GREEN + "Click to teleport")).create()));
      this.player.spigot().sendMessage((BaseComponent)msg);
    } 
  }
}
