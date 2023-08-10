package de.kontux.icepractice.commands;

import de.kontux.icepractice.guis.RankedQueueInventory;
import de.kontux.icepractice.guis.UnrankedQueueInventory;
import de.kontux.icepractice.queue.Queue;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class QueueCommandExecutor implements CommandExecutor {
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    if (sender instanceof Player) {
      if (args.length == 1) {
        Player player = (Player)sender;
        if (Queue.isInQueue(player)) {
          player.sendMessage(ChatColor.RED + "You can't queue twice at the same time.");
          return true;
        } 
        String subCommand = args[0];
        if (subCommand.equals("unranked")) {
          UnrankedQueueInventory unrankedmenu = new UnrankedQueueInventory(player);
          unrankedmenu.openMenu();
        } else if (subCommand.equals("ranked")) {
          RankedQueueInventory rankedmenu = new RankedQueueInventory(player);
          rankedmenu.openMenu();
        } else {
          sender.sendMessage("§c Invalid argument! Use /queue (un)ranked.");
        } 
      } else {
        sender.sendMessage("§c/Queue requires§6 1§c argument! Use /queue (un)ranked.");
      } 
    } else {
      sender.sendMessage("You must be a player to use /queue.");
    } 
    return true;
  }
}
