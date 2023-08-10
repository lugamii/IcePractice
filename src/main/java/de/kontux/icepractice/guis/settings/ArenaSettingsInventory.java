package de.kontux.icepractice.guis.settings;

import de.kontux.icepractice.api.arena.IcePracticeArena;
import de.kontux.icepractice.api.gui.InventoryGui;
import de.kontux.icepractice.arenas.ArenaManager;
import de.kontux.icepractice.configs.Settings;
import de.kontux.icepractice.util.ItemBuilder;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ArenaSettingsInventory extends InventoryGui {
  private final IcePracticeArena arena;
  
  public ArenaSettingsInventory(Player player, IcePracticeArena arena) {
    super(player, Settings.PRIMARY + "Settings for arena " + Settings.SECONDARY + arena.getName(), 9);
    this.arena = arena;
  }
  
  protected void setItems() {
    ItemStack buildItem = ItemBuilder.create(Material.BRICK, Settings.SECONDARY + "Build Mode", buildToggleLore(this.arena.isBuild(), new String[] { "If the arena may be used by build kits.", "These arenas can only be used by one fight at the time." }));
    ItemStack sumoItem = ItemBuilder.create(Material.LEASH, Settings.SECONDARY + "Sumo", buildToggleLore(this.arena.isSumo(), new String[] { "Allows only Sumo kits to use this arena" }));
    ItemStack hcfItem = ItemBuilder.create(Material.FISHING_ROD, Settings.SECONDARY + "HCF", buildToggleLore(this.arena.isHcf(), new String[] { "Allows only HCF kits to use this arena." }));
    ItemStack spleefItem = ItemBuilder.create(Material.DIAMOND_SPADE, Settings.SECONDARY + "Spleef", buildToggleLore(this.arena.isSpleef(), new String[] { "Allows only Spleef kits to use this arena." }));
    ItemStack deleteItem = ItemBuilder.create(Material.REDSTONE, ChatColor.RED + "Delete", null);
    this.inventory.setItem(0, buildItem);
    this.inventory.setItem(1, sumoItem);
    this.inventory.setItem(2, hcfItem);
    this.inventory.setItem(3, spleefItem);
    this.inventory.setItem(8, deleteItem);
  }
  
  public void runAction(ItemStack item) {
    String name = item.getItemMeta().getDisplayName();
    if (item.getType() == Material.BRICK && name.equals(Settings.SECONDARY + "Build Mode")) {
      this.arena.setBuild(!this.arena.isBuild());
    } else if (item.getType() == Material.LEASH && name.equals(Settings.SECONDARY + "Sumo")) {
      this.arena.setSumo(!this.arena.isSumo());
    } else if (item.getType() == Material.FISHING_ROD && name.equals(Settings.SECONDARY + "HCF")) {
      this.arena.setHcf(!this.arena.isHcf());
    } else if (item.getType() == Material.DIAMOND_SPADE && name.equals(Settings.SECONDARY + "Spleef")) {
      this.arena.setSpleef(!this.arena.isSpleef());
    } else if (item.getType() == Material.REDSTONE && name.equals(ChatColor.RED + "Delete")) {
      ArenaManager.getInstance().delete(this.player, this.arena.getName());
      this.player.closeInventory();
    } 
    setItems();
  }
  
  private List<String> buildToggleLore(boolean state, String... description) {
    String stateText = state ? (ChatColor.GREEN + "Enabled") : (ChatColor.RED + "Disabled");
    List<String> lore = new ArrayList<>();
    lore.add(Settings.SECONDARY + "Current: " + stateText);
    for (String line : description)
      lore.add(Settings.PRIMARY + line); 
    return lore;
  }
}
