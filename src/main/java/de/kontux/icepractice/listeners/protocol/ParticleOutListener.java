package de.kontux.icepractice.listeners.protocol;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import de.kontux.icepractice.protocol.EntityHider;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class ParticleOutListener extends PacketAdapter {
  public static Player next;
  
  public ParticleOutListener(Plugin plugin) {
    super(plugin, new PacketType[] { PacketType.Play.Server.WORLD_PARTICLES });
  }
  
  public void onPacketSending(PacketEvent event) {
    if (((Integer)event.getPacket().getIntegers().read(0)).intValue() == 23 && next != null && !EntityHider.getInstance().canSee(event.getPlayer(), (Entity)next))
      event.setCancelled(true); 
  }
}
