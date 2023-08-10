package de.kontux.icepractice.commands;

import de.kontux.icepractice.api.match.IcePracticeFight;
import de.kontux.icepractice.api.playerstates.PlayerState;
import de.kontux.icepractice.playermanagement.PlayerStates;
import de.kontux.icepractice.registries.FightRegistry;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpectateCommandExecutor implements CommandExecutor {
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    if (sender instanceof Player) {
      Player player = (Player)sender;
      if (PlayerStates.getInstance().getState(player) == PlayerState.IDLE) {
        if (args.length > 0) {
          Player toSpectate = Bukkit.getServer().getPlayer(args[0]);
          if (toSpectate != null) {
            IcePracticeFight fight = FightRegistry.getInstance().getFightByPlayer(toSpectate);
            if (fight != null) {
              fight.addSpectator(player, true);
            } else {
              player.sendMessage("§cThis player is not in a fight.");
            } 
          } else {
            player.sendMessage("§cPlayer couldn't be found.");
          } 
        } else {
          player.sendMessage(ChatColor.RED + "/spectate <player>");
        } 
      } else {
        player.sendMessage("§You mus tbe at spawn to spectate matches.");
      } 
    } else {
      sender.sendMessage("§cYou must be a player to use /spectate.");
    } 
    return true;
  }
}
