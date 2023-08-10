package de.kontux.icepractice.commands.arenasubcommands;

import de.kontux.icepractice.api.arena.IcePracticeArena;
import de.kontux.icepractice.arenas.ArenaManager;
import de.kontux.icepractice.guis.settings.ArenaSettingsInventory;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class ArenaSettingsCommand implements ArenaCommand {
  private final String arenaName;
  
  private final Player player;
  
  public ArenaSettingsCommand(String arenaName, Player player) {
    this.arenaName = arenaName;
    this.player = player;
  }
  
  public void execute() {
    IcePracticeArena arena = ArenaManager.getInstance().getArena(this.arenaName);
    if (arena == null) {
      this.player.sendMessage(ChatColor.RED + "This arena doesn't exist!");
      return;
    } 
    (new ArenaSettingsInventory(this.player, arena)).openMenu();
  }
}
