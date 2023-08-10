package de.kontux.icepractice.queue;

import de.kontux.icepractice.IcePracticePlugin;
import de.kontux.icepractice.api.kit.IcePracticeKit;
import de.kontux.icepractice.configs.Settings;
import de.kontux.icepractice.configs.repositories.messages.QueueMessageRepository;
import de.kontux.icepractice.kits.Kit;
import de.kontux.icepractice.match.types.TeamFight;
import de.kontux.icepractice.party.Party;
import de.kontux.icepractice.util.ItemBuilder;
import java.util.HashMap;
import org.bukkit.inventory.ItemStack;

public class PartyQueue {
  public static final ItemStack PARTY_QUEUE_ITEM = ItemBuilder.create(Settings.QUEUE_LEAVE_ITEM, Settings.QUEUE_LEAVE_ITEM_NAME, null);
  
  private static final HashMap<IcePracticeKit, Party> PARTY_QUEUE = new HashMap<>();
  
  public static void addToQueue(Party party, IcePracticeKit kit) {
    Party opponentParty = getParty(kit);
    if (opponentParty != null) {
      removeFromQueue(opponentParty);
      (new TeamFight(party.getMembers(), opponentParty.getMembers(), kit)).runMatch();
    } else {
      PARTY_QUEUE.put(kit, party);
      IcePracticePlugin.broadCastMessage(party.getMembers(), (new QueueMessageRepository()).getPartyJoinMessage(kit.getName()));
    } 
  }
  
  public static void removeFromQueue(Party party) {
    IcePracticeKit kitToRemove = null;
    for (IcePracticeKit kit : PARTY_QUEUE.keySet()) {
      if (((Party)PARTY_QUEUE.get(kit)).equals(party))
        kitToRemove = kit; 
    } 
    if (kitToRemove != null) {
      PARTY_QUEUE.remove(kitToRemove);
      IcePracticePlugin.broadCastMessage(party.getMembers(), (new QueueMessageRepository()).getLeaveMessage());
    } else {
      IcePracticePlugin.broadCastMessage(party.getMembers(), "§cError while leaving the queue.");
    } 
  }
  
  public static int getPlayersInQueue(Kit kit) {
    int playersInQueue = 0;
    for (IcePracticeKit currentKit : PARTY_QUEUE.keySet()) {
      if (currentKit.equals(kit))
        playersInQueue++; 
    } 
    return playersInQueue;
  }
  
  public static Party getParty(IcePracticeKit kit) {
    return PARTY_QUEUE.get(kit);
  }
}
