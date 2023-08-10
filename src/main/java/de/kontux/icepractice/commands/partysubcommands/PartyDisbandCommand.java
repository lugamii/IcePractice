package de.kontux.icepractice.commands.partysubcommands;

import de.kontux.icepractice.party.Party;
import de.kontux.icepractice.registries.PartyRegistry;
import org.bukkit.entity.Player;

public class PartyDisbandCommand implements PartyCommand {
  private final Player player;
  
  public PartyDisbandCommand(Player player) {
    this.player = player;
  }
  
  public void execute() {
    Party party = PartyRegistry.getPartyByPlayer(this.player);
    if (party != null) {
      if (this.player.equals(party.getLeader())) {
        party.disband();
      } else {
        this.player.sendMessage("§cOnly the party's leader can disband the party.");
      } 
    } else {
      this.player.sendMessage("§cYou aren't in a party.");
    } 
  }
}
