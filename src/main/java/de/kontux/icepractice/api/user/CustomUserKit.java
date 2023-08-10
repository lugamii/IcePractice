package de.kontux.icepractice.api.user;

import de.kontux.icepractice.api.kit.IcePracticeKit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface CustomUserKit {
  String getCustomName();
  
  void equip(Player paramPlayer);
  
  ItemStack[] getInventory();
  
  void setInventory(ItemStack[] paramArrayOfItemStack);
  
  int getNumber();
  
  IcePracticeKit getKit();
  
  void setCustomName();
}
