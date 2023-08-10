package de.kontux.icepractice.commands.kitsubcommands;

import de.kontux.icepractice.api.kit.IcePracticeKit;
import de.kontux.icepractice.configs.Settings;
import de.kontux.icepractice.configs.repositories.messages.KitMessageRepository;
import de.kontux.icepractice.kits.KitManager;
import org.bukkit.entity.Player;

public class KitHcfCommand implements KitCommand {
  private final Player player;
  
  private final boolean hcf;
  
  private final String kitName;
  
  public KitHcfCommand(String kitName, Player player, boolean hcf) {
    this.player = player;
    this.hcf = hcf;
    this.kitName = kitName;
  }
  
  public void execute() {
    IcePracticeKit kit = KitManager.getInstance().getKit(this.kitName);
    if (kit != null) {
      kit.setHcf(this.hcf);
      if (this.hcf) {
        this.player.sendMessage(Settings.PRIMARY + "This kit will only use HCF arenas.");
      } else {
        this.player.sendMessage(Settings.PRIMARY + "This kit will no longer use HCF arenas.");
      } 
    } else {
      this.player.sendMessage((new KitMessageRepository()).getNotExistMessage());
    } 
  }
}
