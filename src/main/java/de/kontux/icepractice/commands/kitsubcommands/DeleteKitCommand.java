package de.kontux.icepractice.commands.kitsubcommands;

import de.kontux.icepractice.kits.KitManager;
import org.bukkit.entity.Player;

public class DeleteKitCommand implements KitCommand {
  private final String kitName;
  
  private final Player player;
  
  public DeleteKitCommand(String kitName, Player player) {
    this.kitName = kitName;
    this.player = player;
  }
  
  public void execute() {
    KitManager.getInstance().deleteKit(this.player, this.kitName);
  }
}
