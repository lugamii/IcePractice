package de.kontux.icepractice.items.party;

import org.bukkit.inventory.ItemStack;

public class PartyItem {
  private final ItemStack item;
  
  private final PartyItemFunction function;
  
  public PartyItem(ItemStack item, PartyItemFunction function) {
    this.item = item;
    this.function = function;
    this.item.getItemMeta().spigot().setUnbreakable(true);
  }
  
  public ItemStack getItem() {
    return this.item;
  }
  
  public PartyItemFunction getFunction() {
    return this.function;
  }
}
