package de.kontux.icepractice.commands.eventsubcommands;

import de.kontux.icepractice.api.playerstates.PlayerState;
import de.kontux.icepractice.guis.EventJoinInventory;
import de.kontux.icepractice.playermanagement.PlayerStates;
import de.kontux.icepractice.registries.EventRegistry;
import de.kontux.icepractice.tournaments.Tournament;
import org.bukkit.entity.Player;

public class EventJoinCommand implements EventSubcommand {
  private final Player player;
  
  private final String[] args;
  
  public EventJoinCommand(Player player, String[] args) {
    this.player = player;
    this.args = args;
  }
  
  public void execute() {
    if (PlayerStates.getInstance().getState(this.player) != PlayerState.IDLE) {
      this.player.sendMessage("§cYou must be at spawn to join an event.");
      return;
    } 
    if (this.args.length == 1) {
      (new EventJoinInventory(this.player)).openMenu();
    } else if (this.args.length == 2) {
      int eventId = Integer.parseInt(this.args[1]);
      Tournament eventToJoin = EventRegistry.getById(eventId);
      if (eventToJoin != null) {
        eventToJoin.join(this.player);
      } else {
        this.player.sendMessage("§cThere is currently no event with the id " + eventId + ".");
      } 
    } 
  }
}
