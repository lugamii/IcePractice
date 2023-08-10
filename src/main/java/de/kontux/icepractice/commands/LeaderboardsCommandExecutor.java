package de.kontux.icepractice.commands;

import de.kontux.icepractice.IcePracticePlugin;
import de.kontux.icepractice.api.kit.IcePracticeKit;
import de.kontux.icepractice.configs.Settings;
import de.kontux.icepractice.kits.KitManager;
import de.kontux.icepractice.userdata.Leaderboards;
import de.kontux.icepractice.userdata.OfflinePlayerData;
import java.util.Collection;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class LeaderboardsCommandExecutor implements CommandExecutor {
  private final IcePracticePlugin plugin;
  
  public LeaderboardsCommandExecutor(IcePracticePlugin plugin) {
    this.plugin = plugin;
  }
  
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    if (!(sender instanceof Player)) {
      sender.sendMessage("Only players can use this command!");
      return true;
    } 
    Player player = (Player)sender;
    if (args.length == 0) {
      player.sendMessage(ChatColor.RED + "/leaderboards <kit>");
      return true;
    } 
    IcePracticeKit kit = KitManager.getInstance().getKit(args[0]);
    if (kit != null) {
      showLeaderboards(player, kit);
    } else {
      player.sendMessage(ChatColor.RED + "This kti doesn't exist!");
    } 
    return true;
  }
  
  private void showLeaderboards(Player player, IcePracticeKit kit) {
    Bukkit.getScheduler().runTaskAsynchronously((Plugin)this.plugin, () -> {
          Collection<OfflinePlayerData> leaderboards = (new Leaderboards()).calculateLeaderboards(kit, 10);
          Bukkit.getScheduler().runTask((Plugin)this.plugin, (Runnable) this);
        });
  }
}
