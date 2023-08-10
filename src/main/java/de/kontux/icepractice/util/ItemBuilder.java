package de.kontux.icepractice.util;

import java.util.List;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemBuilder {
  public static ItemStack create(Material material, String name, List<String> lore) {
    ItemStack item = new ItemStack(material);
    ItemMeta itemMeta = item.getItemMeta();
    if (name != null)
      itemMeta.setDisplayName(name); 
    if (lore != null)
      itemMeta.setLore(lore); 
    item.setItemMeta(itemMeta);
    return item;
  }
  
  public static ItemStack create(Material material, String name, short durability, List<String> lore) {
    ItemStack item = new ItemStack(material);
    item.setDurability(durability);
    ItemMeta itemMeta = item.getItemMeta();
    if (name != null)
      itemMeta.setDisplayName(name); 
    if (lore != null)
      itemMeta.setLore(lore); 
    item.setItemMeta(itemMeta);
    return item;
  }
  
  public static ItemStack create(Material material, String name, List<String> lore, int amount, short damage) {
    ItemStack item = new ItemStack(material, amount, damage);
    ItemMeta itemMeta = item.getItemMeta();
    if (name != null)
      itemMeta.setDisplayName(name); 
    if (lore != null)
      itemMeta.setLore(lore); 
    item.setItemMeta(itemMeta);
    return item;
  }
}
