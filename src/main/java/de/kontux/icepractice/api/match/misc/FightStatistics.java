package de.kontux.icepractice.api.match.misc;

import java.util.UUID;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public interface FightStatistics {
  void recoverArena();
  
  void addHit(Player paramPlayer);
  
  void addPotion(Player paramPlayer, double paramDouble);
  
  void addBrokenBlock(Block paramBlock);
  
  int getHitCount(UUID paramUUID);
  
  int getThrownPots(UUID paramUUID);
  
  int getMissedPots(UUID paramUUID);
}
