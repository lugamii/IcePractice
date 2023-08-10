package de.kontux.icepractice.commands.kitsubcommands;

import de.kontux.icepractice.api.kit.IcePracticeKit;
import de.kontux.icepractice.configs.Settings;
import de.kontux.icepractice.configs.repositories.messages.KitMessageRepository;
import de.kontux.icepractice.kits.KitManager;
import org.bukkit.entity.Player;

public class KitChestEditCommand implements KitCommand {
  private final Player player;
  
  private final boolean chestEdit;
  
  private final String kitName;
  
  public KitChestEditCommand(String kitName, Player player, boolean chestEdit) {
    this.player = player;
    this.chestEdit = chestEdit;
    this.kitName = kitName;
  }
  
  public void execute() {
    IcePracticeKit kit = KitManager.getInstance().getKit(this.kitName);
    if (kit != null) {
      kit.setAllowChestEditing(this.chestEdit);
      if (this.chestEdit) {
        this.player.sendMessage(Settings.PRIMARY + "This kit may now be edited with a chest.");
      } else {
        this.player.sendMessage(Settings.PRIMARY + "This kit may no longer be edited with a chest.");
      } 
    } else {
      this.player.sendMessage((new KitMessageRepository()).getNotExistMessage());
    } 
  }
}
