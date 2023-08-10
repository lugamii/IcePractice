package de.kontux.icepractice.commands.kiteditorsubcommands;

import de.kontux.icepractice.kiteditor.KitEditorRepository;
import org.bukkit.entity.Player;

public class KitEditorSetChestCommand implements KitEditorSubcommand {
  private Player player;
  
  public KitEditorSetChestCommand(Player player) {
    this.player = player;
  }
  
  public void execute() {
    (new KitEditorRepository()).setChest(this.player);
  }
}
