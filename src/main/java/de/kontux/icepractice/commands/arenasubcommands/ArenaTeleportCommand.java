package de.kontux.icepractice.commands.arenasubcommands;

import de.kontux.icepractice.api.arena.IcePracticeArena;
import de.kontux.icepractice.arenas.ArenaManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class ArenaTeleportCommand implements ArenaCommand {
  private final Player player;
  
  private final String arenaName;
  
  public ArenaTeleportCommand(Player player, String arenaName) {
    this.player = player;
    this.arenaName = arenaName;
  }
  
  public void execute() {
    IcePracticeArena arena = ArenaManager.getInstance().getArena(this.arenaName);
    if (arena != null) {
      this.player.teleport(arena.getCenter());
    } else {
      this.player.sendMessage(ChatColor.RED + "This arena does not exist.");
    } 
  }
}
