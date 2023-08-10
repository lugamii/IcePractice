package de.kontux.icepractice.guis.eventsettings;

import de.kontux.icepractice.tournaments.EventType;
import de.kontux.icepractice.util.ItemBuilder;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class TournamentSettingsInventory extends EventSettingsInventory {
  public TournamentSettingsInventory(Player player) {
    super(player, EventType.TOURNAMENT);
  }
  
  protected void setItems() {
    List<String> lore = new ArrayList<>();
    lore.add(ChatColor.GRAY + "Feel free to join my discord server to stay up to date : D");
    lore.add(ChatColor.GRAY + "https://discord.gg/ed4vQbw");
    ItemStack item = ItemBuilder.create(Material.CHEST, ChatColor.GREEN + "Tournaments are still under development.", lore);
    this.inventory.setItem(13, item);
  }
}
