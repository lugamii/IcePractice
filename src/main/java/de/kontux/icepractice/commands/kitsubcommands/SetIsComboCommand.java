package de.kontux.icepractice.commands.kitsubcommands;

import de.kontux.icepractice.kits.KitManager;
import org.bukkit.entity.Player;

public class SetIsComboCommand implements KitCommand {
  private final Player player;
  
  private final boolean combo;
  
  private final String kitName;
  
  public SetIsComboCommand(String kitName, Player player, boolean combo) {
    this.player = player;
    this.combo = combo;
    this.kitName = kitName;
  }
  
  public void execute() {
    KitManager.getInstance().setCombo(this.player, this.kitName, this.combo);
  }
}
