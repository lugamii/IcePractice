package de.kontux.icepractice.commands;

import de.kontux.icepractice.commands.arenasubcommands.ArenaBuildCommand;
import de.kontux.icepractice.commands.arenasubcommands.ArenaCenterCommand;
import de.kontux.icepractice.commands.arenasubcommands.ArenaCommand;
import de.kontux.icepractice.commands.arenasubcommands.ArenaCreateCommand;
import de.kontux.icepractice.commands.arenasubcommands.ArenaDeleteCommand;
import de.kontux.icepractice.commands.arenasubcommands.ArenaHcfCommand;
import de.kontux.icepractice.commands.arenasubcommands.ArenaListCommand;
import de.kontux.icepractice.commands.arenasubcommands.ArenaPos1Command;
import de.kontux.icepractice.commands.arenasubcommands.ArenaPos2Command;
import de.kontux.icepractice.commands.arenasubcommands.ArenaSetSumoCommand;
import de.kontux.icepractice.commands.arenasubcommands.ArenaSettingsCommand;
import de.kontux.icepractice.commands.arenasubcommands.ArenaSpleefCommand;
import de.kontux.icepractice.commands.arenasubcommands.ArenaTeleportCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ArenaCommandExecutor implements CommandExecutor {
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    if (sender instanceof Player) {
      if (sender.hasPermission("icepractice.arena")) {
        Player player = (Player) sender;
        if (args.length == 0) {
          showHelp(player);
          return true;
        }
        ArenaCommand cmd;
        String subCommand = args[0].toLowerCase();
        ArenaBuildCommand arenaBuildCommand = null;
        if (args.length == 2) {
          String arenaName = args[1];
          ArenaCreateCommand arenaCreateCommand;
          ArenaPos1Command arenaPos1Command;
          ArenaPos2Command arenaPos2Command;
          ArenaTeleportCommand arenaTeleportCommand;
          ArenaCenterCommand arenaCenterCommand;
          ArenaDeleteCommand arenaDeleteCommand;
          ArenaSettingsCommand arenaSettingsCommand;
          switch (subCommand) {
            case "create":
              arenaCreateCommand = new ArenaCreateCommand(arenaName, player);
              arenaCreateCommand.execute();
              break;
            case "pos1":
              arenaPos1Command = new ArenaPos1Command(arenaName, player);
              arenaPos1Command.execute();
              break;
            case "pos2":
              arenaPos2Command = new ArenaPos2Command(arenaName, player);
              arenaPos2Command.execute();
              break;
            case "teleport":
            case "tp":
              arenaTeleportCommand = new ArenaTeleportCommand(player, arenaName);
              arenaTeleportCommand.execute();
              break;
            case "center":
              arenaCenterCommand = new ArenaCenterCommand(arenaName, player);
              arenaCenterCommand.execute();
              break;
            case "delete":
              arenaDeleteCommand = new ArenaDeleteCommand(arenaName, player);
              arenaDeleteCommand.execute();
              break;
            case "settings":
            case "options":
              arenaSettingsCommand = new ArenaSettingsCommand(arenaName, player);
              arenaSettingsCommand.execute();
              break;
          }
        } else if (args.length == 1) {
          if (subCommand.equalsIgnoreCase("list")) {
            ArenaListCommand arenaListCommand = new ArenaListCommand(player);
            arenaListCommand.execute();
          }
        } else if (args.length == 3) {
          ArenaSetSumoCommand arenaSetSumoCommand;
          ArenaSpleefCommand arenaSpleefCommand;
          ArenaHcfCommand arenaHcfCommand;
          String arenaName = args[1];
          boolean b = Boolean.parseBoolean(args[2]);
          arenaBuildCommand = new ArenaBuildCommand(arenaName, player, b);
          switch (subCommand) {
            case "sumo":
              arenaSetSumoCommand = new ArenaSetSumoCommand(arenaName, player, b);
              arenaSetSumoCommand.execute();
              break;
            case "spleef":
              arenaSpleefCommand = new ArenaSpleefCommand(arenaName, player, b);
              arenaSpleefCommand.execute();
              break;
            case "hcf":
              arenaHcfCommand = new ArenaHcfCommand(arenaName, player, b);
              arenaHcfCommand.execute();
              break;
            case "build":
              arenaBuildCommand = new ArenaBuildCommand(arenaName, player, b);
              arenaBuildCommand.execute();
              break;
          }
        }
      } else {
        sender.sendMessage("§cYou don't have the permission to create arenas.");
      } 
    } else {
      sender.sendMessage("§cYou must be a player to create arenas.");
    } 
    return true;
  }
  
  private void showHelp(Player player) {
    player.sendMessage("§6All /arena commands:");
    player.sendMessage("§6/arena create <name>");
    player.sendMessage("§6/arena pos1 <name>");
    player.sendMessage("§6/arena pos2 <name>");
    player.sendMessage("§6/arena center <name>");
    player.sendMessage("§6/arena settings <name>");
    player.sendMessage("§6/arena delete <name>");
    player.sendMessage("§6/arena build <name> <boolean>");
    player.sendMessage("§6/arena sumo <name> <boolean>");
    player.sendMessage("§6/arena spleef <name> <boolean>");
    player.sendMessage("§6/arena hcf <name> <boolean>");
    player.sendMessage("§6/arena settings <name>");
    player.sendMessage("§6/arena list");
  }
}
