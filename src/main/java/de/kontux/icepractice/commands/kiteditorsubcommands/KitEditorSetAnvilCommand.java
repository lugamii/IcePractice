package de.kontux.icepractice.commands.kiteditorsubcommands;

import de.kontux.icepractice.kiteditor.KitEditorRepository;
import org.bukkit.entity.Player;

public class KitEditorSetAnvilCommand implements KitEditorSubcommand {
  private Player player;
  
  public KitEditorSetAnvilCommand(Player player) {
    this.player = player;
  }
  
  public void execute() {
    (new KitEditorRepository()).setAnvil(this.player);
  }
}
