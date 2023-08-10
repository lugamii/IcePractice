package de.kontux.icepractice.commands.kiteditorsubcommands;

import de.kontux.icepractice.kiteditor.KitEditorRepository;
import org.bukkit.entity.Player;

public class KitEditorSetLocationCommand implements KitEditorSubcommand {
  private Player player;
  
  public KitEditorSetLocationCommand(Player player) {
    this.player = player;
  }
  
  public void execute() {
    (new KitEditorRepository()).setLocation(this.player);
  }
}
