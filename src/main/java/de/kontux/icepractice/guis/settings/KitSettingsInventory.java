package de.kontux.icepractice.guis.settings;

import de.kontux.icepractice.api.gui.InventoryGui;
import de.kontux.icepractice.api.kit.IcePracticeKit;
import de.kontux.icepractice.configs.Settings;
import de.kontux.icepractice.kits.KitManager;
import de.kontux.icepractice.util.ItemBuilder;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class KitSettingsInventory extends InventoryGui {
  private final IcePracticeKit kit;
  
  public KitSettingsInventory(Player player, IcePracticeKit kit) {
    super(player, Settings.PRIMARY + "Settings for kit " + Settings.SECONDARY + kit.getName(), 9);
    this.kit = kit;
  }
  
  protected void setItems() {
    ItemStack buildItem = ItemBuilder.create(Material.BRICK, Settings.SECONDARY + "Build Mode", buildToggleLore(this.kit.isBuild(), new String[] { "If the kit may be used in build arenas." }));
    ItemStack sumoItem = ItemBuilder.create(Material.LEASH, Settings.SECONDARY + "Sumo", buildToggleLore(this.kit.isSumo(), new String[] { "Makes this kit a sumo kit." }));
    ItemStack hcfItem = ItemBuilder.create(Material.FISHING_ROD, Settings.SECONDARY + "HCF", buildToggleLore(this.kit.isHcf(), new String[] { "If the kit may only use HCF arenas." }));
    ItemStack spleefItem = ItemBuilder.create(Material.DIAMOND_SPADE, Settings.SECONDARY + "Spleef", buildToggleLore(this.kit.isSpleef(), new String[] { "Makes this kit a spleef kit." }));
    ItemStack regenItem = ItemBuilder.create(Material.POTION, Settings.SECONDARY + "Natural Regeneration", buildToggleLore(this.kit.allowRegen(), new String[] { "If people regenerate health naturally." }));
    ItemStack chestEditItem = ItemBuilder.create(Material.CHEST, Settings.SECONDARY + "Allow chest editing", buildToggleLore(this.kit.allowChestEditing(), new String[] { "If players may use the chest in the kit editor." }));
    ItemStack deleteItem = ItemBuilder.create(Material.REDSTONE, ChatColor.RED + "Delete", null);
    this.inventory.setItem(0, buildItem);
    this.inventory.setItem(1, sumoItem);
    this.inventory.setItem(2, hcfItem);
    this.inventory.setItem(3, spleefItem);
    this.inventory.setItem(4, regenItem);
    this.inventory.setItem(5, chestEditItem);
    this.inventory.setItem(8, deleteItem);
  }
  
  public void runAction(ItemStack item) {
    String name = item.getItemMeta().getDisplayName();
    if (item.getType() == Material.BRICK && name.equals(Settings.SECONDARY + "Build Mode")) {
      this.kit.setAllowBuild(!this.kit.isBuild());
    } else if (item.getType() == Material.LEASH && name.equals(Settings.SECONDARY + "Sumo")) {
      this.kit.setSumo(!this.kit.isSumo());
    } else if (item.getType() == Material.FISHING_ROD && name.equals(Settings.SECONDARY + "HCF")) {
      this.kit.setHcf(!this.kit.isHcf());
    } else if (item.getType() == Material.DIAMOND_SPADE && name.equals(Settings.SECONDARY + "Spleef")) {
      this.kit.setSpleef(!this.kit.isSpleef());
    } else if (item.getType() == Material.POTION && name.equals(Settings.SECONDARY + "Natural Regeneration")) {
      this.kit.setAllowRegen(!this.kit.allowRegen());
    } else if (item.getType() == Material.CHEST && name.equals(Settings.SECONDARY + "Allow chest editing")) {
      this.kit.setAllowChestEditing(!this.kit.allowChestEditing());
    } else if (item.getType() == Material.REDSTONE && name.equals(ChatColor.RED + "Delete")) {
      KitManager.getInstance().deleteKit(this.player, this.kit.getName());
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
