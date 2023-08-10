package de.kontux.icepractice.nms.adapters;

import io.netty.channel.Channel;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandler;
import java.util.List;
import net.minecraft.server.v1_12_R1.BlockPosition;
import net.minecraft.server.v1_12_R1.Packet;
import net.minecraft.server.v1_12_R1.PacketPlayOutBlockChange;
import net.minecraft.server.v1_12_R1.PacketPlayOutEntityStatus;
import net.minecraft.server.v1_12_R1.PacketPlayOutEntityVelocity;
import net.minecraft.server.v1_12_R1.PacketPlayOutWorldEvent;
import net.minecraft.server.v1_12_R1.World;
import net.minecraft.server.v1_12_R1.WorldServer;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class v1_12_R1NMSAdapter implements NMSAdapter {
  public void sendBlockChangePacket(Player player, Block block) {
    WorldServer worldServer = ((CraftWorld)block.getWorld()).getHandle();
    PacketPlayOutBlockChange packet = new PacketPlayOutBlockChange((World)worldServer, new BlockPosition(block.getX(), block.getY(), block.getZ()));
    (((CraftPlayer)player).getHandle()).playerConnection.sendPacket((Packet)packet);
  }
  
  public void injectPacketListener(Player player, ChannelDuplexHandler handler) {
    Channel channel = (((CraftPlayer)player).getHandle()).playerConnection.networkManager.channel;
    channel.pipeline().addBefore("icepractice", "decoder", (ChannelHandler)handler);
  }
  
  public void sendWorldEventPacket(Player player, int id, double x, double y, double z, int data) {
    BlockPosition position = new BlockPosition(x, y, z);
    PacketPlayOutWorldEvent packet = new PacketPlayOutWorldEvent(id, position, data, false);
    (((CraftPlayer)player).getHandle()).playerConnection.sendPacket((Packet)packet);
  }
  
  public void addPluginChannel(Player player, String channelName) {
    ((CraftPlayer)player).addChannel(channelName);
  }
  
  public int getPing(Player player) {
    return (((CraftPlayer)player).getHandle()).ping;
  }
  
  public void sendEntityStatusPacket(Entity entity, List<Player> sendPacketTo, byte status) {
    for (Player player : sendPacketTo) {
      if (!player.equals(entity))
        (((CraftPlayer)player).getHandle()).playerConnection.sendPacket((Packet)new PacketPlayOutEntityStatus(((CraftEntity)entity).getHandle(), status)); 
    } 
  }
  
  public void sendEntityVelocityPacket(Entity entity, double x, double y, double z, List<Player> sendPacketTo) {
    PacketPlayOutEntityVelocity packet = new PacketPlayOutEntityVelocity(entity.getEntityId(), x, y, z);
    for (Player player : sendPacketTo)
      (((CraftPlayer)player).getHandle()).playerConnection.sendPacket((Packet)packet); 
  }
}
