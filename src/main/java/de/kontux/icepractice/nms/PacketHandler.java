package de.kontux.icepractice.nms;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import org.bukkit.Bukkit;

public class PacketHandler extends ChannelDuplexHandler {
  private static final String NMS_VERSION = Bukkit.getServer().getClass().getPackage().getName().replace("org.bukkit.craftbukkit.", "");
  
  public void channelRead(ChannelHandlerContext channelHandlerContext, Object o) {
    if (processPacket(o))
      channelHandlerContext.flush(); 
  }
  
  private boolean processPacket(Object o) {
    String name = o.getClass().getSimpleName();
    if (!name.contains("Flying"))
      System.out.println("Packet Name:" + name); 
    return false;
  }
}
