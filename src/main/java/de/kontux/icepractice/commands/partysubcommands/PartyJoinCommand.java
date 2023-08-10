package de.kontux.icepractice.commands.partysubcommands;

import de.kontux.icepractice.party.Party;
import de.kontux.icepractice.registries.PartyRegistry;
import org.bukkit.entity.Player;

public class PartyJoinCommand implements PartyCommand {
  private final Player player;
  
  private final Player leader;
  
  public PartyJoinCommand(Player player, Player leader) {
    this.player = player;
    this.leader = leader;
  }
  
  public void execute() {
    if (this.leader == null) {
      this.player.sendMessage("§cCannot find player.");
      return;
    } 
    if (PartyRegistry.isInParty(this.player)) {
      this.player.sendMessage("§cYou are already in a party.");
    } else {
      Party party = PartyRegistry.getPartyByPlayer(this.leader);
      if (party != null) {
        party.joinPlayer(this.player);
      } else {
        this.player.sendMessage("§cThis player is not hosting a party at the moment.");
      } 
    } 
  }
}
