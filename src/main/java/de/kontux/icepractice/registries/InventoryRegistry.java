package de.kontux.icepractice.registries;

import de.kontux.icepractice.postiventories.AfterMatchInventory;
import java.util.HashMap;
import java.util.UUID;
import org.bukkit.entity.Player;

public class InventoryRegistry {
  private static final HashMap<UUID, AfterMatchInventory> INVENTORIES = new HashMap<>();
  
  public static void addInventory(Player player, AfterMatchInventory inventory) {
    INVENTORIES.put(player.getUniqueId(), inventory);
  }
  
  public static AfterMatchInventory getInventory(UUID uuid) {
    AfterMatchInventory inventory = null;
    for (UUID current : INVENTORIES.keySet()) {
      if (current.equals(uuid))
        inventory = INVENTORIES.get(uuid); 
    } 
    return inventory;
  }
}
