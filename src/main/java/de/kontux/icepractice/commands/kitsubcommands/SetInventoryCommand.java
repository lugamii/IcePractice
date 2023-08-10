package de.kontux.icepractice.commands.kitsubcommands;

import de.kontux.icepractice.kits.KitManager;
import org.bukkit.entity.Player;

public class SetInventoryCommand implements KitCommand {
  private final String kitName;
  
  private final Player player;
  
  public SetInventoryCommand(String kitName, Player player) {
    this.kitName = kitName;
    this.player = player;
  }
  
  public void execute() {
    KitManager.getInstance().setKitInventory(this.player, this.kitName);
  }
}
