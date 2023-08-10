package de.kontux.icepractice.commands.kitsubcommands;

import de.kontux.icepractice.kits.KitManager;
import org.bukkit.entity.Player;

public class CreateKitCommand implements KitCommand {
  private final String kitName;
  
  private final Player player;
  
  public CreateKitCommand(String kitName, Player player) {
    this.kitName = kitName;
    this.player = player;
  }
  
  public void execute() {
    KitManager.getInstance().createKit(this.player, this.kitName);
  }
}
