package de.kontux.icepractice.registries;

import de.kontux.icepractice.tournaments.Tournament;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.entity.Player;

public class EventRegistry {
  private static final List<Tournament> STARTING = new ArrayList<>();
  
  private static final List<Tournament> RUNNING = new ArrayList<>();
  
  public static void addStartingEvent(Tournament event) {
    STARTING.add(event);
  }
  
  public static void removeStartingEvent(Tournament event) {
    STARTING.remove(event);
  }
  
  public static void addRunningEvent(Tournament event) {
    RUNNING.add(event);
  }
  
  public static void removeRunningEvent(Tournament event) {
    RUNNING.remove(event);
  }
  
  public static Tournament getById(int id) {
    for (Tournament event : STARTING) {
      if (event.getId() == id)
        return event; 
    } 
    return null;
  }
  
  public static Tournament getEventByPlayer(Player player) {
    for (Tournament current : STARTING) {
      if (current.getParticipants().contains(player))
        return current; 
    } 
    for (Tournament current : RUNNING) {
      if (current.getParticipants().contains(player))
        return current; 
    } 
    return null;
  }
  
  public static List<Tournament> getRunning() {
    return RUNNING;
  }
  
  public static List<Tournament> getStarting() {
    return STARTING;
  }
}
