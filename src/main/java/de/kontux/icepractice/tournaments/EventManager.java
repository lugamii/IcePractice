package de.kontux.icepractice.tournaments;

import de.kontux.icepractice.api.kit.IcePracticeKit;
import de.kontux.icepractice.guis.eventsettings.EventSettingsInventory;
import de.kontux.icepractice.guis.eventsettings.KothSettingsInventory;
import de.kontux.icepractice.guis.eventsettings.SumoSettingsInventory;
import de.kontux.icepractice.guis.eventsettings.TournamentSettingsInventory;
import de.kontux.icepractice.registries.EventRegistry;
import de.kontux.icepractice.tournaments.types.solo.SumoSoloEvent;
import de.kontux.icepractice.tournaments.types.team.SumoTeamTournament;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.entity.Player;

public class EventManager {
  public void openSettings(EventType eventType, Player player) {
    SumoSettingsInventory sumoSettingsInventory;
    KothSettingsInventory kothSettingsInventory;
    TournamentSettingsInventory tournamentSettingsInventory = null;
    EventSettingsInventory settingsInventory = null;
    switch (eventType) {
      case SUMO:
        sumoSettingsInventory = new SumoSettingsInventory(player);
        break;
      case KOTH:
        kothSettingsInventory = new KothSettingsInventory(player);
        break;
      case TOURNAMENT:
        tournamentSettingsInventory = new TournamentSettingsInventory(player);
        break;
    } 
    tournamentSettingsInventory.openMenu();
  }
  
  public void hostEvent(Player host, EventType eventType, String password, int teamSize, IcePracticeKit kit) {
    SumoTeamTournament sumoTeamTournament = null;
    if (kit == null || eventType == null || host == null)
      throw new IllegalArgumentException("Only the password may be null!"); 
    Tournament event = null;
    switch (eventType) {
      case SUMO:
        if (teamSize == 1) {
          SumoSoloEvent sumoSoloEvent = new SumoSoloEvent(determineEventId(), host, password, kit);
          break;
        } 
        if (teamSize > 1)
          sumoTeamTournament = new SumoTeamTournament(determineEventId(), host, password, teamSize, kit); 
        break;
    } 
    if (sumoTeamTournament != null)
      sumoTeamTournament.hostEvent(); 
  }
  
  private int determineEventId() {
    List<Integer> takenIds = new ArrayList<>();
    for (Tournament event : EventRegistry.getStarting())
      takenIds.add(Integer.valueOf(event.getId())); 
    for (Tournament event : EventRegistry.getRunning())
      takenIds.add(Integer.valueOf(event.getId())); 
    int chosenId = 1;
    while (takenIds.contains(Integer.valueOf(chosenId)))
      chosenId++; 
    return chosenId;
  }
}
