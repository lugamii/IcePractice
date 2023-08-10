package de.kontux.icepractice.nms.adapters;

import io.netty.channel.ChannelDuplexHandler;
import java.util.List;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public interface NMSAdapter {
  void injectPacketListener(Player paramPlayer, ChannelDuplexHandler paramChannelDuplexHandler);
  
  void addPluginChannel(Player paramPlayer, String paramString);
  
  void sendBlockChangePacket(Player paramPlayer, Block paramBlock);
  
  void sendWorldEventPacket(Player paramPlayer, int paramInt1, double paramDouble1, double paramDouble2, double paramDouble3, int paramInt2);
  
  int getPing(Player paramPlayer);
  
  void sendEntityStatusPacket(Entity paramEntity, List<Player> paramList, byte paramByte);
  
  void sendEntityVelocityPacket(Entity paramEntity, double paramDouble1, double paramDouble2, double paramDouble3, List<Player> paramList);
}
