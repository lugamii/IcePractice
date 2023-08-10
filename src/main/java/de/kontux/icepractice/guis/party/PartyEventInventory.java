package de.kontux.icepractice.guis.party;

import de.kontux.icepractice.api.gui.InventoryGui;
import de.kontux.icepractice.configs.Settings;
import de.kontux.icepractice.party.Party;
import de.kontux.icepractice.party.PartyEvent;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class PartyEventInventory extends InventoryGui {
  private final Party party;
  
  public PartyEventInventory(Player player, Party party) {
    super(player, Settings.PRIMARY + "Select an event:", 27);
    this.party = party;
  }
  
  protected void setItems() {
    ItemStack split = new ItemStack(Material.DIAMOND_SWORD);
    ItemStack ffa = new ItemStack(Material.GOLD_AXE);
    ItemStack redRover = new ItemStack(Material.SKULL_ITEM);
    ItemMeta splitMeta = split.getItemMeta();
    ItemMeta ffaMeta = ffa.getItemMeta();
    ItemMeta redRoverMeta = redRover.getItemMeta();
    splitMeta.setDisplayName(Settings.PRIMARY + "Split party");
    ffaMeta.setDisplayName(Settings.PRIMARY + "Party FFA");
    redRoverMeta.setDisplayName(Settings.PRIMARY + "Red Rover");
    split.setItemMeta(splitMeta);
    ffa.setItemMeta(ffaMeta);
    redRover.setItemMeta(redRoverMeta);
    this.inventory.setItem(11, split);
    this.inventory.setItem(13, ffa);
    this.inventory.setItem(15, redRover);
  }
  
  public void runAction(ItemStack item) {
    String name = item.getItemMeta().getDisplayName();
    PartyEvent event = null;
    if (item.getType() == Material.DIAMOND_SWORD && name.equals(Settings.PRIMARY + "Split party")) {
      event = PartyEvent.SPLIT_FIGHT;
    } else if (item.getType() == Material.GOLD_AXE && name.equals(Settings.PRIMARY + "Party FFA")) {
      event = PartyEvent.FFA;
    } else if (item.getType() == Material.SKULL_ITEM && name.equals(Settings.PRIMARY + "Red Rover")) {
      event = PartyEvent.RED_ROVER;
    } 
    if (event != null)
      (new PartyEventKitSelectionInventory(this.player, this.party, event)).openMenu(); 
  }
}
