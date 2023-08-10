package de.kontux.icepractice.commands.arenasubcommands;

import de.kontux.icepractice.api.arena.IcePracticeArena;
import de.kontux.icepractice.arenas.ArenaManager;
import de.kontux.icepractice.configs.messages.ArenaMessageRepository;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class ArenaSpleefCommand implements ArenaCommand {
  private final String arenaName;
  
  private final Player player;
  
  private final boolean spleef;
  
  public ArenaSpleefCommand(String arenaName, Player player, boolean spleef) {
    this.arenaName = arenaName;
    this.player = player;
    this.spleef = spleef;
  }
  
  public void execute() {
    IcePracticeArena arena = ArenaManager.getInstance().getArena(this.arenaName);
    if (arena != null) {
      arena.setSpleef(this.spleef);
      if (this.spleef) {
        this.player.sendMessage((new ArenaMessageRepository()).getArenaSpleefTrueMessage(this.arenaName));
      } else {
        this.player.sendMessage((new ArenaMessageRepository()).getArenaSpleefFalseMessage(this.arenaName));
      } 
    } else {
      this.player.sendMessage(ChatColor.RED + "This arena does not exist.");
    } 
  }
}
