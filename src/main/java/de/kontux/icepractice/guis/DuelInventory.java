package de.kontux.icepractice.guis;

import de.kontux.icepractice.api.gui.KitSelectionInventory;
import de.kontux.icepractice.api.kit.IcePracticeKit;
import de.kontux.icepractice.api.playerstates.PlayerState;
import de.kontux.icepractice.kits.KitManager;
import de.kontux.icepractice.playermanagement.PlayerStates;
import de.kontux.icepractice.registries.DuelRequestRegistry;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class DuelInventory extends KitSelectionInventory {
  private final Player opponent;
  
  public DuelInventory(Player player, Player opponent) {
    super(player);
    this.opponent = opponent;
  }
  
  protected void setItems() {
    for (IcePracticeKit kit : KitManager.getInstance().getKits()) {
      this.inventory.addItem(new ItemStack[] { kit.getIcon() });
    } 
  }
  
  public void runAction(ItemStack item) {
    if (PlayerStates.getInstance().getState(this.opponent) == PlayerState.IDLE) {
      IcePracticeKit kit = getKitByItemName(item.getItemMeta().getDisplayName());
      if (kit != null)
        DuelRequestRegistry.sendRequest(kit, this.player, this.opponent); 
    } else {
      this.player.sendMessage(ChatColor.RED + "This player is not at spawn any more.");
    } 
    this.player.closeInventory();
  }
}
