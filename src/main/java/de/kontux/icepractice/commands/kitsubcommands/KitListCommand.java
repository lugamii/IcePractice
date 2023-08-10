package de.kontux.icepractice.commands.kitsubcommands;

import de.kontux.icepractice.api.kit.IcePracticeKit;
import de.kontux.icepractice.configs.Settings;
import de.kontux.icepractice.kits.KitManager;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;

public class KitListCommand implements KitCommand {
  private final Player player;
  
  public KitListCommand(Player player) {
    this.player = player;
  }
  
  public void execute() {
    this.player.sendMessage(Settings.SECONDARY + "All available kits:");
    this.player.sendMessage(ChatColor.GRAY + "(Click a kit to preview it)");
    for (IcePracticeKit kit : KitManager.getInstance().getKits()) {
      String name = kit.getName();
      TextComponent msg = new TextComponent(Settings.PRIMARY + name);
      msg.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/kit load " + name));
      msg.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, (new ComponentBuilder(ChatColor.GREEN + "Preview kit")).create()));
      this.player.spigot().sendMessage((BaseComponent)msg);
    } 
  }
}
