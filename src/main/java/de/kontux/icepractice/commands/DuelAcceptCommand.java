package de.kontux.icepractice.commands;

import de.kontux.icepractice.api.kit.IcePracticeKit;
import de.kontux.icepractice.configs.repositories.messages.BasicMessageRepository;
import de.kontux.icepractice.match.types.Duel;
import de.kontux.icepractice.registries.DuelRequestRegistry;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DuelAcceptCommand implements CommandExecutor {
  private final BasicMessageRepository messages = new BasicMessageRepository();
  
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    if (sender instanceof Player) {
      if (args.length == 1) {
        Player player = (Player)sender;
        Player challenger = Bukkit.getServer().getPlayer(args[0]);
        if (challenger != null) {
          if (DuelRequestRegistry.hasSentRequest(challenger, player)) {
            IcePracticeKit kit = DuelRequestRegistry.getKit(challenger, player);
            DuelRequestRegistry.removeRequest(challenger, player);
            challenger.sendMessage(this.messages.getAcceptedRequest(player));
            callMatch(challenger, player, kit);
          } else {
            player.sendMessage(this.messages.getHasNotSentRequest(args[0]));
          } 
        } else {
          player.sendMessage("§cPlayer not found.");
        } 
      } else {
        sender.sendMessage("§cYou need to specify the player.");
      } 
    } else {
      sender.sendMessage("§cYou must be a player to use this command!");
    } 
    return true;
  }
  
  private void callMatch(Player player1, Player player2, IcePracticeKit kit) {
    (new Duel(player1, player2, kit, false)).runMatch();
  }
}
