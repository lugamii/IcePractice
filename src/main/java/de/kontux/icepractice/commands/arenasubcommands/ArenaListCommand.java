package de.kontux.icepractice.commands.arenasubcommands;

import de.kontux.icepractice.api.arena.IcePracticeArena;
import de.kontux.icepractice.arenas.ArenaManager;
import de.kontux.icepractice.configs.Settings;
import java.util.List;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;

public class ArenaListCommand implements ArenaCommand {
  private final Player player;
  
  public ArenaListCommand(Player player) {
    this.player = player;
  }
  
  public void execute() {
    List<IcePracticeArena> arenas = ArenaManager.getInstance().getArenas();
    this.player.sendMessage(Settings.SECONDARY + "All arenas:");
    for (IcePracticeArena arena : arenas) {
      TextComponent msg = new TextComponent(Settings.PRIMARY + arena.getName() + ChatColor.GREEN + " [Teleport]");
      msg.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, (new ComponentBuilder(ChatColor.GREEN + "Click to teleport")).create()));
      msg.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/arena tp " + arena.getName()));
      this.player.spigot().sendMessage((BaseComponent)msg);
    } 
  }
}
