package de.kontux.icepractice.commands;

import de.kontux.icepractice.api.playerstates.PlayerState;
import de.kontux.icepractice.commands.duelcommands.DuelRequestCommand;
import de.kontux.icepractice.playermanagement.PlayerStates;
import de.kontux.icepractice.userdata.PlayerDataManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DuelCommandExecutor implements CommandExecutor {
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    if (!(sender instanceof Player))
      return false; 
    Player player = (Player)sender;
    if (args.length != 1)
      return false; 
    Player challenged = Bukkit.getPlayer(args[0]);
    if (challenged != null) {
      if (player != challenged) {
        if (PlayerStates.getInstance().getState(player) == PlayerState.IDLE) {
          boolean requests = PlayerDataManager.getSettingsData(challenged.getUniqueId()).isSendRequests();
          if (requests) {
            (new DuelRequestCommand(player, challenged)).execute();
          } else {
            player.sendMessage("§cThis player disabled duel requests.");
          } 
        } else {
          player.sendMessage("§cThis player is already in a match.");
        } 
      } else {
        player.sendMessage("§cYou can't duel yourself.");
      } 
    } else {
      player.sendMessage("§cThis player isn't online.");
    } 
    return true;
  }
}
