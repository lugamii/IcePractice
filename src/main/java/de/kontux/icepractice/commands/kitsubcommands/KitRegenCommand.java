package de.kontux.icepractice.commands.kitsubcommands;

import de.kontux.icepractice.api.kit.IcePracticeKit;
import de.kontux.icepractice.configs.Settings;
import de.kontux.icepractice.configs.repositories.messages.KitMessageRepository;
import de.kontux.icepractice.kits.KitManager;
import org.bukkit.entity.Player;

public class KitRegenCommand implements KitCommand {
  private final Player player;
  
  private final boolean regen;
  
  private final String kitName;
  
  public KitRegenCommand(String kitName, Player player, boolean regen) {
    this.player = player;
    this.regen = regen;
    this.kitName = kitName;
  }
  
  public void execute() {
    IcePracticeKit kit = KitManager.getInstance().getKit(this.kitName);
    if (kit != null) {
      kit.setAllowRegen(this.regen);
      if (this.regen) {
        this.player.sendMessage(Settings.PRIMARY + "This kit now has regeneration.");
      } else {
        this.player.sendMessage(Settings.PRIMARY + "This kit no longer has regeneration.");
      } 
    } else {
      this.player.sendMessage((new KitMessageRepository()).getNotExistMessage());
    } 
  }
}
