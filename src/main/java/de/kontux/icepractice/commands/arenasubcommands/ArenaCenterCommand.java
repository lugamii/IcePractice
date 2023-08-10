package de.kontux.icepractice.commands.arenasubcommands;

import de.kontux.icepractice.api.arena.IcePracticeArena;
import de.kontux.icepractice.arenas.ArenaManager;
import de.kontux.icepractice.configs.messages.ArenaMessageRepository;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class ArenaCenterCommand implements ArenaCommand {
  private final String arenaName;
  
  private final Player player;
  
  public ArenaCenterCommand(String arenaName, Player player) {
    this.arenaName = arenaName;
    this.player = player;
  }
  
  public void execute() {
    IcePracticeArena arena = ArenaManager.getInstance().getArena(this.arenaName);
    if (arena != null) {
      arena.setCenter(this.player.getLocation());
      this.player.sendMessage((new ArenaMessageRepository()).getArenaCenterMessage(this.arenaName));
    } else {
      this.player.sendMessage(ChatColor.RED + "This arena does not exist.");
    } 
  }
}
