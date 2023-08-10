package de.kontux.icepractice.api.gui;

import de.kontux.icepractice.api.IcePracticeAPI;
import de.kontux.icepractice.api.kit.IcePracticeKit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public abstract class KitSelectionInventory extends InventoryGui {
  public KitSelectionInventory(Player player) {
    super(player, IcePracticeAPI.getPrimary() + "Select a kit", 18);
  }
  
  public KitSelectionInventory(Player player, String title) {
    super(player, title, 18);
  }
  
  protected void setItems() {
    for (IcePracticeKit kit : IcePracticeAPI.getKitHandler().getKits()) {
      this.inventory.addItem(new ItemStack[] { kit.getIcon() });
    } 
  }
  
  protected final IcePracticeKit getKitByItemName(String name) {
    name = name.replace(IcePracticeAPI.getSecondary().toString(), "");
    return IcePracticeAPI.getKit(name);
  }
}
