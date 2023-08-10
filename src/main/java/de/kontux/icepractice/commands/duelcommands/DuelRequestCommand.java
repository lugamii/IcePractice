package de.kontux.icepractice.commands.duelcommands;

import de.kontux.icepractice.guis.DuelInventory;
import org.bukkit.entity.Player;

public class DuelRequestCommand {
  private final Player player;
  
  private final Player opponent;
  
  public DuelRequestCommand(Player sender, Player challenged) {
    this.player = sender;
    this.opponent = challenged;
  }
  
  public void execute() {
    (new DuelInventory(this.player, this.opponent)).openMenu();
  }
}
