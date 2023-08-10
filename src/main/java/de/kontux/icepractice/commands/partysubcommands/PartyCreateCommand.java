package de.kontux.icepractice.commands.partysubcommands;

import de.kontux.icepractice.party.Party;
import de.kontux.icepractice.registries.PartyRegistry;
import org.bukkit.entity.Player;

public class PartyCreateCommand implements PartyCommand {
  private final Player player;
  
  public PartyCreateCommand(Player player) {
    this.player = player;
  }
  
  public void execute() {
    if (PartyRegistry.isInParty(this.player)) {
      this.player.sendMessage("You are already in a party.");
    } else {
      (new Party(this.player)).createParty();
    } 
  }
}
