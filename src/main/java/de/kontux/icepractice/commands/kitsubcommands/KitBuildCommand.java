package de.kontux.icepractice.commands.kitsubcommands;

import de.kontux.icepractice.api.kit.IcePracticeKit;
import de.kontux.icepractice.configs.Settings;
import de.kontux.icepractice.configs.repositories.messages.KitMessageRepository;
import de.kontux.icepractice.kits.KitManager;
import org.bukkit.entity.Player;

public class KitBuildCommand implements KitCommand {
  private final Player player;
  
  private final boolean build;
  
  private final String kitName;
  
  public KitBuildCommand(String kitName, Player player, boolean build) {
    this.player = player;
    this.build = build;
    this.kitName = kitName;
  }
  
  public void execute() {
    IcePracticeKit kit = KitManager.getInstance().getKit(this.kitName);
    if (kit != null) {
      kit.setAllowBuild(this.build);
      if (this.build) {
        this.player.sendMessage(Settings.PRIMARY + "Players can now build while playing with this kit.");
      } else {
        this.player.sendMessage(Settings.PRIMARY + "Players can no longer build while playing with this kit.");
      } 
    } else {
      this.player.sendMessage((new KitMessageRepository()).getNotExistMessage());
    } 
  }
}
