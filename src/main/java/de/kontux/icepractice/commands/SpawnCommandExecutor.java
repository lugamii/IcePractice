package de.kontux.icepractice.commands;

import de.kontux.icepractice.api.playerstates.PlayerState;
import de.kontux.icepractice.locations.SpawnPointHandler;
import de.kontux.icepractice.playermanagement.PlayerStates;
import de.kontux.icepractice.registries.PartyRegistry;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpawnCommandExecutor implements CommandExecutor {
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    if (sender instanceof Player) {
      Player player = (Player)sender;
      if (PlayerStates.getInstance().getState(player) == PlayerState.IDLE) {
        if (PartyRegistry.isInParty(player)) {
          PartyRegistry.getPartyByPlayer(player).teleportToSpawn(player);
        } else {
          SpawnPointHandler.teleportToSpawn(player);
        } 
      } else {
        player.sendMessage("§cYou must be in idle to teleport to spawn.");
      } 
    } 
    return true;
  }
}
