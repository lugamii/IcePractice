package de.kontux.icepractice.items.join;

import org.bukkit.inventory.ItemStack;

public class JoinItem {
  private final ItemStack item;
  
  private final JoinItemFunction function;
  
  public JoinItem(ItemStack item, JoinItemFunction function) {
    this.item = item;
    this.function = function;
    this.item.getItemMeta().spigot().setUnbreakable(true);
  }
  
  public ItemStack getItem() {
    return this.item;
  }
  
  public JoinItemFunction getFunction() {
    return this.function;
  }
}
