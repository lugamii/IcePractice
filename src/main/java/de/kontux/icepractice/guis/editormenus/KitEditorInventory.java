package de.kontux.icepractice.guis.editormenus;

import de.kontux.icepractice.api.gui.KitSelectionInventory;
import de.kontux.icepractice.api.kit.IcePracticeKit;
import de.kontux.icepractice.configs.Settings;
import de.kontux.icepractice.kiteditor.KitEditorHandler;
import de.kontux.icepractice.kits.KitManager;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class KitEditorInventory extends KitSelectionInventory {
  public KitEditorInventory(Player player) {
    super(player, Settings.PRIMARY + "Select a kit to edit:");
  }
  
  protected void setItems() {
    for (IcePracticeKit kit : KitManager.getInstance().getKits()) {
      if (!kit.isSumo() && kit.isEditable())
        this.inventory.addItem(new ItemStack[] { kit.getIcon() }); 
    } 
  }
  
  public void runAction(ItemStack item) {
    if (!item.hasItemMeta())
      return; 
    String name = item.getItemMeta().getDisplayName();
    if (name == null)
      return; 
    IcePracticeKit kit = getKitByItemName(item.getItemMeta().getDisplayName());
    if (kit != null) {
      (new KitEditorHandler(this.player, kit)).teleportPlayer();
      this.player.closeInventory();
    } 
  }
}
