package de.kontux.icepractice.commands;

import de.kontux.icepractice.guis.SettingsInventory;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SettingsCommandExecutor implements CommandExecutor {
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    if (sender instanceof Player) {
      (new SettingsInventory((Player)sender)).openMenu();
    } else {
      sender.sendMessage("You need to be a player to access the settings menu.");
    } 
    return true;
  }
}
