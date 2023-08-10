package de.kontux.icepractice.userdata;

import de.kontux.icepractice.api.kit.IcePracticeKit;
import de.kontux.icepractice.api.user.CustomUserKit;
import de.kontux.icepractice.api.user.WorldTime;
import de.kontux.icepractice.configs.files.PlayerConfig;
import de.kontux.icepractice.database.SQLRepository;
import de.kontux.icepractice.kits.KitManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class PlayerDataRepository {
  private final FileConfiguration config = PlayerConfig.get();
  
  private final SQLRepository repository;
  
  public PlayerDataRepository(SQLRepository repository) {
    this.repository = repository;
  }
  
  public void addUser(UUID uuid, String name) {
    this.repository.addPlayer(uuid, name);
    for (IcePracticeKit current : KitManager.getInstance().getKits()) {
      if (current.isRanked())
        setElo(uuid, getElo(uuid, current), current); 
    } 
    PlayerConfig.save();
  }
  
  void setUseScoreboard(UUID uuid, boolean use) {
    this.config.set("PlayerData." + uuid.toString() + ".settings.sidebar", Boolean.valueOf(use));
    PlayerConfig.save();
  }
  
  void setDuelRequests(UUID uuid, boolean requests) {
    this.config.set("PlayerData." + uuid.toString() + ".settings.requests", Boolean.valueOf(requests));
    PlayerConfig.save();
  }
  
  void setShowPlayers(UUID uuid, boolean show) {
    this.config.set("PlayerData." + uuid.toString() + ".settings.show_players", Boolean.valueOf(show));
    PlayerConfig.save();
  }
  
  void setWorldTime(UUID uuid, WorldTime time) {
    this.config.set("PlayerData." + uuid.toString() + ".settings.time", time.toString());
    PlayerConfig.save();
  }
  
  public void setElo(UUID uuid, int elo, IcePracticeKit kit) {
    this.config.set("PlayerData." + uuid.toString() + ".elo." + kit.getName(), Integer.valueOf(elo));
    PlayerConfig.save();
    this.repository.setElo(uuid, kit, elo);
  }
  
  public int getElo(UUID uuid, IcePracticeKit kit) {
    int localElo = this.config.isInt("PlayerData." + uuid.toString() + ".elo." + kit.getName()) ? this.config.getInt("PlayerData." + uuid.toString() + ".elo." + kit.getName()) : 1000;
    return this.repository.useMySql() ? this.repository.getElo(uuid, kit) : localElo;
  }
  
  boolean useScoreboard(UUID uuid) {
    String entry = this.config.getString("PlayerData." + uuid.toString() + ".settings.sidebar");
    return (entry == null || Boolean.parseBoolean(entry));
  }
  
  boolean sendRequests(UUID uuid) {
    String entry = this.config.getString("PlayerData." + uuid.toString() + ".settings.requests");
    return (entry == null || Boolean.parseBoolean(entry));
  }
  
  boolean showPlayers(UUID uuid) {
    String entry = this.config.getString("PlayerData." + uuid.toString() + ".settings.show_players");
    return (entry == null || Boolean.parseBoolean(entry));
  }
  
  WorldTime getWorldTime(UUID uuid) {
    String entry = this.config.getString("PlayerData." + uuid.toString() + ".time");
    return (entry != null) ? WorldTime.valueOf(entry) : WorldTime.DAY;
  }
  
  void saveCustomKit(UUID uuid, IcePracticeKit kit, int kitNumber, List<ItemStack> inventory) {
    this.config.set("PlayerData." + uuid.toString() + ".kits." + kit + "." + kitNumber + ".name", kit + " #" + kitNumber);
    for (int i = 0; i <= 39; i++) {
      ItemStack item = inventory.get(i);
      if (item != null) {
        ItemMeta itemMeta = item.getItemMeta();
        Map<Enchantment, Integer> enchants = itemMeta.getEnchants();
        String material = item.getType().name();
        String itemDisplayName = itemMeta.getDisplayName();
        int itemAmount = item.getAmount();
        short itemDurability = item.getDurability();
        this.config.set("PlayerData." + uuid.toString() + ".kits." + kit.getName() + "." + kitNumber + ".inventory.slots." + i + ".itemType", material);
        this.config.set("PlayerData." + uuid.toString() + ".kits." + kit.getName() + "." + kitNumber + ".inventory.slots." + i + ".itemName", itemDisplayName);
        this.config.set("PlayerData." + uuid.toString() + ".kits." + kit.getName() + "." + kitNumber + ".inventory.slots." + i + ".itemAmount", Integer.valueOf(itemAmount));
        this.config.set("PlayerData." + uuid.toString() + ".kits." + kit.getName() + "." + kitNumber + ".inventory.slots." + i + ".itemDurability", Short.valueOf(itemDurability));
        if (!enchants.isEmpty())
          for (Enchantment enchantment : enchants.keySet()) {
            this.config.set("PlayerData." + uuid.toString() + ".kits." + kit + "." + kitNumber + ".inventory.slots." + i + ".enchantments." + enchantment.getName() + ".name", enchantment.getName());
            this.config.set("PlayerData." + uuid.toString() + ".kits." + kit + "." + kitNumber + ".inventory.slots." + i + ".enchantments." + enchantment.getName() + ".level", enchants.get(enchantment));
          }  
      } 
    } 
    PlayerConfig.save();
  }
  
  void setCustomName(UUID uuid, IcePracticeKit kit, int kitNumber, String customName) {
    this.config.set("PlayerData." + uuid.toString() + ".kits." + kit + "." + kitNumber + ".name", customName);
    PlayerConfig.save();
  }
  
  void deleteCustomKit(UUID uuid, IcePracticeKit kit, int number) {
    this.config.set("PlayerData." + uuid.toString() + ".kits." + kit + "." + number, null);
    PlayerConfig.save();
  }
  
  CustomKit getCustomKit(UUID uuid, IcePracticeKit kit, int number) {
    if (!this.config.isConfigurationSection("PlayerData." + uuid.toString() + ".kits." + kit + "." + number))
      return null; 
    String customName = this.config.isString("PlayerData." + uuid.toString() + ".kits." + kit + "." + number + ".name") ? this.config.getString("PlayerData." + uuid.toString() + ".kits." + kit + "." + number + ".name") : (kit.getName() + " #" + number);
    ItemStack[] items = new ItemStack[40];
    for (int i = 0; i <= 39; i++) {
      Material material = Material.getMaterial(this.config.getString("PlayerData." + uuid.toString() + ".kits." + kit + "." + number + ".inventory.slots." + i + ".itemType"));
      if (material != null) {
        ItemStack item = new ItemStack(material);
        String displayName = this.config.getString("PlayerData." + uuid.toString() + ".kits." + kit + "." + number + ".inventory.slots." + i + ".itemDisplayName");
        int itemAmount = Integer.parseInt(this.config.getString("PlayerData." + uuid.toString() + ".kits." + kit + "." + number + ".inventory.slots." + i + ".itemAmount"));
        short itemDurability = Short.parseShort(this.config.getString("PlayerData." + uuid.toString() + ".kits." + kit + "." + number + ".inventory.slots." + i + ".itemDurability"));
        item.setAmount(itemAmount);
        item.setDurability(itemDurability);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(displayName);
        String path = "PlayerData." + uuid.toString() + ".kits." + kit + "." + number + ".inventory.slots." + i + ".enchantments";
        if (this.config.isConfigurationSection(path))
          for (String enchant : this.config.getConfigurationSection(path).getKeys(false)) {
            String enchantment = this.config.getString("PlayerData." + uuid.toString() + ".kits." + kit + "." + number + ".inventory.slots." + i + ".enchantments." + enchant + ".name");
            int level = this.config.getInt("PlayerData." + uuid.toString() + ".kits." + kit + "." + number + ".inventory.slots." + i + ".enchantments." + enchant + ".level");
            itemMeta.addEnchant(Enchantment.getByName(enchantment), level, false);
          }  
        item.setItemMeta(itemMeta);
        items[i] = item;
      } else {
        items[i] = new ItemStack(Material.AIR);
      } 
    } 
    return new CustomKit(kit, number, customName, items);
  }
  
  List<CustomUserKit> getAllCustomKits(UUID uuid, IcePracticeKit kit) {
    List<CustomUserKit> customKits = new ArrayList<>();
    for (int i = 1; i <= 9; i++) {
      CustomKit customKit = getCustomKit(uuid, kit, i);
      if (customKit != null)
        customKits.add(customKit); 
    } 
    return customKits;
  }
}
