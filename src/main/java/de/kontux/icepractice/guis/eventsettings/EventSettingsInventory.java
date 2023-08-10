package de.kontux.icepractice.guis.eventsettings;

import de.kontux.icepractice.IcePracticePlugin;
import de.kontux.icepractice.api.gui.InventoryGui;
import de.kontux.icepractice.api.kit.IcePracticeKit;
import de.kontux.icepractice.configs.Settings;
import de.kontux.icepractice.kits.KitManager;
import de.kontux.icepractice.listeners.ChatEntryListener;
import de.kontux.icepractice.tournaments.EventManager;
import de.kontux.icepractice.tournaments.EventType;
import de.kontux.icepractice.util.ChatEntryUser;
import de.kontux.icepractice.util.ItemBuilder;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

public abstract class EventSettingsInventory extends InventoryGui implements ChatEntryUser {
  protected final ItemStack startButton = ItemBuilder.create(Material.INK_SACK, ChatColor.GREEN + "Start Event", null, 1, (short)2);
  
  private final EventType type;
  
  protected IcePracticeKit kit;
  
  protected int teamSize = 1;
  
  protected String password = null;
  
  public EventSettingsInventory(Player player, EventType type) {
    super(player, Settings.PRIMARY + "Event Settings:", 27);
    this.type = type;
    this.kit = KitManager.getInstance().getKits().get(0);
  }
  
  protected void setItems() {
    List<String> teamLore = new ArrayList<>();
    teamLore.add(Settings.PRIMARY + "Current team size: " + Settings.SECONDARY + this.teamSize);
    ItemStack teamItem = ItemBuilder.create(Material.SKULL_ITEM, Settings.SECONDARY + "Set team size:", teamLore);
    ItemStack plusItem = ItemBuilder.create(Material.STONE_BUTTON, "+", null);
    ItemStack minusItem = ItemBuilder.create(Material.STONE_BUTTON, "-", null);
    ItemStack kitItem = this.kit.getIcon();
    ItemMeta kitMeta = kitItem.getItemMeta();
    kitMeta.setDisplayName(Settings.PRIMARY + "Kit: " + Settings.SECONDARY + this.kit.getName());
    kitItem.setItemMeta(kitMeta);
    if (Settings.ALLOW_PASSWORD_PROTECTION) {
      String name;
      if (this.password != null) {
        name = Settings.PRIMARY + "Current password: " + Settings.SECONDARY + this.password;
      } else {
        name = Settings.PRIMARY + "Click to set the event's password";
      } 
      ItemStack passwordItem = ItemBuilder.create(Material.ENDER_CHEST, name, null);
      this.inventory.setItem(2, plusItem);
      this.inventory.setItem(20, minusItem);
      this.inventory.setItem(11, teamItem);
      this.inventory.setItem(13, kitItem);
      this.inventory.setItem(15, passwordItem);
    } else {
      this.inventory.setItem(3, plusItem);
      this.inventory.setItem(21, minusItem);
      this.inventory.setItem(12, teamItem);
      this.inventory.setItem(14, kitItem);
    } 
    this.inventory.setItem(26, this.startButton);
  }
  
  public void runAction(ItemStack item) {
    if (item.isSimilar(this.startButton)) {
      (new EventManager()).hostEvent(this.player, this.type, this.password, this.teamSize, this.kit);
      this.player.closeInventory();
      return;
    } 
    String name = item.getItemMeta().getDisplayName();
    Material type = item.getType();
    if (type == Material.STONE_BUTTON) {
      if (name.equals("+")) {
        this.teamSize++;
      } else if (name.equals("-") && this.teamSize > 1) {
        this.teamSize--;
      } 
      setItems();
    } else if (type == Material.ENDER_CHEST && name.contains("password")) {
      Bukkit.getPluginManager().registerEvents((Listener)new ChatEntryListener(this.player, this), (Plugin)IcePracticePlugin.getInstance());
      this.player.closeInventory();
      this.player.sendMessage(Settings.PRIMARY + "Enter the password for the event in chat:");
    } else if (name.contains(Settings.PRIMARY + "Kit: " + Settings.SECONDARY)) {
      this.kit = getKitByItemName(name);
      setItems();
    } 
  }
  
  public void setText(String text) {
    this.player.sendMessage(Settings.PRIMARY + "You set the password for your event.");
    this.password = text;
    openMenu();
  }
  
  private IcePracticeKit getKitByItemName(String name) {
    name = name.replace(Settings.PRIMARY + "Kit: " + Settings.SECONDARY, "");
    return KitManager.getInstance().getKit(name);
  }
}
