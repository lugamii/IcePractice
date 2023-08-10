package de.kontux.icepractice.tournaments.types.solo;

import de.kontux.icepractice.IcePracticePlugin;
import de.kontux.icepractice.api.kit.IcePracticeKit;
import de.kontux.icepractice.registries.EventRegistry;
import de.kontux.icepractice.tournaments.EventType;
import de.kontux.icepractice.tournaments.Tournament;
import org.bukkit.entity.Player;

public abstract class SoloTournament extends Tournament {
  public SoloTournament(String eventName, int eventId, Player host, String password, EventType type, IcePracticeKit kit) {
    super(eventName, eventId, host, password, type, kit);
  }
  
  public void startMatchCircuit() {
    if (this.participants.size() < 2) {
      for (Player player : this.participants)
        player.sendMessage(this.messages.getNotEnoughPlayersMessage()); 
      endEvent();
      return;
    } 
    EventRegistry.removeStartingEvent(this);
    EventRegistry.addRunningEvent(this);
    this.broadcast.stopBroadcast();
    IcePracticePlugin.broadCastMessage(this.participants, this.messages.getStartMessage());
    startNextFights();
  }
}
