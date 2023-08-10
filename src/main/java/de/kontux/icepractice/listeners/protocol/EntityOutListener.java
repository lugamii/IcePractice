package de.kontux.icepractice.listeners.protocol;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import de.kontux.icepractice.IcePracticePlugin;
import de.kontux.icepractice.protocol.EntityHider;
import org.bukkit.plugin.Plugin;

public class EntityOutListener extends PacketAdapter {
  public EntityOutListener(IcePracticePlugin plugin) {
    super((Plugin)plugin, new PacketType[] { 
          PacketType.Play.Server.ENTITY_EQUIPMENT, PacketType.Play.Server.BED, PacketType.Play.Server.ANIMATION, PacketType.Play.Server.NAMED_ENTITY_SPAWN, PacketType.Play.Server.COLLECT, PacketType.Play.Server.SPAWN_ENTITY, PacketType.Play.Server.SPAWN_ENTITY_LIVING, PacketType.Play.Server.SPAWN_ENTITY_PAINTING, PacketType.Play.Server.SPAWN_ENTITY_EXPERIENCE_ORB, PacketType.Play.Server.ENTITY_VELOCITY, 
          PacketType.Play.Server.REL_ENTITY_MOVE, PacketType.Play.Server.ENTITY_LOOK, PacketType.Play.Server.REL_ENTITY_MOVE_LOOK, PacketType.Play.Server.REL_ENTITY_MOVE, PacketType.Play.Server.ENTITY_TELEPORT, PacketType.Play.Server.ENTITY_HEAD_ROTATION, PacketType.Play.Server.ENTITY_STATUS, PacketType.Play.Server.ATTACH_ENTITY, PacketType.Play.Server.ENTITY_METADATA, PacketType.Play.Server.ENTITY_EFFECT, 
          PacketType.Play.Server.REMOVE_ENTITY_EFFECT, PacketType.Play.Server.BLOCK_BREAK_ANIMATION });
  }
  
  public void onPacketSending(PacketEvent event) {
    int entityId = ((Integer)event.getPacket().getIntegers().read(0)).intValue();
    if (!EntityHider.getInstance().isVisible(event.getPlayer(), entityId))
      event.setCancelled(true); 
  }
}
