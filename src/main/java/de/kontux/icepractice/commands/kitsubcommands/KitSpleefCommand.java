package de.kontux.icepractice.commands.kitsubcommands;

import de.kontux.icepractice.api.kit.IcePracticeKit;
import de.kontux.icepractice.configs.Settings;
import de.kontux.icepractice.configs.repositories.messages.KitMessageRepository;
import de.kontux.icepractice.kits.KitManager;
import org.bukkit.entity.Player;

public class KitSpleefCommand implements KitCommand {
  private final Player player;
  
  private final boolean spleef;
  
  private final String kitName;
  
  public KitSpleefCommand(String kitName, Player player, boolean spleef) {
    this.player = player;
    this.spleef = spleef;
    this.kitName = kitName;
  }
  
  public void execute() {
    IcePracticeKit kit = KitManager.getInstance().getKit(this.kitName);
    if (kit != null) {
      kit.setSpleef(this.spleef);
      if (this.spleef) {
        this.player.sendMessage(Settings.PRIMARY + "This kit is now a spleef kit.");
      } else {
        this.player.sendMessage(Settings.PRIMARY + "This kit is no longer a spleef kit.");
      } 
    } else {
      this.player.sendMessage((new KitMessageRepository()).getNotExistMessage());
    } 
  }
}
