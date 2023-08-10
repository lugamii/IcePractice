package de.kontux.icepractice.guis.editormenus;

import de.kontux.icepractice.IcePracticePlugin;
import de.kontux.icepractice.api.gui.InventoryGui;
import de.kontux.icepractice.api.kit.IcePracticeKit;
import de.kontux.icepractice.api.playerstates.PlayerState;
import de.kontux.icepractice.api.user.CustomUserKit;
import de.kontux.icepractice.configs.Settings;
import de.kontux.icepractice.configs.repositories.messages.KitEditorMessageRepository;
import de.kontux.icepractice.kiteditor.KitEditorHandler;
import de.kontux.icepractice.listeners.ChatEntryListener;
import de.kontux.icepractice.playermanagement.PlayerStates;
import de.kontux.icepractice.userdata.PlayerData;
import de.kontux.icepractice.userdata.PlayerDataManager;
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
import org.bukkit.plugin.Plugin;

public class KitSaveInventory extends InventoryGui implements ChatEntryUser {
  private final IcePracticeKit kit;
  
  private int currentlyEditing = 1;
  
  public KitSaveInventory(Player player, IcePracticeKit kit) {
    super(player, Settings.PRIMARY + "Save the kit you created:", 36);
    this.kit = kit;
  }
  
  protected void setItems() {
    List<CustomUserKit> customKits = PlayerDataManager.getSettingsData(this.player.getUniqueId()).getCustomKits(this.kit);
    for (int i = 0; i < 9; i++) {
      List<String> lore = new ArrayList<>();
      lore.add(ChatColor.GRAY + "#" + (i + 1));
      ItemStack item = ItemBuilder.create(Material.CHEST, Settings.SECONDARY + "Save kit " + this.kit + " #" + (i + 1), lore);
      this.inventory.setItem(i, item);
    } 
    for (CustomUserKit customKit : customKits) {
      int number = customKit.getNumber();
      String name = customKit.getCustomName();
      List<String> lore = new ArrayList<>();
      lore.add(ChatColor.GRAY + "#" + number);
      ItemStack loadItem = ItemBuilder.create(Material.BOOK, Settings.SECONDARY + "Load kit " + name, lore);
      this.inventory.setItem(number + 8, loadItem);
      ItemStack renameItem = ItemBuilder.create(Material.NAME_TAG, Settings.SECONDARY + "Rename kit " + name, lore);
      this.inventory.setItem(number + 17, renameItem);
      ItemStack deleteItem = ItemBuilder.create(Material.FLINT, Settings.SECONDARY + "Delete kit " + name, lore);
      this.inventory.setItem(number + 26, deleteItem);
    } 
  }
  
  public void runAction(ItemStack item) {
    PlayerData data = PlayerDataManager.getSettingsData(this.player.getUniqueId());
    int number = getKitNumber(item);
    KitEditorHandler handler = (KitEditorHandler)KitEditorHandler.HANDLERS.get(this.player.getUniqueId());
    handler.currentlyEditing = number;
    IcePracticeKit kit = handler.getKit();
    if (item.getType() == Material.CHEST && item.getItemMeta().getDisplayName().contains("Save kit")) {
      data.saveCustomKit(kit, number);
    } else if (item.getType() == Material.NAME_TAG && item.getItemMeta().getDisplayName().contains("Rename kit ")) {
      this.currentlyEditing = number;
      Bukkit.getPluginManager().registerEvents((Listener)new ChatEntryListener(this.player, this), (Plugin)IcePracticePlugin.getInstance());
      this.player.sendMessage((new KitEditorMessageRepository()).getSetNamePromptMessage());
    } else if (item.getType() == Material.BOOK && item.getItemMeta().getDisplayName().contains("Load kit")) {
      data.getCustomKit(kit, number).equip(this.player);
    } else if (item.getType() == Material.FLINT && item.getItemMeta().getDisplayName().contains("Delete kit")) {
      data.deleteCustomKit(kit, number);
    } 
    this.player.closeInventory();
  }
  
  public void setText(String text) {
    if (PlayerStates.getInstance().getState(this.player) != PlayerState.EDITOR) {
      this.player.sendMessage(ChatColor.RED + "Could not set custom name because you left the kit editor.");
      return;
    } 
    PlayerData data = PlayerDataManager.getSettingsData(this.player.getUniqueId());
    data.setCustomKitName(this.kit, text, this.currentlyEditing);
  }
  
  private int getKitNumber(ItemStack item) {
    List<String> lore = item.getItemMeta().getLore();
    if (lore == null)
      return -1; 
    String text = ((String)lore.get(0)).replace(ChatColor.GRAY.toString(), "").replace("#", "");
    return Integer.parseInt(text);
  }
}
