package de.kontux.icepractice.commands.partysubcommands;

import de.kontux.icepractice.party.Party;
import de.kontux.icepractice.registries.PartyRegistry;
import org.bukkit.entity.Player;

public class PartyCloseCommand implements PartyCommand {
  private final Player player;
  
  public PartyCloseCommand(Player player) {
    this.player = player;
  }
  
  public void execute() {
    Party party = PartyRegistry.getPartyByPlayer(this.player);
    if (party != null) {
      party.setOpen(this.player, false);
    } else {
      this.player.sendMessage("§cYou aren't in a party.");
    } 
  }
}
