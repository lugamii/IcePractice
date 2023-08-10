package de.kontux.icepractice.guis;

import de.kontux.icepractice.api.gui.InventoryGui;
import de.kontux.icepractice.configs.Settings;
import de.kontux.icepractice.registries.EventRegistry;
import de.kontux.icepractice.tournaments.Tournament;
import de.kontux.icepractice.util.LangUtil;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class EventJoinInventory extends InventoryGui {
  private final List<Tournament> startingEvents = EventRegistry.getStarting();
  
  public EventJoinInventory(Player player) {
    super(player, Settings.PRIMARY + "All running events:", 45);
  }
  
  protected void setItems() {
    for (Tournament event : this.startingEvents) {
      String name = LangUtil.capitalize(event.getType().toString().toLowerCase());
      ItemStack item = new ItemStack(Material.CLAY);
      ItemMeta itemMeta = item.getItemMeta();
      itemMeta.setDisplayName(Settings.SECONDARY + name);
      List<String> lore = new ArrayList<>();
      lore.add("");
      lore.add(Settings.SECONDARY + "Host: " + Settings.PRIMARY + event.getHost().getDisplayName());
      if (event.isRunning()) {
        lore.add(Settings.SECONDARY + "Status:" + Settings.PRIMARY + " Running");
        lore.add(ChatColor.GRAY + "Click to join");
      } else {
        lore.add(Settings.SECONDARY + "Status:" + Settings.PRIMARY + " Starting");
        lore.add(ChatColor.GRAY + "Click to spectate");
      } 
      if (event.isProtected())
        lore.add(ChatColor.GRAY + "Password Protected"); 
      itemMeta.setLore(lore);
      item.setItemMeta(itemMeta);
      this.inventory.addItem(new ItemStack[] { item });
    } 
  }
  
  public void runAction(ItemStack item) {
    String name = item.getItemMeta().getDisplayName();
    String eventName = name.replace(Settings.SECONDARY.toString(), "");
    Tournament validEvent = null;
    for (Tournament current : this.startingEvents) {
      if (eventName.equals(LangUtil.capitalize(current.getType().toString().toLowerCase())))
        validEvent = current; 
    } 
    if (validEvent != null) {
      validEvent.join(this.player);
    } else {
      this.player.sendMessage("§cError while trying to enter the event.");
    } 
    this.player.closeInventory();
  }
}
