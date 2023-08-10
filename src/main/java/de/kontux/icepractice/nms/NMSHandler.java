package de.kontux.icepractice.nms;

import de.kontux.icepractice.IcePracticePlugin;
import de.kontux.icepractice.api.nms.NmsApi;
import de.kontux.icepractice.nms.adapters.NMSAdapter;
import de.kontux.icepractice.nms.adapters.v1_12_R1NMSAdapter;
import de.kontux.icepractice.nms.adapters.v1_13_R2NMSAdapter;
import de.kontux.icepractice.nms.adapters.v1_14_R1NMSAdapter;
import de.kontux.icepractice.nms.adapters.v1_15_R1NMSAdapter;
import de.kontux.icepractice.nms.adapters.v1_7_R4NMSAdapter;
import de.kontux.icepractice.nms.adapters.v1_8_R3NMSAdapter;
import java.util.List;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class NMSHandler implements NmsApi {
  private NMSAdapter adapter;
  
  public NMSHandler() {
    switch (IcePracticePlugin.nmsVersion) {
      case v1_7:
        this.adapter = (NMSAdapter)new v1_7_R4NMSAdapter();
        break;
      case v1_8:
        this.adapter = (NMSAdapter)new v1_8_R3NMSAdapter();
        break;
      case v1_12:
        this.adapter = (NMSAdapter)new v1_12_R1NMSAdapter();
        break;
      case v1_13:
        this.adapter = (NMSAdapter)new v1_13_R2NMSAdapter();
        break;
      case v1_14:
        this.adapter = (NMSAdapter)new v1_14_R1NMSAdapter();
        break;
      case v1_15:
        this.adapter = (NMSAdapter)new v1_15_R1NMSAdapter();
        break;
    } 
  }
  
  public void sendEntityStatusPacket(Entity entity, List<Player> sendPacketTo, byte status) {
    this.adapter.sendEntityStatusPacket(entity, sendPacketTo, status);
  }
  
  public void sendWorldEventPacket(Player player, int id, double x, double y, double z, int data) {
    this.adapter.sendWorldEventPacket(player, id, x, y, z, data);
  }
  
  public void sendEntityVelocityPacket(Entity entity, double x, double y, double z, List<Player> sendPacketTo) {
    this.adapter.sendEntityVelocityPacket(entity, x, y, z, sendPacketTo);
  }
  
  public void sendBlockChangePacket(Block block, Player player) {
    this.adapter.sendBlockChangePacket(player, block);
  }
  
  public void injectPacketListener(Player player) {
    this.adapter.injectPacketListener(player, new PacketHandler());
  }
  
  public int getPing(Player player) {
    return this.adapter.getPing(player);
  }
}
