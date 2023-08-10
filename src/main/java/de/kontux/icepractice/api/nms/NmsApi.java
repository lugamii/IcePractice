package de.kontux.icepractice.api.nms;

import java.util.List;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public interface NmsApi {
  void sendEntityStatusPacket(Entity paramEntity, List<Player> paramList, byte paramByte);
  
  void sendWorldEventPacket(Player paramPlayer, int paramInt1, double paramDouble1, double paramDouble2, double paramDouble3, int paramInt2);
  
  void sendEntityVelocityPacket(Entity paramEntity, double paramDouble1, double paramDouble2, double paramDouble3, List<Player> paramList);
  
  void sendBlockChangePacket(Block paramBlock, Player paramPlayer);
  
  void injectPacketListener(Player paramPlayer);
  
  int getPing(Player paramPlayer);
}
