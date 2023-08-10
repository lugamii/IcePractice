package de.kontux.icepractice.commands;

import de.kontux.icepractice.IcePracticePlugin;
import de.kontux.icepractice.commands.mastersubcommands.MasterSubCommand;
import de.kontux.icepractice.commands.mastersubcommands.ReloadCommand;
import de.kontux.icepractice.commands.mastersubcommands.ResetCommand;
import de.kontux.icepractice.commands.mastersubcommands.SetSpawnCommand;
import de.kontux.icepractice.commands.mastersubcommands.UpdateCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class IcePracticeCommandExecutor implements CommandExecutor {
  private final IcePracticePlugin plugin;
  
  public IcePracticeCommandExecutor(IcePracticePlugin plugin) {
    this.plugin = plugin;
  }
  
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    if (!sender.hasPermission("icepractice.admin")) {
      sender.sendMessage("§cYou don't have the permission to access this command!");
      return false;
    } 
    if (sender instanceof Player) {
      Player player = (Player)sender;
      if (args.length > 0) {
        UpdateCommand updateCommand = new UpdateCommand(player);
        String subcommand = args[0];
        MasterSubCommand masterSubCommand;
        if (subcommand.equalsIgnoreCase("reload") || subcommand.equalsIgnoreCase("rl")) {
          ReloadCommand reloadCommand = new ReloadCommand(sender);
          reloadCommand.execute();
        } else if (subcommand.equalsIgnoreCase("setspawn") || subcommand.equalsIgnoreCase("spawn")) {
          SetSpawnCommand setSpawnCommand = new SetSpawnCommand(player);
          setSpawnCommand.execute();
        } else if (subcommand.equals("reset") && args.length > 1) {
          ResetCommand resetCommand = new ResetCommand((Plugin)this.plugin, sender, args[1]);
          resetCommand.execute();
        } else if (subcommand.equalsIgnoreCase("update") || subcommand.equalsIgnoreCase("checkupdate")) {
          updateCommand = new UpdateCommand(player);
          updateCommand.execute();
        } else {
          showHelp(sender);
        }
        if (updateCommand != null) {
          updateCommand.execute();
        }
      } else {
        showHelp(sender);
      }
    } 
    return true;
  }
  
  private void showHelp(CommandSender sender) {
    sender.sendMessage("§c/icepractice setspawn");
    sender.sendMessage("§c/icepractice reload");
    sender.sendMessage("§c/icepractice update");
    sender.sendMessage("§c/icepractice reset <filename>");
  }
}
