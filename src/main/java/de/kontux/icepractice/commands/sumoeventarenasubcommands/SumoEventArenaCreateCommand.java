package de.kontux.icepractice.commands.sumoeventarenasubcommands;

import de.kontux.icepractice.arenas.SumoEventArenaManager;
import org.bukkit.entity.Player;

public class SumoEventArenaCreateCommand implements SumoEventArenaSubcommand {
  private final Player player;
  
  private final String arenaName;
  
  public SumoEventArenaCreateCommand(String arenaName, Player player) {
    this.arenaName = arenaName;
    this.player = player;
  }
  
  public void execute() {
    SumoEventArenaManager.getInstance().create(this.player, this.arenaName);
  }
}
