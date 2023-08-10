package de.kontux.icepractice.api.match.misc;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface MatchInventory {
  void initializeInventory();
  
  Player getNext();
  
  Player getTarget();
  
  ItemStack[] getContents();
}
