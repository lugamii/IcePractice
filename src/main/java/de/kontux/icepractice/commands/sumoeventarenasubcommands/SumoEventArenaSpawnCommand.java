package de.kontux.icepractice.commands.sumoeventarenasubcommands;

import de.kontux.icepractice.arenas.SumoEventArena;
import de.kontux.icepractice.arenas.SumoEventArenaManager;
import de.kontux.icepractice.configs.messages.ArenaMessageRepository;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class SumoEventArenaSpawnCommand implements SumoEventArenaSubcommand {
  private final Player player;
  
  private final String arenaName;
  
  public SumoEventArenaSpawnCommand(String arenaName, Player player) {
    this.arenaName = arenaName;
    this.player = player;
  }
  
  public void execute() {
    SumoEventArena arena = SumoEventArenaManager.getInstance().getArena(this.arenaName);
    if (arena != null) {
      arena.setCenter(this.player.getLocation());
      this.player.sendMessage((new ArenaMessageRepository()).getArenaCenterMessage(arena.getName()));
    } else {
      this.player.sendMessage(ChatColor.RED + "This arena does not exist.");
    } 
  }
}
