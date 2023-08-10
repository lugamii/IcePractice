package de.kontux.icepractice.commands.sumoeventarenasubcommands;

import de.kontux.icepractice.arenas.SumoEventArena;
import de.kontux.icepractice.arenas.SumoEventArenaManager;
import de.kontux.icepractice.configs.messages.ArenaMessageRepository;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class SumoEventArenaPos1Command implements SumoEventArenaSubcommand {
  private final Player player;
  
  private final String arenaName;
  
  public SumoEventArenaPos1Command(String arenaName, Player player) {
    this.arenaName = arenaName;
    this.player = player;
  }
  
  public void execute() {
    SumoEventArena arena = SumoEventArenaManager.getInstance().getArena(this.arenaName);
    if (arena != null) {
      arena.setPos1(this.player.getLocation());
      this.player.sendMessage((new ArenaMessageRepository()).getArenaPos1Message(arena.getName()));
    } else {
      this.player.sendMessage(ChatColor.RED + "This arena does not exist.");
    } 
  }
}
