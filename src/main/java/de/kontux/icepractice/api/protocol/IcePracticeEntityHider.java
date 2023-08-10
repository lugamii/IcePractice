package de.kontux.icepractice.api.protocol;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public interface IcePracticeEntityHider {
  boolean isVisible(Player paramPlayer, int paramInt);
  
  boolean toggleEntity(Player paramPlayer, Entity paramEntity);
  
  boolean showEntity(Player paramPlayer, Entity paramEntity);
  
  boolean hideEntity(Player paramPlayer, Entity paramEntity);
  
  boolean canSee(Player paramPlayer, Entity paramEntity);
}
