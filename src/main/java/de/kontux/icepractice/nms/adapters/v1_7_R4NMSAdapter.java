package de.kontux.icepractice.nms.adapters;

import io.netty.channel.ChannelDuplexHandler;
import java.util.List;
import net.minecraft.server.v1_7_R4.Packet;
import net.minecraft.server.v1_7_R4.PacketPlayOutBlockChange;
import net.minecraft.server.v1_7_R4.PacketPlayOutEntityStatus;
import net.minecraft.server.v1_7_R4.PacketPlayOutEntityVelocity;
import net.minecraft.server.v1_7_R4.PacketPlayOutWorldEvent;
import net.minecraft.server.v1_7_R4.World;
import net.minecraft.server.v1_7_R4.WorldServer;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_7_R4.CraftWorld;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class v1_7_R4NMSAdapter implements NMSAdapter {
  public void injectPacketListener(Player player, ChannelDuplexHandler handler) {}
  
  public void addPluginChannel(Player player, String channelName) {}
  
  public void sendWorldEventPacket(Player player, int id, double x, double y, double z, int data) {
    PacketPlayOutWorldEvent packet = new PacketPlayOutWorldEvent(id, (int)x, (int)y, (int)z, data, false);
    (((CraftPlayer)player).getHandle()).playerConnection.sendPacket((Packet)packet);
  }
  
  public void sendBlockChangePacket(Player player, Block block) {
    WorldServer worldServer = ((CraftWorld)block.getWorld()).getHandle();
    PacketPlayOutBlockChange packet = new PacketPlayOutBlockChange(block.getX(), block.getY(), block.getZ(), (World)worldServer);
    (((CraftPlayer)player).getHandle()).playerConnection.sendPacket((Packet)packet);
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
