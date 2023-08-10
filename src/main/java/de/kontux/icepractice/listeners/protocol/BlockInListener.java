package de.kontux.icepractice.listeners.protocol;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.BlockPosition;
import de.kontux.icepractice.IcePracticePlugin;
import de.kontux.icepractice.api.playerstates.PlayerState;
import de.kontux.icepractice.playermanagement.PlayerStates;
import de.kontux.icepractice.protocol.EntityHider;
import de.kontux.icepractice.protocol.ProtocolUtil;
import de.kontux.icepractice.registries.FightRegistry;
import java.util.Set;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class BlockInListener extends PacketAdapter {
  public BlockInListener(Plugin plugin) {
    super(plugin, new PacketType[] { PacketType.Play.Client.BLOCK_PLACE });
  }
  
  public void onPacketReceiving(PacketEvent event) {
    if (PlayerStates.getInstance().getState(event.getPlayer()) != PlayerState.MATCH && PlayerStates.getInstance().getState(event.getPlayer()) != PlayerState.STARTING_MATCH)
      return; 
    if (processBlockPlacePacket(event.getPlayer(), event.getPacket()))
      event.setCancelled(true); 
  }
  
  private boolean processBlockPlacePacket(Player player, PacketContainer packet) {
    ItemStack item = player.getItemInHand();
    Material material = item.getType();
    int blockFaceId = ((Integer)packet.getIntegers().read(0)).intValue();
    BlockFace face = ProtocolUtil.getFaceById(blockFaceId);
    PlayerInteractEvent event = fireInteractEvent(player, item, face);
    if (event.isCancelled())
      return true; 
    if (material == null || material == Material.AIR)
      return false; 
    double x = ((BlockPosition)packet.getBlockPositionModifier().read(0)).getX();
    double y = ((BlockPosition)packet.getBlockPositionModifier().read(0)).getY();
    double z = ((BlockPosition)packet.getBlockPositionModifier().read(0)).getZ();
    Location location = ProtocolUtil.getLocationAtFace(player.getWorld(), x, y, z, face);
    if (blockFaceId == 255) {
      if (material == Material.BUCKET || material == Material.LAVA_BUCKET || material == Material.WATER_BUCKET) {
        processBucketUse(player, item, location);
        return true;
      } 
      return false;
    } 
    if (!material.isBlock())
      return false; 
    if (player.getLocation().getX() == location.getX() && player.getLocation().getZ() == location.getZ() && (player.getLocation().getY() == location.getY() || player.getLocation().getY() == location.getY() + 1.0D))
      return false; 
    if (!FightRegistry.getInstance().getFightByPlayer(player).getKit().isBuild())
      return false; 
    sendBlockChangeToSeenPlayers(player, item, location);
    item.setAmount(item.getAmount() - 1);
    return true;
  }
  
  private void processBucketUse(Player player, ItemStack item, Location location) {
    Material material = item.getType();
    if (material == Material.WATER_BUCKET || material == Material.BUCKET) {
      item.setType(Material.WATER);
    } else if (material == Material.LAVA_BUCKET) {
      item.setType(Material.LAVA);
    } 
    sendBlockChangeToSeenPlayers(player, item, location);
  }
  
  private PlayerInteractEvent fireInteractEvent(Player player, ItemStack item, BlockFace face) {
    Action action = (face != null) ? Action.RIGHT_CLICK_BLOCK : Action.RIGHT_CLICK_AIR;
    Block block = player.getTargetBlock((Set)null, 100);
    PlayerInteractEvent event = new PlayerInteractEvent(player, action, item, block, face);
    Bukkit.getScheduler().runTask((Plugin)IcePracticePlugin.getInstance(), () -> Bukkit.getPluginManager().callEvent((Event)event));
    return event;
  }
  
  private void sendBlockChangeToSeenPlayers(Player player, ItemStack item, Location location) {
    for (Player current : player.getWorld().getPlayers()) {
      if (EntityHider.getInstance().canSee(current, (Entity)player))
        current.sendBlockChange(location, item.getType(), item.getData().getData()); 
    } 
  }
}
