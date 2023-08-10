package de.kontux.icepractice.guis.eventsettings;

import de.kontux.icepractice.api.kit.IcePracticeKit;
import de.kontux.icepractice.configs.Settings;
import de.kontux.icepractice.kits.KitManager;
import de.kontux.icepractice.tournaments.EventType;
import de.kontux.icepractice.util.ItemBuilder;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class SumoSettingsInventory extends EventSettingsInventory {
  public SumoSettingsInventory(Player player) {
    super(player, EventType.SUMO);
    this.kit = (IcePracticeKit)KitManager.getInstance().getSumoEventKit();
  }
  
  protected void setItems() {
    List<String> teamLore = new ArrayList<>();
    teamLore.add(Settings.PRIMARY + "Current team size: " + Settings.SECONDARY + this.teamSize);
    ItemStack teamItem = ItemBuilder.create(Material.SKULL_ITEM, Settings.SECONDARY + "Set team size:", teamLore);
    ItemStack plusItem = ItemBuilder.create(Material.STONE_BUTTON, "+", null);
    ItemStack minusItem = ItemBuilder.create(Material.STONE_BUTTON, "-", null);
    if (Settings.ALLOW_PASSWORD_PROTECTION) {
      String name;
      if (this.password != null) {
        name = Settings.PRIMARY + "Current password: " + Settings.SECONDARY + this.password;
      } else {
        name = Settings.PRIMARY + "Click to set the event's password";
      } 
      ItemStack passwordItem = ItemBuilder.create(Material.ENDER_CHEST, name, null);
      this.inventory.setItem(3, plusItem);
      this.inventory.setItem(21, minusItem);
      this.inventory.setItem(12, teamItem);
      this.inventory.setItem(14, passwordItem);
    } else {
      this.inventory.setItem(4, plusItem);
      this.inventory.setItem(22, minusItem);
      this.inventory.setItem(13, teamItem);
    } 
    this.inventory.setItem(26, this.startButton);
  }
}
