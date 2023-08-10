package de.kontux.icepractice.guis.party;

import de.kontux.icepractice.api.gui.KitSelectionInventory;
import de.kontux.icepractice.api.kit.IcePracticeKit;
import de.kontux.icepractice.party.Party;
import de.kontux.icepractice.party.PartyEvent;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class PartyEventKitSelectionInventory extends KitSelectionInventory {
  private final Party party;
  
  private final PartyEvent event;
  
  public PartyEventKitSelectionInventory(Player player, Party party, PartyEvent event) {
    super(player);
    this.party = party;
    this.event = event;
  }
  
  public void runAction(ItemStack item) {
    IcePracticeKit kit = getKitByItemName(item.getItemMeta().getDisplayName());
    if (kit != null) {
      switch (this.event) {
        case FFA:
          this.party.startFFAMatch(kit);
          break;
        case SPLIT_FIGHT:
          this.party.startSplitMatch(kit);
          break;
        case RED_ROVER:
          this.party.startRedRover(kit);
          break;
      } 
      this.player.closeInventory();
    } 
  }
}
