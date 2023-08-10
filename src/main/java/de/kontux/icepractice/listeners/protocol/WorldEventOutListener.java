package de.kontux.icepractice.listeners.protocol;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import de.kontux.icepractice.api.playerstates.PlayerState;
import de.kontux.icepractice.playermanagement.PlayerStates;
import de.kontux.icepractice.protocol.EntityHider;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class WorldEventOutListener extends PacketAdapter {
  public WorldEventOutListener(Plugin plugin) {
    super(plugin, new PacketType[] { PacketType.Play.Server.WORLD_EVENT });
  }
  
  public void onPacketSending(PacketEvent event) {
    if (PlayerStates.getInstance().getState(event.getPlayer()) != PlayerState.MATCH && PlayerStates.getInstance().getState(event.getPlayer()) != PlayerState.SPECTATING)
      return; 
    if (((Integer)event.getPacket().getIntegers().read(0)).intValue() == 2002) {
      Player nextParticle = (EntityHider.getInstance()).nextParticle;
      if (nextParticle == null || !EntityHider.getInstance().canSee(event.getPlayer(), (Entity)nextParticle))
        event.setCancelled(true); 
    } 
  }
}
