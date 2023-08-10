package de.kontux.icepractice.commands;

import de.kontux.icepractice.commands.eventsubcommands.EventHostCommand;
import de.kontux.icepractice.commands.eventsubcommands.EventJoinCommand;
import de.kontux.icepractice.commands.eventsubcommands.EventStartCommand;
import de.kontux.icepractice.commands.eventsubcommands.EventStopCommand;
import de.kontux.icepractice.commands.eventsubcommands.EventSubcommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class EventCommandExecutor implements CommandExecutor {
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    if (sender instanceof Player) {
      Player player = (Player)sender;
      EventSubcommand eventSubcommand = null;
      if (args.length > 0) {
        EventJoinCommand eventJoinCommand = null;
        String subcommand = args[0];
        if (subcommand.equalsIgnoreCase("host")) {
          EventHostCommand eventHostCommand = new EventHostCommand(player);
          eventHostCommand.execute();
        } else if (subcommand.equalsIgnoreCase("expireCooldown")) {
          EventStartCommand eventStartCommand = new EventStartCommand(player);
          eventStartCommand.execute();
        } else if (subcommand.equalsIgnoreCase("stop")) {
          EventStopCommand eventStopCommand = new EventStopCommand(player);
          eventStopCommand.execute();
        } else if (subcommand.equalsIgnoreCase("join")) {
          eventJoinCommand = new EventJoinCommand(player, args);
          eventJoinCommand.execute();
        } else {
          showHelp(player);
        }
      }
    } 
    return true;
  }
  
  private void showHelp(Player player) {
    player.sendMessage("§c/event join");
    player.sendMessage("§c/event host");
    player.sendMessage("§c/event expireCooldown");
    player.sendMessage("§c/event stop");
  }
}
