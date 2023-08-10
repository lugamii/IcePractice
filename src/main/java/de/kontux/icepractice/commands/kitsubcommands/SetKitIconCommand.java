package de.kontux.icepractice.commands.kitsubcommands;

import de.kontux.icepractice.kits.KitManager;
import org.bukkit.entity.Player;

public class SetKitIconCommand implements KitCommand {
  private final String kitName;
  
  private final Player player;
  
  public SetKitIconCommand(String kitName, Player player) {
    this.kitName = kitName;
    this.player = player;
  }
  
  public void execute() {
    KitManager.getInstance().setIcon(this.player, this.kitName);
  }
}
