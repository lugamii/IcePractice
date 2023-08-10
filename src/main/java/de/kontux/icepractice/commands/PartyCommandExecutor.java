package de.kontux.icepractice.commands;

import de.kontux.icepractice.api.playerstates.PlayerState;
import de.kontux.icepractice.commands.partysubcommands.PartyAcceptCommand;
import de.kontux.icepractice.commands.partysubcommands.PartyCloseCommand;
import de.kontux.icepractice.commands.partysubcommands.PartyCommand;
import de.kontux.icepractice.commands.partysubcommands.PartyCreateCommand;
import de.kontux.icepractice.commands.partysubcommands.PartyDisbandCommand;
import de.kontux.icepractice.commands.partysubcommands.PartyInfoCommand;
import de.kontux.icepractice.commands.partysubcommands.PartyInviteCommand;
import de.kontux.icepractice.commands.partysubcommands.PartyJoinCommand;
import de.kontux.icepractice.commands.partysubcommands.PartyKickCommand;
import de.kontux.icepractice.commands.partysubcommands.PartyOpenCommand;
import de.kontux.icepractice.playermanagement.PlayerStates;
import de.kontux.icepractice.registries.PartyRegistry;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PartyCommandExecutor implements CommandExecutor {
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    if (sender instanceof Player) {
      Player player = (Player)sender;
      if (PlayerStates.getInstance().getState(player) == PlayerState.IDLE || PartyRegistry.isInParty(player)) {
        if (args.length > 0) {
          PartyCreateCommand partyCreateCommand;
          PartyOpenCommand partyOpenCommand;
          PartyCloseCommand partyCloseCommand;
          PartyInfoCommand partyInfoCommand;
          PartyDisbandCommand partyDisbandCommand;
          PartyAcceptCommand partyAcceptCommand = null;
          String subCommand = args[0];
          PartyCommand cmd = null;
          switch (subCommand) {
            case "create":
              partyCreateCommand = new PartyCreateCommand(player);
              partyCreateCommand.execute();
              break;
            case "open":
              partyOpenCommand = new PartyOpenCommand(player);
              partyOpenCommand.execute();
              break;
            case "close":
              partyCloseCommand = new PartyCloseCommand(player);
              partyCloseCommand.execute();
              break;
            case "info":
              partyInfoCommand = new PartyInfoCommand(player);
              partyInfoCommand.execute();
              break;
            case "disband":
              partyDisbandCommand = new PartyDisbandCommand(player);
              partyDisbandCommand.execute();
              break;
            case "join":
              if (args.length > 1) {
                Player leader = Bukkit.getServer().getPlayer(args[1]);
                PartyJoinCommand partyJoinCommand = new PartyJoinCommand(player, leader);
                partyJoinCommand.execute();
              } 
              break;
            case "invite":
              if (args.length > 1) {
                Player guest = Bukkit.getServer().getPlayer(args[1]);
                PartyInviteCommand partyInviteCommand = new PartyInviteCommand(player, guest);
                partyInviteCommand.execute();
              } 
              break;
            case "kick":
              if (args.length > 1) {
                Player playerToKick = Bukkit.getServer().getPlayer(args[1]);
                PartyKickCommand partyKickCommand = new PartyKickCommand(player, playerToKick);
                partyKickCommand.execute();
              } 
              break;
            case "accept":
              if (args.length > 1)
                partyAcceptCommand = new PartyAcceptCommand(player, args[1]);
                partyAcceptCommand.execute();
              break;
          }
        } else {
          showHelp(player);
        }
      } else {
        player.sendMessage("§cYou must be at spawn or in a party to join/interact with parties");
      } 
    } 
    return true;
  }
  
  private void showHelp(Player player) {
    player.sendMessage("§c/party create");
    player.sendMessage("§c/party accept <leader>");
    player.sendMessage("§c/party join <leader>");
    player.sendMessage("§c/party invite <player>");
    player.sendMessage("§c/party open");
    player.sendMessage("§c/party close");
    player.sendMessage("§c/party info");
    player.sendMessage("§c/party kick <player>");
    player.sendMessage("§c/party disband");
  }
}
