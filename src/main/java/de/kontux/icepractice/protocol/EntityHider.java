package de.kontux.icepractice.protocol;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.google.common.base.Preconditions;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import de.kontux.icepractice.IcePracticePlugin;
import de.kontux.icepractice.api.protocol.IcePracticeEntityHider;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.Map;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.world.ChunkUnloadEvent;
import org.bukkit.plugin.Plugin;

public class EntityHider implements Listener, IcePracticeEntityHider {
  private static final EntityHider INSTANCE = new EntityHider(IcePracticePlugin.getInstance(), Policy.WHITELIST);
  
  protected final Policy policy;
  
  private final ProtocolManager manager;
  
  public Player nextParticle = null;
  
  protected Table<Integer, Integer, Boolean> observerEntityMap = HashBasedTable.create();
  
  private EntityHider(IcePracticePlugin plugin, Policy policy) {
    this.policy = policy;
    this.manager = plugin.getProtocolManager();
    plugin.getServer().getPluginManager().registerEvents(constructBukkit(), (Plugin)plugin);
  }
  
  public static EntityHider getInstance() {
    return INSTANCE;
  }
  
  private boolean setVisibility(Player observer, int entityID, boolean visible) {
    switch (this.policy) {
      case BLACKLIST:
        return !setMembership(observer, entityID, !visible);
      case WHITELIST:
        return setMembership(observer, entityID, visible);
    } 
    throw new IllegalArgumentException("Unknown policy: " + this.policy);
  }
  
  protected boolean setMembership(Player observer, int entityID, boolean member) {
    if (member)
      return (this.observerEntityMap.put(Integer.valueOf(observer.getEntityId()), Integer.valueOf(entityID), Boolean.valueOf(true)) != null); 
    return (this.observerEntityMap.remove(Integer.valueOf(observer.getEntityId()), Integer.valueOf(entityID)) != null);
  }
  
  protected boolean getMembership(Player observer, int entityID) {
    return this.observerEntityMap.contains(Integer.valueOf(observer.getEntityId()), Integer.valueOf(entityID));
  }
  
  public boolean isVisible(Player observer, int entityID) {
    boolean presence = getMembership(observer, entityID);
    return (((this.policy == Policy.WHITELIST)) == presence || this.manager.getEntityFromID(observer.getWorld(), entityID) instanceof org.bukkit.entity.ArmorStand);
  }
  
  private void removeEntity(Entity entity) {
    int entityID = entity.getEntityId();
    for (Map<Integer, Boolean> maps : (Iterable<Map<Integer, Boolean>>)this.observerEntityMap.rowMap().values())
      maps.remove(Integer.valueOf(entityID)); 
  }
  
  public void removePlayer(Player player) {
    this.observerEntityMap.rowMap().remove(Integer.valueOf(player.getEntityId()));
  }
  
  private Listener constructBukkit() {
    return new Listener() {
        @EventHandler
        public void onEntityDeath(EntityDeathEvent e) {
          EntityHider.this.removeEntity((Entity)e.getEntity());
        }
        
        @EventHandler
        public void onChunkUnload(ChunkUnloadEvent e) {
          for (Entity entity : e.getChunk().getEntities())
            EntityHider.this.removeEntity(entity); 
        }
      };
  }
  
  public final boolean toggleEntity(Player observer, Entity entity) {
    if (isVisible(observer, entity.getEntityId()))
      return hideEntity(observer, entity); 
    return !showEntity(observer, entity);
  }
  
  public final boolean showEntity(Player observer, Entity entity) {
    validate(observer, entity);
    boolean hiddenBefore = !setVisibility(observer, entity.getEntityId(), true);
    if (this.manager != null && hiddenBefore)
      this.manager.updateEntity(entity, Collections.singletonList(observer)); 
    return hiddenBefore;
  }
  
  public final boolean hideEntity(Player observer, Entity entity) {
    validate(observer, entity);
    boolean visibleBefore = setVisibility(observer, entity.getEntityId(), false);
    if (visibleBefore) {
      PacketContainer destroyEntity = new PacketContainer(PacketType.Play.Server.ENTITY_DESTROY);
      destroyEntity.getIntegerArrays().write(0, new int[] { entity.getEntityId() });
      try {
        this.manager.sendServerPacket(observer, destroyEntity);
      } catch (InvocationTargetException e) {
        throw new RuntimeException("Cannot send server packet.", e);
      } 
    } 
    return visibleBefore;
  }
  
  public final boolean canSee(Player observer, Entity entity) {
    validate(observer, entity);
    return isVisible(observer, entity.getEntityId());
  }
  
  private void validate(Player observer, Entity entity) {
    Preconditions.checkNotNull(observer, "observer cannot be NULL.");
    Preconditions.checkNotNull(entity, "entity cannot be NULL.");
  }
  
  public enum Policy {
    WHITELIST, BLACKLIST;
  }
}
