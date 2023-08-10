package de.kontux.icepractice.commands.partysubcommands;

import de.kontux.icepractice.party.Party;
import de.kontux.icepractice.registries.PartyRegistry;
import org.bukkit.entity.Player;

public class PartyKickCommand implements PartyCommand {
  private final Player player;
  
  private final Player playerToKick;
  
  public PartyKickCommand(Player player, Player playerToKick) {
    this.player = player;
    this.playerToKick = playerToKick;
  }
  
  public void execute() {
    Party party = PartyRegistry.getPartyByPlayer(this.player);
    if (party != null) {
      party.kickPlayer(this.player, this.playerToKick);
    } else {
      this.player.sendMessage("§You aren't in a party.");
    } 
  }
}
