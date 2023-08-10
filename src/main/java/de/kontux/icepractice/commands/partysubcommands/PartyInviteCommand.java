package de.kontux.icepractice.commands.partysubcommands;

import de.kontux.icepractice.api.playerstates.PlayerState;
import de.kontux.icepractice.party.Party;
import de.kontux.icepractice.playermanagement.PlayerStates;
import de.kontux.icepractice.registries.PartyRegistry;
import org.bukkit.entity.Player;

public class PartyInviteCommand implements PartyCommand {
  private final Player guest;
  
  private final Player player;
  
  public PartyInviteCommand(Player player, Player guest) {
    this.player = player;
    this.guest = guest;
  }
  
  public void execute() {
    Party party = PartyRegistry.getPartyByPlayer(this.player);
    if (party != null) {
      if (this.guest != null) {
        if (PlayerStates.getInstance().getState(this.guest) == PlayerState.IDLE) {
          party.invitePlayer(this.player, this.guest);
        } else {
          this.player.sendMessage("§cThis player is not at spawn.");
        } 
      } else {
        this.player.sendMessage("Cannot find that player.");
      } 
    } else {
      this.player.sendMessage("§You aren't in a party.");
    } 
  }
}
