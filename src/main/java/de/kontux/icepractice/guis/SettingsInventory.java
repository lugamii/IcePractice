package de.kontux.icepractice.guis;

import de.kontux.icepractice.api.gui.InventoryGui;
import de.kontux.icepractice.api.user.WorldTime;
import de.kontux.icepractice.configs.Settings;
import de.kontux.icepractice.userdata.PlayerData;
import de.kontux.icepractice.userdata.PlayerDataManager;
import de.kontux.icepractice.util.ItemBuilder;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class SettingsInventory extends InventoryGui {
  public SettingsInventory(Player player) {
    super(player, Settings.PRIMARY + "Settings:", 18);
  }
  
  protected void setItems() {
    PlayerData data = PlayerDataManager.getSettingsData(this.player.getUniqueId());
    ItemStack[] items = new ItemStack[4];
    items[0] = ItemBuilder.create(Material.DIAMOND_SWORD, Settings.SECONDARY + "Toggle duel requests", getToggleLore(data.isSendRequests()));
    items[1] = ItemBuilder.create(Material.SIGN, Settings.SECONDARY + "Toggle sidebar", getToggleLore(data.isShowBoard()));
    items[2] = ItemBuilder.create(Material.SKULL_ITEM, Settings.SECONDARY + "Toggle spawn players", getToggleLore(data.isShowPlayers()));
    items[3] = ItemBuilder.create(Material.WATCH, Settings.SECONDARY + "Set world time", getWorldTimeLore(data.getWorldTime()));
    this.inventory.setContents(items);
  }
  
  private List<String> getToggleLore(boolean enabled) {
    List<String> lore = new ArrayList<>();
    if (enabled) {
      lore.add(ChatColor.GREEN + "+ " + ChatColor.GRAY + "Enabled");
      lore.add(ChatColor.GRAY + "Disabled");
    } else {
      lore.add(ChatColor.GRAY + "Enabled");
      lore.add(ChatColor.RED + "- " + ChatColor.GRAY + "Disabled");
    } 
    return lore;
  }
  
  private List<String> getWorldTimeLore(WorldTime current) {
    List<String> lore = new ArrayList<>();
    switch (current) {
      case DAY:
        lore.add(ChatColor.GREEN + "+ " + ChatColor.GRAY + "Day");
        lore.add(ChatColor.GRAY + "Sunset");
        lore.add(ChatColor.GRAY + "Night");
        break;
      case SUNSET:
        lore.add(ChatColor.GRAY + "Day");
        lore.add(ChatColor.GREEN + "+ " + ChatColor.GRAY + "Sunset");
        lore.add(ChatColor.GRAY + "Night");
        break;
      case NIGHT:
        lore.add(ChatColor.GRAY + "Day");
        lore.add(ChatColor.GRAY + "Sunset");
        lore.add(ChatColor.GREEN + "+ " + ChatColor.GRAY + "Night");
        break;
    } 
    return lore;
  }
  
  public void runAction(ItemStack item) {
    PlayerData settings = PlayerDataManager.getSettingsData(this.player.getUniqueId());
    if (item.getType() == Material.DIAMOND_SWORD) {
      settings.setSendRequests(!settings.isSendRequests());
    } else if (item.getType() == Material.SIGN) {
      settings.setShowBoard(!settings.isShowBoard());
    } else if (item.getType() == Material.SKULL_ITEM) {
      settings.setShowPlayers(!settings.isShowPlayers());
    } else if (item.getType() == Material.WATCH) {
      WorldTime time = settings.getWorldTime();
      switch (time) {
        case DAY:
          settings.setWorldTime(WorldTime.SUNSET);
          break;
        case SUNSET:
          settings.setWorldTime(WorldTime.NIGHT);
          break;
        case NIGHT:
          settings.setWorldTime(WorldTime.DAY);
          break;
      } 
    } 
    setItems();
  }
}
