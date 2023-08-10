package de.kontux.icepractice.protocol;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import de.kontux.icepractice.IcePracticePlugin;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class ProtocolPacketSender {
  public static void sendEntityStatusPacket(Entity entity, byte status, List<Player> sendPacketTo) {
    PacketContainer packet = new PacketContainer(PacketType.Play.Server.ENTITY_STATUS);
    packet.getIntegers().write(0, Integer.valueOf(entity.getEntityId()));
    packet.getBytes().write(0, Byte.valueOf(status));
    for (Player player : sendPacketTo) {
      try {
        IcePracticePlugin.getInstance().getProtocolManager().sendServerPacket(player, packet);
      } catch (InvocationTargetException e) {
        throw new RuntimeException("Could not send entity status packet: " + e.getMessage());
      } 
    } 
  }
}
