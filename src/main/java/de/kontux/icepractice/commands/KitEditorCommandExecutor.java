package de.kontux.icepractice.commands;

import de.kontux.icepractice.commands.kiteditorsubcommands.KitEditorSetAnvilCommand;
import de.kontux.icepractice.commands.kiteditorsubcommands.KitEditorSetChestCommand;
import de.kontux.icepractice.commands.kiteditorsubcommands.KitEditorSetLocationCommand;
import de.kontux.icepractice.commands.kiteditorsubcommands.KitEditorSetSignCommand;
import de.kontux.icepractice.commands.kiteditorsubcommands.KitEditorSubcommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class KitEditorCommandExecutor implements CommandExecutor {
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    if (sender instanceof Player) {
      Player player = (Player)sender;
      if (player.hasPermission("icepractice.admin")) {
        if (args.length > 0) {
          KitEditorSetLocationCommand kitEditorSetLocationCommand;
          KitEditorSetChestCommand kitEditorSetChestCommand;
          KitEditorSetAnvilCommand kitEditorSetAnvilCommand;
          KitEditorSetSignCommand kitEditorSetSignCommand = null;
          String subCommand = args[0].toLowerCase();
          KitEditorSubcommand cmd = null;
          switch (subCommand) {
            case "setlocation":
              kitEditorSetLocationCommand = new KitEditorSetLocationCommand(player);
              kitEditorSetLocationCommand.execute();
              break;
            case "setchest":
              kitEditorSetChestCommand = new KitEditorSetChestCommand(player);
              kitEditorSetChestCommand.execute();
              break;
            case "setanvil":
              kitEditorSetAnvilCommand = new KitEditorSetAnvilCommand(player);
              kitEditorSetAnvilCommand.execute();
              break;
            case "setsign":
              kitEditorSetSignCommand = new KitEditorSetSignCommand(player);
              kitEditorSetSignCommand.execute();
              break;
            default:
              showHelp(player);
              break;
          }
        } else {
          showHelp(player);
        }
      } else {
        player.sendMessage("§cYou don't have access to this command!");
      } 
    } else {
      sender.sendMessage("§cThis command can only be used by players");
    } 
    return true;
  }
  
  private void showHelp(Player player) {
    player.sendMessage("§c/editor setlocation");
    player.sendMessage("§c/editor setsign");
    player.sendMessage("§c/editor setchest");
    player.sendMessage("§c/editor setanvil");
  }
}
