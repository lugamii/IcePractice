package de.kontux.icepractice.items.join;

import de.kontux.icepractice.IcePracticePlugin;
import de.kontux.icepractice.api.playerstates.PlayerState;
import de.kontux.icepractice.configs.Settings;
import de.kontux.icepractice.configs.files.JoinItemConfig;
import de.kontux.icepractice.configs.repositories.messages.EventMessageRepository;
import de.kontux.icepractice.guis.EventHostInventory;
import de.kontux.icepractice.guis.RankedQueueInventory;
import de.kontux.icepractice.guis.SettingsInventory;
import de.kontux.icepractice.guis.StatsInventory;
import de.kontux.icepractice.guis.UnrankedQueueInventory;
import de.kontux.icepractice.guis.editormenus.KitEditorInventory;
import de.kontux.icepractice.party.Party;
import de.kontux.icepractice.playermanagement.PlayerStates;
import de.kontux.icepractice.registries.PartyRegistry;
import de.kontux.icepractice.util.ItemBuilder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class JoinItemManager {
  private static final JoinItemManager INSTANCE = new JoinItemManager();
  
  private final HashMap<Integer, JoinItem> joinItems = new HashMap<>();
  
  private JoinItemManager() {
    loadFromConfig();
  }
  
  public static JoinItemManager getInstance() {
    return INSTANCE;
  }
  
  private void loadFromConfig() {
    FileConfiguration config = JoinItemConfig.get();
    ConfigurationSection section = config.isConfigurationSection("joinitems") ? config.getConfigurationSection("joinitems") : config.createSection("joinitems");
    for (String key : section.getKeys(false)) {
      int slot = Integer.parseInt(key);
      Material material = (Material.matchMaterial(section.getString(key + ".item")) != null) ? Material.matchMaterial(section.getString(key + ".item")) : Material.SKULL_ITEM;
      String displayName = section.isString(key + ".name") ? section.getString(key + ".name") : "§cWrongly Set up";
      List<String> lore = section.getStringList(key + ".lore");
      JoinItemFunction function = JoinItemFunction.getFunction(section.getString(key + ".function"));
      if (Settings.USE_COLOURS_FOR_ITEMS)
        displayName = Settings.PRIMARY + displayName; 
      ItemStack item = ItemBuilder.create(material, displayName, lore);
      this.joinItems.put(Integer.valueOf(slot), new JoinItem(item, function));
    } 
  }
  
  public void giveItems(Player player) {
    player.getInventory().clear();
    for (int i = 36; i < 40; i++)
      player.getInventory().setItem(i, new ItemStack(Material.AIR)); 
    for (Iterator<Integer> iterator = this.joinItems.keySet().iterator(); iterator.hasNext(); ) {
      int slot = ((Integer)iterator.next()).intValue();
      player.getInventory().setItem(slot, ((JoinItem)this.joinItems.get(Integer.valueOf(slot))).getItem());
    } 
  }
  
  public JoinItem getJoinItem(ItemStack item, int slot) {
    return (this.joinItems.get(Integer.valueOf(slot)) != null && ((JoinItem)this.joinItems.get(Integer.valueOf(slot))).getItem().isSimilar(item)) ? this.joinItems.get(Integer.valueOf(slot)) : null;
  }
  
  public void runFunction(Player player, JoinItemFunction function) {
    switch (function) {
      case UNRANKED:
        (new UnrankedQueueInventory(player)).openMenu();
        break;
      case RANKED:
        (new RankedQueueInventory(player)).openMenu();
        break;
      case EVENTS:
        if (player.hasPermission("icepractice.host")) {
          (new EventHostInventory(player)).openMenu();
          break;
        } 
        player.sendMessage((new EventMessageRepository()).getNoPermMessage());
        break;
      case SETTINGS:
        (new SettingsInventory(player)).openMenu();
        break;
      case STATS:
        (new StatsInventory(player, IcePracticePlugin.getInstance().getRepository())).openMenu();
        break;
      case EDITOR:
        (new KitEditorInventory(player)).openMenu();
        break;
      case PARTY:
        if (PlayerStates.getInstance().getState(player) == PlayerState.IDLE && !PartyRegistry.isInParty(player))
          (new Party(player)).createParty(); 
        break;
      case WRONGLY_SETUP:
        player.sendMessage("§cThis item was not configured correctly.");
        break;
    } 
  }
  
  public void reload() {
    this.joinItems.clear();
    loadFromConfig();
  }
}
