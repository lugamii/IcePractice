package de.kontux.icepractice.commands.eventsubcommands;

import de.kontux.icepractice.registries.EventRegistry;
import de.kontux.icepractice.tournaments.Tournament;
import java.util.List;
import org.bukkit.entity.Player;

public class EventStartCommand implements EventSubcommand {
  private final Player player;
  
  public EventStartCommand(Player player) {
    this.player = player;
  }
  
  public void execute() {
    if (this.player.hasPermission("icepractice.host")) {
      List<Tournament> startingEvents = EventRegistry.getStarting();
      Tournament ownedEvent = null;
      for (Tournament event : startingEvents) {
        if (event.getHost().equals(this.player))
          ownedEvent = event; 
      } 
      if (ownedEvent != null) {
        ownedEvent.startMatchCircuit();
      } else {
        this.player.sendMessage("§cYou can only expireCooldown events you are hosting yourself.");
      } 
    } else {
      this.player.sendMessage("§cYou don't have the permission to host events!");
    } 
  }
}
