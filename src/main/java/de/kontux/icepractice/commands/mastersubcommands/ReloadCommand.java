package de.kontux.icepractice.commands.mastersubcommands;

import de.kontux.icepractice.IcePracticePlugin;
import org.bukkit.command.CommandSender;

public class ReloadCommand implements MasterSubCommand {
  private final CommandSender sender;
  
  public ReloadCommand(CommandSender sender) {
    this.sender = sender;
  }
  
  public void execute() {
    IcePracticePlugin.getInstance().reloadPlugin(this.sender);
  }
}
