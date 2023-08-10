package de.kontux.icepractice.commands.kitsubcommands;

import de.kontux.icepractice.api.kit.IcePracticeKit;
import de.kontux.icepractice.configs.Settings;
import de.kontux.icepractice.configs.repositories.messages.KitMessageRepository;
import de.kontux.icepractice.kits.KitManager;
import org.bukkit.entity.Player;

public class KitEditableCommand implements KitCommand {
  private final Player player;
  
  private final boolean editable;
  
  private final String kitName;
  
  public KitEditableCommand(String kitName, Player player, boolean editable) {
    this.player = player;
    this.editable = editable;
    this.kitName = kitName;
  }
  
  public void execute() {
    IcePracticeKit kit = KitManager.getInstance().getKit(this.kitName);
    if (kit != null) {
      kit.setEditable(this.editable);
      if (this.editable) {
        this.player.sendMessage(Settings.PRIMARY + "This kit can now be edited in the kit editor.");
      } else {
        this.player.sendMessage(Settings.PRIMARY + "This kit can no longer be edited in the kit editor.");
      } 
    } else {
      this.player.sendMessage((new KitMessageRepository()).getNotExistMessage());
    } 
  }
}
