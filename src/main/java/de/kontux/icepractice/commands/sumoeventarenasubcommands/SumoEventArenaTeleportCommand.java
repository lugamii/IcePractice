package de.kontux.icepractice.commands.sumoeventarenasubcommands;

import de.kontux.icepractice.arenas.SumoEventArena;
import de.kontux.icepractice.arenas.SumoEventArenaManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class SumoEventArenaTeleportCommand implements SumoEventArenaSubcommand {
  private final String arenaName;
  
  private final Player player;
  
  public SumoEventArenaTeleportCommand(String arenaName, Player player) {
    this.arenaName = arenaName;
    this.player = player;
  }
  
  public void execute() {
    SumoEventArena arena = SumoEventArenaManager.getInstance().getArena(this.arenaName);
    if (arena != null) {
      this.player.teleport(arena.getCenter());
    } else {
      this.player.sendMessage(ChatColor.RED + "This arena does not exist.");
    } 
  }
}
