package de.kontux.icepractice.commands.kitsubcommands;

import de.kontux.icepractice.api.kit.IcePracticeKit;
import de.kontux.icepractice.configs.repositories.messages.KitMessageRepository;
import de.kontux.icepractice.guis.settings.KitSettingsInventory;
import de.kontux.icepractice.kits.KitManager;
import org.bukkit.entity.Player;

public class KitSettingsCommand implements KitCommand {
  private final String kitName;
  
  private final Player player;
  
  public KitSettingsCommand(String kitName, Player player) {
    this.kitName = kitName;
    this.player = player;
  }
  
  public void execute() {
    IcePracticeKit kit = KitManager.getInstance().getKit(this.kitName);
    if (kit == null) {
      this.player.sendMessage((new KitMessageRepository()).getNotExistMessage());
      return;
    } 
    (new KitSettingsInventory(this.player, kit)).openMenu();
  }
}
