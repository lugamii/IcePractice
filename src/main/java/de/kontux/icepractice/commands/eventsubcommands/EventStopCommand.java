package de.kontux.icepractice.commands.eventsubcommands;

import de.kontux.icepractice.registries.EventRegistry;
import de.kontux.icepractice.tournaments.Tournament;
import java.util.List;
import org.bukkit.entity.Player;

public class EventStopCommand implements EventSubcommand {
  private final Player player;
  
  public EventStopCommand(Player player) {
    this.player = player;
  }
  
  public void execute() {
    if (this.player.hasPermission("icepractice.host")) {
      List<Tournament> startingEvents = EventRegistry.getStarting();
      Tournament eventToStop = null;
      for (Tournament event : startingEvents) {
        if (event.getHost().equals(this.player))
          eventToStop = event; 
      } 
      List<Tournament> runningEvents = EventRegistry.getRunning();
      for (Tournament event : runningEvents) {
        if (event.getHost().equals(this.player))
          eventToStop = event; 
      } 
      if (eventToStop != null) {
        eventToStop.endEvent();
      } else {
        this.player.sendMessage("§cYou can only stop events you are hosting yourself.");
      } 
    } else {
      this.player.sendMessage("§cYou don't have the permission to host events!");
    } 
  }
}
