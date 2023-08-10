package de.kontux.icepractice.tournaments.types.solo;

import de.kontux.icepractice.api.kit.IcePracticeKit;
import de.kontux.icepractice.kits.Kit;
import de.kontux.icepractice.tournaments.EventType;
import org.bukkit.entity.Player;

public class KitSoloTournament extends SoloTournament {
  public KitSoloTournament(int eventId, Player host, String password, Kit kit) {
    super(kit.getName() + " Tournament (1vs1)", eventId, host, password, EventType.TOURNAMENT, (IcePracticeKit)kit);
  }
  
  public void hostEvent() {}
  
  public void startNextFights() {}
  
  protected void onMatchEnd() {}
  
  protected void teleportToSpawn(Player player) {}
  
  protected void changeInventory(Player player) {}
}
