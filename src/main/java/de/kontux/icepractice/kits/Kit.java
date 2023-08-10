package de.kontux.icepractice.kits;

import de.kontux.icepractice.api.kit.IcePracticeKit;
import de.kontux.icepractice.configs.Settings;
import de.kontux.icepractice.configs.files.KitConfig;
import de.kontux.icepractice.util.ItemBuilder;
import java.util.Map;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

public class Kit implements IcePracticeKit {
  private final FileConfiguration kitConfig = KitConfig.get();
  
  private final String name;
  
  private final ItemStack[] cachedItems = new ItemStack[40];
  
  private int pearlCooldown = 0;
  
  private ItemStack icon;
  
  private boolean sumo;
  
  private boolean combo;
  
  private boolean ranked;
  
  private boolean hcf;
  
  private boolean spleef;
  
  private boolean editable;
  
  private boolean build;
  
  private boolean regen;
  
  private boolean chestEditing;
  
  Kit(String name) {
    this.name = name;
    this.icon = ItemBuilder.create(Material.DIAMOND_SWORD, Settings.SECONDARY + name, null);
    this.editable = true;
    loadFromFile();
  }
  
  private void loadFromFile() {
    ConfigurationSection section = this.kitConfig.getConfigurationSection("Kits." + this.name);
    if (section == null)
      section = this.kitConfig.createSection("Kits." + this.name); 
    Material iconMaterial = this.kitConfig.isString("Kits." + this.name + ".icon.name") ? Material.getMaterial(this.kitConfig.getString("Kits." + this.name + ".icon.name")) : Material.DIAMOND_SWORD;
    short durability = this.kitConfig.isInt("Kits." + this.name + ".icon.durability") ? (short)this.kitConfig.getInt("Kits." + this.name + ".icon.durability") : 100;
    this.icon = ItemBuilder.create(iconMaterial, Settings.SECONDARY + this.name, durability, null);
    this.ranked = section.getBoolean("ranked");
    this.sumo = section.getBoolean("sumo");
    this.combo = section.getBoolean("combo");
    this.hcf = section.getBoolean("hcf");
    this.spleef = section.getBoolean("spleef");
    this.editable = (!section.isBoolean("editable") || section.getBoolean("editable"));
    this.build = section.getBoolean("build");
    this.regen = (!section.isBoolean("regen") || section.getBoolean("regen"));
    this.chestEditing = (!section.isBoolean("chest-editing") || section.getBoolean("chest-editing"));
    for (int i = 0; i < 40; i++) {
      String materialName = section.getString("inventory.slots." + i + ".itemName");
      if (materialName != null) {
        ItemStack item = new ItemStack(Material.matchMaterial(materialName));
        String displayName = section.getString("inventory.slots." + i + ".itemDisplayName");
        int itemAmount = Integer.parseInt(section.getString("inventory.slots." + i + ".itemAmount"));
        short itemDurability = Short.parseShort(section.getString("inventory.slots." + i + ".itemDurability"));
        item.setAmount(itemAmount);
        item.setDurability(itemDurability);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(displayName);
        ConfigurationSection enchantSection = section.getConfigurationSection("inventory.slots." + i + ".enchantments");
        if (enchantSection != null)
          for (String enchant : enchantSection.getKeys(false)) {
            int level = enchantSection.getInt(enchant + ".level");
            meta.addEnchant(Enchantment.getByName(enchant), level, true);
          }  
        item.setItemMeta(meta);
        this.cachedItems[i] = item;
      } else {
        this.cachedItems[i] = new ItemStack(Material.AIR);
      } 
    } 
    if (section.isInt("pearl-cooldown"))
      this.pearlCooldown = section.getInt("pearl-cooldown"); 
  }
  
  public void setInventory(ItemStack[] items, ItemStack[] armour) {
    for (int i = 0; i < 40; i++) {
      if (i <= 35) {
        this.cachedItems[i] = items[i];
      } else {
        this.cachedItems[i] = armour[i - 36];
      } 
    } 
    save();
  }
  
