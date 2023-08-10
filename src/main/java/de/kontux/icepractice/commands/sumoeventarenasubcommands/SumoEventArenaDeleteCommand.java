package de.kontux.icepractice.commands.sumoeventarenasubcommands;

import de.kontux.icepractice.arenas.SumoEventArenaManager;
import org.bukkit.entity.Player;

public class SumoEventArenaDeleteCommand implements SumoEventArenaSubcommand {
  private final String arenaName;
  
  private final Player player;
  
  public SumoEventArenaDeleteCommand(String arenaName, Player player) {
    this.arenaName = arenaName;
    this.player = player;
  }
  
  public void execute() {
    SumoEventArenaManager.getInstance().delete(this.player, this.arenaName);
  }
}
