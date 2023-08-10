package de.kontux.icepractice.commands.partysubcommands;

import de.kontux.icepractice.party.Party;
import de.kontux.icepractice.registries.PartyRegistry;
import org.bukkit.entity.Player;

public class PartyInfoCommand implements PartyCommand {
  private final Player player;
  
  public PartyInfoCommand(Player player) {
    this.player = player;
  }
  
  public void execute() {
    Party party = PartyRegistry.getPartyByPlayer(this.player);
    if (party != null) {
      party.showInfo(this.player);
    } else {
      this.player.sendMessage("§cYou aren't in a party.");
    } 
  }
}