  public void save() {
    for (int i = 0; i < 40; i++) {
      ItemStack item = this.cachedItems[i];
      if (item != null)
        if (item.getType() != Material.AIR) {
          ItemMeta itemMeta = item.getItemMeta();
          Map<Enchantment, Integer> enchants = itemMeta.getEnchants();
          String material = item.getType().name();
          String itemDisplayName = itemMeta.getDisplayName();
          int itemAmount = item.getAmount();
          short itemDurability = item.getDurability();
          this.kitConfig.set("Kits." + this.name + ".inventory.slots." + i + ".itemName", material);
          this.kitConfig.set("Kits." + this.name + ".inventory.slots." + i + ".itemDisplayName", itemDisplayName);
          this.kitConfig.set("Kits." + this.name + ".inventory.slots." + i + ".itemAmount", Integer.valueOf(itemAmount));
          this.kitConfig.set("Kits." + this.name + ".inventory.slots." + i + ".itemDurability", Short.valueOf(itemDurability));
          if (!enchants.isEmpty())
            for (Enchantment enchantment : enchants.keySet()) {
              this.kitConfig.set("Kits." + this.name + ".inventory.slots." + i + ".enchantments." + enchantment.getName() + ".name", enchantment.getName());
              this.kitConfig.set("Kits." + this.name + ".inventory.slots." + i + ".enchantments." + enchantment.getName() + ".level", enchants.get(enchantment));
            }  
        }  
      this.kitConfig.set("Kits." + this.name + ".pearl-cooldown", Integer.valueOf(this.pearlCooldown));
    } 
    this.kitConfig.set("Kits." + this.name + ".sumo", Boolean.valueOf(this.sumo));
    this.kitConfig.set("Kits." + this.name + ".hcf", Boolean.valueOf(this.hcf));
    this.kitConfig.set("Kits." + this.name + ".combo", Boolean.valueOf(this.combo));
    this.kitConfig.set("Kits." + this.name + ".editable", Boolean.valueOf(this.editable));
    this.kitConfig.set("Kits." + this.name + ".ranked", Boolean.valueOf(this.ranked));
    this.kitConfig.set("Kits." + this.name + ".build", Boolean.valueOf(this.build));
    this.kitConfig.set("Kits." + this.name + ".regen", Boolean.valueOf(this.regen));
    this.kitConfig.set("Kits." + this.name + ".chest-editing", Boolean.valueOf(this.chestEditing));
    KitConfig.save();
  }
  
  public void delete() {
    this.kitConfig.set("Kits." + this.name, null);
    KitConfig.save();
  }
  
  public void equipKit(Player player) {
    PlayerInventory playerInventory = player.getInventory();
    playerInventory.clear();
    for (int i = 0; i < 40; i++)
      playerInventory.setItem(i, this.cachedItems[i]); 
  }
  
  public int getPearlCooldown() {
    return this.pearlCooldown;
  }
  
  public void setPearlCooldown(int cooldown) {
    this.pearlCooldown = cooldown;
    save();
    KitConfig.save();
  }
  
  public String getName() {
    return this.name;
  }
  
  public ItemStack[] getItems() {
    return this.cachedItems;
  }
  
  public ItemStack[] getArmor() {
    ItemStack[] armor = new ItemStack[4];
    System.arraycopy(this.cachedItems, 36, armor, 0, 4);
    return armor;
  }
  
  public ItemStack getIcon() {
    return this.icon;
  }
  
  public void setIcon(ItemStack icon) {
    this.icon = icon;
    short durability = icon.getDurability();
    this.kitConfig.set("Kits." + this.name + ".icon.name", icon.getType().name());
    this.kitConfig.set("Kits." + this.name + ".icon.durability", Short.valueOf(durability));
    KitConfig.save();
  }
  
  public boolean isRanked() {
    return this.ranked;
  }
  
  public void setRanked(boolean ranked) {
    this.ranked = ranked;
    this.kitConfig.set("Kits." + this.name + ".ranked", Boolean.valueOf(ranked));
    KitConfig.save();
  }
  
  public boolean isSumo() {
    return this.sumo;
  }
  
  public void setSumo(boolean sumo) {
    this.sumo = sumo;
    this.kitConfig.set("Kits." + this.name + ".sumo", Boolean.valueOf(sumo));
    KitConfig.save();
  }
  
  public boolean isCombo() {
    return this.combo;
  }
  
  public void setCombo(boolean combo) {
    this.combo = combo;
    this.kitConfig.set("Kits." + this.name + ".combo", Boolean.valueOf(combo));
    KitConfig.save();
  }
  
  public String toString() {
    return getName();
  }
  
  public void setAllowBuild(boolean allowBuild) {
    this.build = allowBuild;
    this.kitConfig.set("Kits." + this.name + ".build", Boolean.valueOf(allowBuild));
    KitConfig.save();
  }
  
  public boolean isBuild() {
    return this.build;
  }
  
  public boolean isHcf() {
    return this.hcf;
  }
  
  public void setHcf(boolean hcf) {
    this.hcf = hcf;
    this.kitConfig.set("Kits." + this.name + ".hcf", Boolean.valueOf(hcf));
    KitConfig.save();
  }
  
  public boolean isSpleef() {
    return this.spleef;
  }
  
  public void setSpleef(boolean spleef) {
    this.spleef = spleef;
    this.kitConfig.set("Kits." + this.name + ".spleef", Boolean.valueOf(spleef));
    KitConfig.save();
  }
  
  public boolean isEditable() {
    return this.editable;
  }
  
  public void setEditable(boolean editable) {
    this.editable = editable;
    this.kitConfig.set("Kits." + this.name + ".editable", Boolean.valueOf(editable));
    KitConfig.save();
  }
  
  public boolean allowRegen() {
    return this.regen;
  }
  
  public void setAllowRegen(boolean allowRegen) {
    this.regen = allowRegen;
    this.kitConfig.set("Kits." + this.name + ".regen", Boolean.valueOf(allowRegen));
    KitConfig.save();
  }
  
  public boolean allowChestEditing() {
    return this.chestEditing;
  }
  
  public void setAllowChestEditing(boolean allowChestEditing) {
    this.chestEditing = allowChestEditing;
    this.kitConfig.set("Kits." + this.name + ".chest-editing", Boolean.valueOf(this.chestEditing));
    KitConfig.save();
  }
}
