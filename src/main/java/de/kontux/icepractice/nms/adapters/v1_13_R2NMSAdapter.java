package de.kontux.icepractice.nms.adapters;

import io.netty.channel.ChannelDuplexHandler;
import java.util.List;
import net.minecraft.server.v1_13_R2.BlockPosition;
import net.minecraft.server.v1_13_R2.IBlockAccess;
import net.minecraft.server.v1_13_R2.Packet;
import net.minecraft.server.v1_13_R2.PacketPlayOutBlockChange;
import net.minecraft.server.v1_13_R2.PacketPlayOutEntityStatus;
import net.minecraft.server.v1_13_R2.PacketPlayOutEntityVelocity;
import net.minecraft.server.v1_13_R2.PacketPlayOutWorldEvent;
import net.minecraft.server.v1_13_R2.WorldServer;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_13_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_13_R2.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_13_R2.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class v1_13_R2NMSAdapter implements NMSAdapter {
  public void sendBlockChangePacket(Player player, Block block) {
    WorldServer worldServer = ((CraftWorld)block.getWorld()).getHandle();
    PacketPlayOutBlockChange packet = new PacketPlayOutBlockChange((IBlockAccess)worldServer, new BlockPosition(block.getX(), block.getY(), block.getZ()));
    (((CraftPlayer)player).getHandle()).playerConnection.sendPacket((Packet)packet);
  }
  
  public void injectPacketListener(Player player, ChannelDuplexHandler handler) {}
  
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
      if (!entity.equals(player))
        (((CraftPlayer)player).getHandle()).playerConnection.sendPacket((Packet)new PacketPlayOutEntityStatus(((CraftEntity)entity).getHandle(), status)); 
    } 
  }
  
  public void sendEntityVelocityPacket(Entity entity, double x, double y, double z, List<Player> sendPacketTo) {
    PacketPlayOutEntityVelocity packet = new PacketPlayOutEntityVelocity(entity.getEntityId(), x, y, z);
    for (Player player : sendPacketTo)
      (((CraftPlayer)player).getHandle()).playerConnection.sendPacket((Packet)packet); 
  }
}
