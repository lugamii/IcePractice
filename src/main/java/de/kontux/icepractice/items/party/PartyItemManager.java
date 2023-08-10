package de.kontux.icepractice.items.party;

import de.kontux.icepractice.configs.Settings;
import de.kontux.icepractice.configs.files.JoinItemConfig;
import de.kontux.icepractice.guis.party.PartyDuelInventory;
import de.kontux.icepractice.guis.party.PartyEventInventory;
import de.kontux.icepractice.guis.party.PartyQueueInventory;
import de.kontux.icepractice.party.Party;
import de.kontux.icepractice.registries.PartyRegistry;
import de.kontux.icepractice.util.ItemBuilder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class PartyItemManager {
  private static final PartyItemManager INSTANCE = new PartyItemManager();
  
  private final HashMap<Integer, PartyItem> partyItems = new HashMap<>();
  
  private PartyItemManager() {
    loadFromConfig();
  }
  
  public static PartyItemManager getInstance() {
    return INSTANCE;
  }
  
  private void loadFromConfig() {
    FileConfiguration config = JoinItemConfig.get();
    for (String key : config.getConfigurationSection("partyitems").getKeys(false)) {
      int slot = Integer.parseInt(key);
      Material material = (Material.matchMaterial(config.getString("partyitems." + key + ".item")) != null) ? Material.matchMaterial(config.getString("partyitems." + key + ".item")) : Material.SKULL_ITEM;
      String displayName = config.isString("partyitems." + key + ".name") ? config.getString("partyitems." + key + ".name") : "§cWrongly Set up";
      List<String> lore = config.getStringList("partyitems." + key + ".lore");
      PartyItemFunction function = PartyItemFunction.getFunction(config.getString("partyitems." + key + ".function"));
      if (Settings.USE_COLOURS_FOR_ITEMS)
        displayName = Settings.PRIMARY + displayName; 
      ItemStack item = ItemBuilder.create(material, displayName, lore);
      this.partyItems.put(Integer.valueOf(slot), new PartyItem(item, function));
    } 
  }
  
  public void giveItems(Player player) {
    player.getInventory().clear();
    for (int i = 36; i < 40; i++)
      player.getInventory().setItem(i, new ItemStack(Material.AIR)); 
    for (Iterator<Integer> iterator = this.partyItems.keySet().iterator(); iterator.hasNext(); ) {
      int slot = ((Integer)iterator.next()).intValue();
      player.getInventory().setItem(slot, ((PartyItem)this.partyItems.get(Integer.valueOf(slot))).getItem());
    } 
  }
  
  public PartyItem getPartyItem(ItemStack item, int slot) {
    if (this.partyItems.containsKey(Integer.valueOf(slot)) && ((PartyItem)this.partyItems.get(Integer.valueOf(slot))).getItem().isSimilar(item))
      return this.partyItems.get(Integer.valueOf(slot)); 
    return null;
  }
  
  public void runFunction(Player player, PartyItemFunction function) {
    Party party = PartyRegistry.getPartyByPlayer(player);
    if (party == null) {
      player.sendMessage(ChatColor.RED + "You are not supposed to have these items...");
      return;
    } 
    switch (function) {
      case INFO:
        party.showInfo(player);
        break;
      case LEAVE:
        party.leavePlayer(player);
        break;
      case WRONGLY_SETUP:
        player.sendMessage("§cThis item was not configured correctly.");
        break;
    } 
    if (party.isLeader(player))
      switch (function) {
        case DUEL:
          (new PartyDuelInventory(player, party)).openMenu();
          break;
        case QUEUE:
          (new PartyQueueInventory(player, party)).openMenu();
          break;
        case EVENTS:
          (new PartyEventInventory(player, party)).openMenu();
          break;
      }  
  }
  
  public void reload() {
    this.partyItems.clear();
    loadFromConfig();
  }
}
