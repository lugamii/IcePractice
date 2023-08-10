package de.kontux.icepractice.items;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;

public class ItemLoader {
  public void save(ItemStack item, FileConfiguration config, String path) {
    if (!item.hasItemMeta())
      throw new IllegalArgumentException("The item must have an item meta!"); 
    HashMap<String, Object> values = new HashMap<>();
    ItemMeta meta = item.getItemMeta();
    values.put("material", item.getType().name());
    values.put("name", meta.getDisplayName());
    values.put("lore", meta.getLore());
    values.put("amount", Integer.valueOf(item.getAmount()));
    values.put("durability", Short.valueOf(item.getDurability()));
    values.put("enchants", getEnchants(item));
    if (meta instanceof PotionMeta) {
      PotionMeta potionMeta = (PotionMeta)meta;
      values.put("effects", getCustomEffects(potionMeta));
    } 
    saveMap(config, path, values);
  }
  
  private void saveMap(FileConfiguration config, String path, Map<String, Object> values) {
    ConfigurationSection section = config.isConfigurationSection(path) ? config.getConfigurationSection(path) : config.createSection(path);
    for (String key : values.keySet())
      section.set(key, values.get(key)); 
  }
  
  private HashMap<String, Integer> getEnchants(ItemStack item) {
    Map<Enchantment, Integer> enchantments = item.getEnchantments();
    HashMap<String, Integer> enchants = new HashMap<>();
    if (enchantments == null || enchantments.isEmpty())
      return enchants; 
    for (Enchantment enchantment : enchantments.keySet())
      enchants.put(enchantment.getName(), enchantments.getOrDefault(enchantment, Integer.valueOf(0))); 
    return enchants;
  }
  
  private HashMap<String, int[]> getCustomEffects(PotionMeta meta) {
    List<PotionEffect> effects = meta.getCustomEffects();
    HashMap<String, int[]> customEffects = (HashMap)new HashMap<>();
    if (effects == null || effects.isEmpty())
      return customEffects; 
    for (PotionEffect effect : effects) {
      customEffects.put(effect.getType().getName(), new int[] { effect.getAmplifier(), effect.getDuration() });
    } 
    return customEffects;
  }
}
