package de.kontux.icepractice.api.match;

import de.kontux.icepractice.api.util.ItemBuilder;
import java.util.List;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface Spectatable {
  public static final ItemStack LEAVE_ITEM = ItemBuilder.create(Material.REDSTONE, "§cBack To Spawn", null);
  
  void addSpectator(Player paramPlayer, boolean paramBoolean);
  
  void removeSpectator(Player paramPlayer, boolean paramBoolean);
  
  List<Player> getSpectators();
}
