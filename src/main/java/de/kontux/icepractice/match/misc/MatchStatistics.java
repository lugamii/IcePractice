package de.kontux.icepractice.match.misc;

import de.kontux.icepractice.api.match.IcePracticeFight;
import de.kontux.icepractice.api.match.misc.FightStatistics;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class MatchStatistics implements FightStatistics {
  private final IcePracticeFight fight;
  
  private final List<Block> brokenBlocks = new ArrayList<>();
  
  private final HashMap<UUID, Integer> hits = new HashMap<>();
  
  private final HashMap<UUID, Integer> missedPots = new HashMap<>();
  
  private final HashMap<UUID, Integer> thrownPots = new HashMap<>();
  
  public MatchStatistics(IcePracticeFight fight) {
    this.fight = fight;
  }
  
  public void recoverArena() {
    for (Block block : this.brokenBlocks)
      block.getWorld().getBlockAt(block.getLocation()).setType(block.getType()); 
  }
  
  public void addHit(Player player) {
    this.hits.put(player.getUniqueId(), Integer.valueOf(((Integer)this.hits.getOrDefault(player.getUniqueId(), Integer.valueOf(0))).intValue() + 1));
  }
  
  public void addPotion(Player player, double intensity) {
    UUID uuid = player.getUniqueId();
    int currentThrownPots = ((Integer)this.thrownPots.getOrDefault(uuid, Integer.valueOf(0))).intValue();
    this.thrownPots.put(uuid, Integer.valueOf(currentThrownPots + 1));
    if (intensity < 0.6D) {
      int currentMissedPots = ((Integer)this.missedPots.getOrDefault(uuid, Integer.valueOf(0))).intValue();
      this.missedPots.put(uuid, Integer.valueOf(currentMissedPots + 1));
    } 
  }
  
  public void addBrokenBlock(Block block) {
    this.brokenBlocks.add(block);
  }
  
  public int getHitCount(UUID player) {
    return ((Integer)this.hits.getOrDefault(player, Integer.valueOf(0))).intValue();
  }
  
  public int getThrownPots(UUID player) {
    return ((Integer)this.thrownPots.getOrDefault(player, Integer.valueOf(0))).intValue();
  }
  
  public int getMissedPots(UUID player) {
    return ((Integer)this.missedPots.getOrDefault(player, Integer.valueOf(0))).intValue();
  }
}
