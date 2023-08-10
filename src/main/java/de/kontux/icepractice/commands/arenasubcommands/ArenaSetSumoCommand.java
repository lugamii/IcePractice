package de.kontux.icepractice.commands.arenasubcommands;

import de.kontux.icepractice.api.arena.IcePracticeArena;
import de.kontux.icepractice.arenas.ArenaManager;
import de.kontux.icepractice.configs.messages.ArenaMessageRepository;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class ArenaSetSumoCommand implements ArenaCommand {
  private final String arenaName;
  
  private final Player player;
  
  private final boolean isSumo;
  
  public ArenaSetSumoCommand(String arenaName, Player player, boolean isSumo) {
    this.arenaName = arenaName;
    this.player = player;
    this.isSumo = isSumo;
  }
  
  public void execute() {
    IcePracticeArena arena = ArenaManager.getInstance().getArena(this.arenaName);
    if (arena != null) {
      arena.setSumo(this.isSumo);
      if (this.isSumo) {
        this.player.sendMessage((new ArenaMessageRepository()).getArenaSumoTrueMessage(this.arenaName));
      } else {
        this.player.sendMessage((new ArenaMessageRepository()).getArenaSumoFalseMessage(this.arenaName));
      } 
    } else {
      this.player.sendMessage(ChatColor.RED + "This arena does not exist.");
    } 
  }
}
