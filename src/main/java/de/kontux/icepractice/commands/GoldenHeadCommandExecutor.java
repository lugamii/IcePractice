package de.kontux.icepractice.commands;

import de.kontux.icepractice.configs.Settings;
import de.kontux.icepractice.util.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class GoldenHeadCommandExecutor implements CommandExecutor {
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    if (sender instanceof Player) {
      Player player = (Player)sender;
      if (player.hasPermission("icepractice.ghead")) {
        ItemStack goldenHead = ItemBuilder.create(Material.GOLDEN_APPLE, ChatColor.GOLD + "Golden Head", null);
        player.getInventory().addItem(new ItemStack[] { goldenHead });
        player.sendMessage(Settings.PRIMARY + "You have been given a golden head.");
      } 
    } 
    return true;
  }
}
