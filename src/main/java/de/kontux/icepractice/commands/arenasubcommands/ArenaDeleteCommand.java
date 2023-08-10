package de.kontux.icepractice.commands.arenasubcommands;

import de.kontux.icepractice.arenas.ArenaManager;
import org.bukkit.entity.Player;

public class ArenaDeleteCommand implements ArenaCommand {
  private final String arenaName;
  
  private final Player player;
  
  public ArenaDeleteCommand(String arenaName, Player player) {
    this.arenaName = arenaName;
    this.player = player;
  }
  
  public void execute() {
    ArenaManager.getInstance().delete(this.player, this.arenaName);
  }
}
