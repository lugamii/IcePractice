package de.kontux.icepractice.commands.arenasubcommands;

import de.kontux.icepractice.api.arena.IcePracticeArena;
import de.kontux.icepractice.arenas.ArenaManager;
import de.kontux.icepractice.configs.messages.ArenaMessageRepository;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class ArenaHcfCommand implements ArenaCommand {
  private final String arenaName;
  
  private final Player player;
  
  private final boolean hcf;
  
  public ArenaHcfCommand(String arenaName, Player player, boolean hcf) {
    this.arenaName = arenaName;
    this.player = player;
    this.hcf = hcf;
  }
  
  public void execute() {
    IcePracticeArena arena = ArenaManager.getInstance().getArena(this.arenaName);
    if (arena != null) {
      arena.setHcf(this.hcf);
      if (this.hcf) {
        this.player.sendMessage((new ArenaMessageRepository()).getArenaHcfTrueMessage(this.arenaName));
      } else {
        this.player.sendMessage((new ArenaMessageRepository()).getArenaHcfFalseMessage(this.arenaName));
      } 
    } else {
      this.player.sendMessage(ChatColor.RED + "This arena does not exist.");
    } 
  }
}
