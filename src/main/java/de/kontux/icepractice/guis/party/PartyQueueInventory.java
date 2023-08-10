package de.kontux.icepractice.guis.party;

import de.kontux.icepractice.api.gui.KitSelectionInventory;
import de.kontux.icepractice.api.kit.IcePracticeKit;
import de.kontux.icepractice.configs.Settings;
import de.kontux.icepractice.party.Party;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class PartyQueueInventory extends KitSelectionInventory {
  private final Party party;
  
  public PartyQueueInventory(Player player, Party party) {
    super(player, Settings.PRIMARY + "Party Queue:");
    this.party = party;
  }
  
  public void runAction(ItemStack item) {
    IcePracticeKit kit = getKitByItemName(item.getItemMeta().getDisplayName());
    if (kit != null) {
      this.party.joinQueue(kit);
      this.player.closeInventory();
    } 
  }
}
