package de.kontux.icepractice.commands;

import de.kontux.icepractice.IcePracticePlugin;
import de.kontux.icepractice.configs.Settings;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PingCommandExecutor implements CommandExecutor {
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    if (sender instanceof Player) {
      Player player = (Player)sender;
      if (args.length == 0) {
        player.sendMessage(Settings.PRIMARY + "Your ping: " + Settings.SECONDARY + getPing(player) + "ms");
      } else {
        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
          player.sendMessage(ChatColor.RED + "This player is not connected to the server.");
          return true;
        } 
        player.sendMessage(Settings.SECONDARY + target.getDisplayName() + Settings.PRIMARY + "'s ping: " + Settings.SECONDARY + getPing(player) + "ms");
      } 
    } 
    return true;
  }
  
  private int getPing(Player player) {
    return IcePracticePlugin.getNmsHandler().getPing(player);
  }
}
