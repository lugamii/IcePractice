package de.kontux.icepractice.registries;

import de.kontux.icepractice.party.Party;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.entity.Player;

public class PartyRegistry {
  private static final List<Party> ALL_PARTIES = new ArrayList<>();
  
  public static void addParty(Party party) {
    ALL_PARTIES.add(party);
  }
  
  public static void removeParty(Party party) {
    ALL_PARTIES.remove(party);
  }
  
  public static List<Party> getAllParties() {
    return ALL_PARTIES;
  }
  
  public static Party getPartyByPlayer(Player player) {
    if (player == null)
      return null; 
    for (Party current : ALL_PARTIES) {
      if (current.getMembers().contains(player))
        return current; 
    } 
    return null;
  }
  
  public static boolean isInParty(Player player) {
    for (Party current : ALL_PARTIES) {
      if (current.getMembers().contains(player))
        return true; 
    } 
    return false;
  }
}
