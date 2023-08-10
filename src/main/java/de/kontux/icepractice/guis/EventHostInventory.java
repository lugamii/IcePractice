package de.kontux.icepractice.guis;

import de.kontux.icepractice.api.gui.InventoryGui;
import de.kontux.icepractice.configs.Settings;
import de.kontux.icepractice.tournaments.EventManager;
import de.kontux.icepractice.tournaments.EventType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class EventHostInventory extends InventoryGui {
  public EventHostInventory(Player player) {
    super(player, Settings.PRIMARY + "Select an event:", 27);
  }
  
  protected void setItems() {
    this.inventory.setItem(11, EventType.SUMO.getIcon());
    this.inventory.setItem(13, EventType.TOURNAMENT.getIcon());
    this.inventory.setItem(15, EventType.KOTH.getIcon());
  }
  
  public void runAction(ItemStack item) {
    EventType type = EventType.getByItem(item);
    if (type != null)
      (new EventManager()).openSettings(type, this.player); 
  }
}
