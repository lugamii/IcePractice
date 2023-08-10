package de.kontux.icepractice.commands.mastersubcommands;

import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

public class ResetCommand implements MasterSubCommand {
  private final Plugin plugin;
  
  private final CommandSender sender;
  
  private final String file;
  
  public ResetCommand(Plugin plugin, CommandSender sender, String file) {
    this.plugin = plugin;
    this.sender = sender;
    this.file = file;
  }
  
  public void execute() {
    switch (this.file) {
      case "config":
        this.plugin.saveResource("config.yml", true);
        this.sender.sendMessage("The file CONFIG.YML was reset. Please restart/reload your server to apply the changes.");
        return;
      case "kit":
      case "kits":
        this.plugin.saveResource("kits.yml", true);
        this.sender.sendMessage("The file KITS.YML was reset. Please restart/reload your server to apply the changes.");
        return;
      case "arena":
      case "arenas":
        this.plugin.saveResource("arena.yml", true);
        this.sender.sendMessage("The file ARENA.YML was reset. Please restart/reload your server to apply the changes.");
        return;
      case "joinitems":
        this.plugin.saveResource("joinitems.yml", true);
        this.sender.sendMessage("The file JOINITEMS.YML was reset. Please restart/reload your server to apply the changes.");
        return;
      case "messages":
        this.plugin.saveResource("messages.yml", true);
        this.sender.sendMessage("The file MESSAGES.YML was reset. Please restart/reload your server to apply the changes.");
        return;
      case "playerdata":
        this.plugin.saveResource("playerdata.yml", true);
        this.sender.sendMessage("The file PLAYERDATA.YML was reset. Please restart/reload your server to apply the changes.");
        return;
    } 
    showHelp();
  }
  
  private void showHelp() {
    this.sender.sendMessage("Usage: /iprac reset <filename>");
    this.sender.sendMessage("Available files:");
    this.sender.sendMessage("- config");
    this.sender.sendMessage("- kits");
    this.sender.sendMessage("- arena");
    this.sender.sendMessage("- messages");
    this.sender.sendMessage("- joinitems");
    this.sender.sendMessage("- playerdata");
  }
}
