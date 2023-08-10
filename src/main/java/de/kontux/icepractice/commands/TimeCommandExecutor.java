package de.kontux.icepractice.commands;

import de.kontux.icepractice.api.user.WorldTime;
import de.kontux.icepractice.userdata.PlayerData;
import de.kontux.icepractice.userdata.PlayerDataManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TimeCommandExecutor implements CommandExecutor {
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    if (sender instanceof Player) {
      PlayerData data = PlayerDataManager.getSettingsData(((Player)sender).getUniqueId());
      switch (command.getName()) {
        case "day":
          data.setWorldTime(WorldTime.DAY);
          break;
        case "sunset":
          data.setWorldTime(WorldTime.SUNSET);
          break;
        case "night":
          data.setWorldTime(WorldTime.NIGHT);
          break;
      } 
    } 
    return true;
  }
}
