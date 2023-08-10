package de.kontux.icepractice.guis.party;

import de.kontux.icepractice.api.gui.InventoryGui;
import de.kontux.icepractice.configs.Settings;
import de.kontux.icepractice.party.Party;
import de.kontux.icepractice.registries.PartyRegistry;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class PartyDuelInventory extends InventoryGui {
  private final Party party;
  
  public PartyDuelInventory(Player player, Party party) {
    super(player, Settings.PRIMARY + "Select a party:", 45);
    this.party = party;
  }
  
  protected void setItems() {
    for (Party current : PartyRegistry.getAllParties()) {
      if (!current.equals(this.party)) {
        String itemName = Settings.SECONDARY + current.getLeader().getDisplayName() + "'s " + Settings.PRIMARY + "Party";
        ItemStack item = new ItemStack(Material.SKULL_ITEM);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(itemName);
        List<String> lore = new ArrayList<>();
        if (current.getOnGoingFight() != null) {
          lore.add(ChatColor.RED + "Busy");
        } else {
          lore.add(ChatColor.GRAY + "Click to duel");
        } 
        lore.add(ChatColor.GREEN + "At Spawn");
        meta.setLore(lore);
        item.setItemMeta(meta);
        this.inventory.addItem(new ItemStack[] { item });
      } 
    } 
  }
  
  public void runAction(ItemStack item) {
    String name = item.getItemMeta().getDisplayName();
    String leaderName = name.replace("'s " + Settings.PRIMARY + "Party", "").replace(Settings.SECONDARY.toString(), "");
    Player opponentLeader = Bukkit.getPlayer(leaderName);
    Party opponentParty = PartyRegistry.getPartyByPlayer(opponentLeader);
    if (opponentParty != null) {
      (new PartyDuelKitSelectionInventory(this.player, this.party, opponentParty)).openMenu();
    } else {
      this.player.sendMessage(ChatColor.RED + "This party was closed.");
      this.player.closeInventory();
    } 
  }
}
