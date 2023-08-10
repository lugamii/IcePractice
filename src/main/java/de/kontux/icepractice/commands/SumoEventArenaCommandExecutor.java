package de.kontux.icepractice.commands;

import de.kontux.icepractice.commands.sumoeventarenasubcommands.SumoEventArenaCreateCommand;
import de.kontux.icepractice.commands.sumoeventarenasubcommands.SumoEventArenaDeleteCommand;
import de.kontux.icepractice.commands.sumoeventarenasubcommands.SumoEventArenaListCommand;
import de.kontux.icepractice.commands.sumoeventarenasubcommands.SumoEventArenaPos1Command;
import de.kontux.icepractice.commands.sumoeventarenasubcommands.SumoEventArenaPos2Command;
import de.kontux.icepractice.commands.sumoeventarenasubcommands.SumoEventArenaSpawnCommand;
import de.kontux.icepractice.commands.sumoeventarenasubcommands.SumoEventArenaSubcommand;
import de.kontux.icepractice.commands.sumoeventarenasubcommands.SumoEventArenaTeleportCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SumoEventArenaCommandExecutor implements CommandExecutor {
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    if (sender instanceof Player) {
      Player player = (Player)sender;
      if (player.hasPermission("icepractice.arena")) {
        if (args.length > 1) {
          SumoEventArenaTeleportCommand sumoEventArenaTeleportCommand = null;
          String subCommand = args[0];
          String arenaName = args[1];
          SumoEventArenaSubcommand arenaSubcommand = null;
          if (subCommand.equalsIgnoreCase("create")) {
            SumoEventArenaCreateCommand sumoEventArenaCreateCommand = new SumoEventArenaCreateCommand(arenaName, player);
            sumoEventArenaCreateCommand.execute();
          } else if (subCommand.equalsIgnoreCase("pos1")) {
            SumoEventArenaPos1Command sumoEventArenaPos1Command = new SumoEventArenaPos1Command(arenaName, player);
            sumoEventArenaPos1Command.execute();
          } else if (subCommand.equalsIgnoreCase("pos2")) {
            SumoEventArenaPos2Command sumoEventArenaPos2Command = new SumoEventArenaPos2Command(arenaName, player);
            sumoEventArenaPos2Command.execute();
          } else if (subCommand.equalsIgnoreCase("spawn")) {
            SumoEventArenaSpawnCommand sumoEventArenaSpawnCommand = new SumoEventArenaSpawnCommand(arenaName, player);
            sumoEventArenaSpawnCommand.execute();
          } else if (subCommand.equalsIgnoreCase("delete")) {
            SumoEventArenaDeleteCommand sumoEventArenaDeleteCommand = new SumoEventArenaDeleteCommand(arenaName, player);
            sumoEventArenaDeleteCommand.execute();
          } else if (subCommand.equalsIgnoreCase("tp") || subCommand.equalsIgnoreCase("teleport")) {
            sumoEventArenaTeleportCommand = new SumoEventArenaTeleportCommand(arenaName, player);
            sumoEventArenaTeleportCommand.execute();
          } else {
            showHelp(player);
          }
        } else if (args.length == 1) {
          if (args[0].equalsIgnoreCase("list"))
            (new SumoEventArenaListCommand(player)).execute(); 
        } else {
          showHelp(player);
        } 
      } else {
        player.sendMessage("§cYou don't have access to this command.");
      } 
    } else {
      sender.sendMessage("This command can only be executed by players!");
    } 
    return true;
  }
  
  private void showHelp(Player player) {
    player.sendMessage("§6All Sumo Event arena commands:");
    player.sendMessage("§6/sumoeventarena create <name>");
    player.sendMessage("§6/sumoeventarena pos1 <name>");
    player.sendMessage("§6/sumoeventarena pos2 <name>");
    player.sendMessage("§6/sumoeventarena spawn <name>");
    player.sendMessage("§6/sumoeventarena delete <name>");
  }
}
