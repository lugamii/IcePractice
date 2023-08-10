package de.kontux.icepractice.guis.party;

import de.kontux.icepractice.api.gui.KitSelectionInventory;
import de.kontux.icepractice.api.kit.IcePracticeKit;
import de.kontux.icepractice.party.Party;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class PartyDuelKitSelectionInventory extends KitSelectionInventory {
  private final Party party;
  
  private final Party opponentParty;
  
  public PartyDuelKitSelectionInventory(Player player, Party party, Party opponentParty) {
    super(player);
    this.party = party;
    this.opponentParty = opponentParty;
  }
  
  public void runAction(ItemStack item) {
    IcePracticeKit kit = getKitByItemName(item.getItemMeta().getDisplayName());
    if (kit != null)
      this.party.sendDuelRequest(this.opponentParty, kit); 
  }
}
