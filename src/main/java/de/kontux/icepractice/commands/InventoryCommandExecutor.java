package de.kontux.icepractice.commands;

import de.kontux.icepractice.api.playerstates.PlayerState;
import de.kontux.icepractice.guis.InventoryViewInventory;
import de.kontux.icepractice.playermanagement.PlayerStates;
import de.kontux.icepractice.postiventories.AfterMatchInventory;
import de.kontux.icepractice.registries.InventoryRegistry;
import java.util.UUID;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class InventoryCommandExecutor implements CommandExecutor {
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    if (sender instanceof Player) {
      Player player = (Player)sender;
      if (PlayerStates.getInstance().getState(player) == PlayerState.IDLE) {
        if (args.length > 0) {
          UUID uuid = null;
          try {
            uuid = UUID.fromString(args[0]);
          } catch (IllegalArgumentException e) {
            player.sendMessage(ChatColor.RED + "Invalid UUID!");
          } 
          if (uuid != null) {
            AfterMatchInventory matchInventory = InventoryRegistry.getInventory(uuid);
            if (matchInventory != null) {
              (new InventoryViewInventory(player, matchInventory)).openMenu();
            } else {
              player.sendMessage("§cUnable to find that player's inventory.");
            } 
          } else {
            player.sendMessage(ChatColor.RED + "/inv <uuid>");
          } 
        } 
      } else {
        player.sendMessage("§cYou must be at spawn to use /inventory.");
      } 
    } else {
      sender.sendMessage("You must be a player to execute this command.");
    } 
    return true;
  }
}
