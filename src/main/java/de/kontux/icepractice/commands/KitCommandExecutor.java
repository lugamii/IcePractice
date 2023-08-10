package de.kontux.icepractice.commands;

import de.kontux.icepractice.commands.kitsubcommands.CooldownKitCommand;
import de.kontux.icepractice.commands.kitsubcommands.CreateKitCommand;
import de.kontux.icepractice.commands.kitsubcommands.DeleteKitCommand;
import de.kontux.icepractice.commands.kitsubcommands.KitBuildCommand;
import de.kontux.icepractice.commands.kitsubcommands.KitChestEditCommand;
import de.kontux.icepractice.commands.kitsubcommands.KitCommand;
import de.kontux.icepractice.commands.kitsubcommands.KitEditableCommand;
import de.kontux.icepractice.commands.kitsubcommands.KitHcfCommand;
import de.kontux.icepractice.commands.kitsubcommands.KitListCommand;
import de.kontux.icepractice.commands.kitsubcommands.KitRegenCommand;
import de.kontux.icepractice.commands.kitsubcommands.KitSettingsCommand;
import de.kontux.icepractice.commands.kitsubcommands.KitSpleefCommand;
import de.kontux.icepractice.commands.kitsubcommands.SetInventoryCommand;
import de.kontux.icepractice.commands.kitsubcommands.SetIsComboCommand;
import de.kontux.icepractice.commands.kitsubcommands.SetIsRankedCommand;
import de.kontux.icepractice.commands.kitsubcommands.SetIsSumoCommand;
import de.kontux.icepractice.commands.kitsubcommands.SetKitIconCommand;
import de.kontux.icepractice.commands.kitsubcommands.ViewKitCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class KitCommandExecutor implements CommandExecutor {
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    KitRegenCommand kitRegenCommand = null;
    if (!(sender instanceof Player)) {
      sender.sendMessage("This command can only be used by players!");
      return true;
    } 
    Player player = (Player)sender;
    if (!player.hasPermission("icepractice.kits")) {
      player.sendMessage(ChatColor.RED + "You don't have the permission to access /kit");
      return false;
    } 
    if (args.length == 0) {
      showHelp(player);
      return true;
    } 
    String subCommand = args[0].toLowerCase();
    KitCommand cmd = null;
    if (args.length == 1 && subCommand.equals("list")) {
      KitListCommand kitListCommand = new KitListCommand(player);
      kitListCommand.execute();
    } else if (args.length == 2) {
      CreateKitCommand createKitCommand;
      SetKitIconCommand setKitIconCommand;
      ViewKitCommand viewKitCommand;
      DeleteKitCommand deleteKitCommand;
      KitSettingsCommand kitSettingsCommand;
      SetInventoryCommand setInventoryCommand;
      String kitName = args[1];
      switch (subCommand) {
        case "create":
          createKitCommand = new CreateKitCommand(kitName, player);
          createKitCommand.execute();
          break;
        case "seticon":
          setKitIconCommand = new SetKitIconCommand(kitName, player);
          setKitIconCommand.execute();
          break;
        case "load":
          viewKitCommand = new ViewKitCommand(kitName, player);
          viewKitCommand.execute();
          break;
        case "delete":
          deleteKitCommand = new DeleteKitCommand(kitName, player);
          deleteKitCommand.execute();
          break;
        case "settings":
        case "options":
          kitSettingsCommand = new KitSettingsCommand(kitName, player);
          kitSettingsCommand.execute();
          break;
        case "setinventory":
        case "setinv":
          setInventoryCommand = new SetInventoryCommand(kitName, player);
          setInventoryCommand.execute();
          break;
      } 
    } else if (args.length == 3) {
      SetIsRankedCommand setIsRankedCommand;
      SetIsSumoCommand setIsSumoCommand;
      SetIsComboCommand setIsComboCommand;
      KitHcfCommand kitHcfCommand;
      KitSpleefCommand kitSpleefCommand;
      KitEditableCommand kitEditableCommand;
      KitBuildCommand kitBuildCommand;
      KitChestEditCommand kitChestEditCommand;
      int cooldown;
      String kitName = args[1];
      boolean b = Boolean.parseBoolean(args[2]);
      switch (subCommand) {
        case "isranked":
        case "ranked":
        case "alsoranked":
          setIsRankedCommand = new SetIsRankedCommand(kitName, player, b);
          setIsRankedCommand.execute();
          break;
        case "sumo":
          setIsSumoCommand = new SetIsSumoCommand(kitName, player, b);
          setIsSumoCommand.execute();
          break;
        case "combo":
          setIsComboCommand = new SetIsComboCommand(kitName, player, b);
          setIsComboCommand.execute();
          break;
        case "cooldown":
          cooldown = 0;
          try {
            cooldown = Integer.parseInt(args[2]);
          } catch (NumberFormatException e) {
            player.sendMessage("§c" + args[2] + " is not a valid number.");
          } 
          if (cooldown != 0) {
            CooldownKitCommand cooldownKitCommand = new CooldownKitCommand(player, cooldown, kitName);
            cooldownKitCommand.execute();
          }
          break;
        case "hcf":
          kitHcfCommand = new KitHcfCommand(kitName, player, b);
          kitHcfCommand.execute();
          break;
        case "spleef":
          kitSpleefCommand = new KitSpleefCommand(kitName, player, b);
          kitSpleefCommand.execute();
          break;
        case "editable":
          kitEditableCommand = new KitEditableCommand(kitName, player, b);
          kitEditableCommand.execute();
          break;
        case "build":
          kitBuildCommand = new KitBuildCommand(kitName, player, b);
          kitBuildCommand.execute();
          break;
        case "chest":
          kitChestEditCommand = new KitChestEditCommand(kitName, player, b);
          kitChestEditCommand.execute();
          break;
        case "regen":
          kitRegenCommand = new KitRegenCommand(kitName, player, b);
          kitRegenCommand.execute();
          break;
      } 
    }
    return true;
  }
  
  private void showHelp(Player player) {
    player.sendMessage("§6All /kit commands:");
    player.sendMessage("§6/kit create <name>");
    player.sendMessage("§6/kit setinv <name>");
    player.sendMessage("§6/kit seticon <name>");
    player.sendMessage("§6/kit sumo <name> true/false");
    player.sendMessage("§6/kit combo <name> true/false");
    player.sendMessage("§6/kit build <name> true/false");
    player.sendMessage("§6/kit ranked <name> true/false");
    player.sendMessage("§6/kit hcf <name> true/false");
    player.sendMessage("§6/kit spleef <name> true/false");
    player.sendMessage("§6/kit editable <name> true/false");
    player.sendMessage("§6/kit chest <name> true/false");
    player.sendMessage("§6/kit regen <name> true/false");
    player.sendMessage("§6/kit cooldown <name> <seconds>");
    player.sendMessage("§6/kit delete <name>");
    player.sendMessage("§6/kit list");
  }
}
