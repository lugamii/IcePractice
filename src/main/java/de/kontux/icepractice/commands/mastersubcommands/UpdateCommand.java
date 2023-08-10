package de.kontux.icepractice.commands.mastersubcommands;

import de.kontux.icepractice.IcePracticePlugin;
import de.kontux.icepractice.updater.UpdateChecker;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class UpdateCommand implements MasterSubCommand {
  private final Player player;
  
  public UpdateCommand(Player player) {
    this.player = player;
  }
  
  public void execute() {
    (new UpdateChecker(IcePracticePlugin.getInstance(), 75338)).getVersion(version -> {
          if (IcePracticePlugin.getInstance().getDescription().getVersion().equalsIgnoreCase(version)) {
            this.player.sendMessage("No new updates found");
          } else {
            this.player.sendMessage(ChatColor.RED + "[IcePractice] There is a new version available. Please visit https://www.spigotmc.org/resources/icepractice-queues-sumo-combo-sumo-events-kit-editor-and-more.75338/ to expireCooldown the plugin.");
          } 
        });
  }
}
