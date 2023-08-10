package de.kontux.icepractice.api.kit;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface IcePracticeKit {
  void setInventory(ItemStack[] paramArrayOfItemStack1, ItemStack[] paramArrayOfItemStack2);
  
  void save();
  
  void delete();
  
  void equipKit(Player paramPlayer);
  
  int getPearlCooldown();
  
  void setPearlCooldown(int paramInt);
  
  String getName();
  
  ItemStack[] getItems();
  
  ItemStack[] getArmor();
  
  ItemStack getIcon();
  
  void setIcon(ItemStack paramItemStack);
  
  boolean isRanked();
  
  void setRanked(boolean paramBoolean);
  
  boolean isSumo();
  
  void setSumo(boolean paramBoolean);
  
  boolean isCombo();
  
  void setCombo(boolean paramBoolean);
  
  String toString();
  
  void setAllowBuild(boolean paramBoolean);
  
  boolean isBuild();
  
  boolean isHcf();
  
  void setHcf(boolean paramBoolean);
  
  boolean isSpleef();
  
  void setSpleef(boolean paramBoolean);
  
  boolean isEditable();
  
  void setEditable(boolean paramBoolean);
  
  boolean allowRegen();
  
  void setAllowRegen(boolean paramBoolean);
  
  boolean allowChestEditing();
  
  void setAllowChestEditing(boolean paramBoolean);
}
