package de.kontux.icepractice.commands.kitsubcommands;

import de.kontux.icepractice.kits.KitManager;
import org.bukkit.entity.Player;

public class SetIsRankedCommand implements KitCommand {
  private final String kitName;
  
  private final Player player;
  
  private final Boolean ranked;
  
  public SetIsRankedCommand(String kitName, Player player, boolean ranked) {
    this.kitName = kitName;
    this.player = player;
    this.ranked = Boolean.valueOf(ranked);
  }
  
  public void execute() {
    KitManager.getInstance().setRanked(this.player, this.kitName, this.ranked.booleanValue());
  }
}
