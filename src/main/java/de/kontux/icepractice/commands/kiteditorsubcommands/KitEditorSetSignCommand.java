package de.kontux.icepractice.commands.kiteditorsubcommands;

import de.kontux.icepractice.kiteditor.KitEditorRepository;
import org.bukkit.entity.Player;

public class KitEditorSetSignCommand implements KitEditorSubcommand {
  private Player player;
  
  public KitEditorSetSignCommand(Player player) {
    this.player = player;
  }
  
  public void execute() {
    (new KitEditorRepository()).setSign(this.player);
  }
}
