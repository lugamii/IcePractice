package de.kontux.icepractice.guis;

import de.kontux.icepractice.api.gui.InventoryGui;
import de.kontux.icepractice.configs.Settings;
import de.kontux.icepractice.postiventories.AfterMatchInventory;
import de.kontux.icepractice.registries.InventoryRegistry;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class InventoryViewInventory extends InventoryGui {
  private final AfterMatchInventory matchInventory;
  
  public InventoryViewInventory(Player player, AfterMatchInventory matchInventory) {
    super(player, Settings.PRIMARY + matchInventory.getTarget().getDisplayName() + "'s inventory", 45);
    this.matchInventory = matchInventory;
  }
  
  protected void setItems() {
    this.inventory.setContents(this.matchInventory.getContents());
  }
  
  public void runAction(ItemStack item) {
    if (!item.hasItemMeta())
      return; 
    String name = item.getItemMeta().getDisplayName();
    if (item.getType() == Material.ARROW && name != null && name.contains("'s Inventory")) {
      Player next = this.matchInventory.getNext();
      AfterMatchInventory nextInventory = InventoryRegistry.getInventory(next.getUniqueId());
      if (nextInventory != null) {
        (new InventoryViewInventory(this.player, nextInventory)).openMenu();
      } else {
        this.player.sendMessage(ChatColor.RED + "No inventory of this player found.");
      } 
    } 
  }
}
