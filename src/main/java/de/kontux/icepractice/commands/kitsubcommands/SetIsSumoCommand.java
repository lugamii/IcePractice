package de.kontux.icepractice.commands.kitsubcommands;

import de.kontux.icepractice.kits.KitManager;
import org.bukkit.entity.Player;

public class SetIsSumoCommand implements KitCommand {
  private final String kitName;
  
  private final Player player;
  
  private final boolean sumo;
  
  public SetIsSumoCommand(String kitName, Player player, boolean sumo) {
    this.kitName = kitName;
    this.player = player;
    this.sumo = sumo;
  }
  
  public void execute() {
    KitManager.getInstance().setSumo(this.player, this.kitName, this.sumo);
  }
}
