package de.kontux.icepractice.tournaments;

import de.kontux.icepractice.configs.Settings;
import de.kontux.icepractice.util.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public enum EventType {
  SUMO(ItemBuilder.create(Material.LEASH, Settings.PRIMARY + "Sumo Event", null)),
  KOTH(ItemBuilder.create(Material.BEACON, Settings.PRIMARY + "King of the Hill", null)),
  TOURNAMENT(ItemBuilder.create(Material.DIAMOND_SWORD, Settings.PRIMARY + "Tournament", null));
  
  private final ItemStack icon;
  
  EventType(ItemStack icon) {
    this.icon = icon;
  }
  
  public static EventType getByItem(ItemStack item) {
    if (item.isSimilar(SUMO.getIcon()))
      return SUMO; 
    if (item.isSimilar(KOTH.getIcon()))
      return KOTH; 
    if (item.isSimilar(TOURNAMENT.getIcon()))
      return TOURNAMENT; 
    return null;
  }
  
  public ItemStack getIcon() {
    return this.icon;
  }
}
