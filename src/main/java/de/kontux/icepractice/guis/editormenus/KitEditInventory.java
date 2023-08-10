package de.kontux.icepractice.guis.editormenus;

import de.kontux.icepractice.api.kit.IcePracticeKit;
import de.kontux.icepractice.configs.Settings;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class KitEditInventory {
  private final Player player;
  
  private final IcePracticeKit kit;
  
  private final String TITLE = Settings.PRIMARY + "Edit the kit:";
  
  private final Inventory inventory = Bukkit.createInventory(null, 36, this.TITLE);
  
  public KitEditInventory(Player player, IcePracticeKit kit) {
    this.player = player;
    this.kit = kit;
  }
  
  public void openMenu() {
    setItems();
    this.player.openInventory(this.inventory);
  }
  
  private void setItems() {
    ItemStack[] items = this.kit.getItems();
    for (int i = 0; i < 36; i++)
      this.inventory.setItem(i, items[i]); 
  }
}
