package de.kontux.icepractice.listeners.interaction;

import de.kontux.icepractice.IcePracticePlugin;
import de.kontux.icepractice.api.kit.IcePracticeKit;
import de.kontux.icepractice.api.match.IcePracticeFight;
import de.kontux.icepractice.api.match.Spectatable;
import de.kontux.icepractice.api.playerstates.PlayerState;
import de.kontux.icepractice.api.user.CustomUserKit;
import de.kontux.icepractice.items.join.JoinItem;
import de.kontux.icepractice.items.join.JoinItemManager;
import de.kontux.icepractice.items.party.PartyItem;
import de.kontux.icepractice.items.party.PartyItemManager;
import de.kontux.icepractice.kiteditor.KitEditorHandler;
import de.kontux.icepractice.kiteditor.KitEditorRepository;
import de.kontux.icepractice.party.Party;
import de.kontux.icepractice.playermanagement.PlayerStates;
import de.kontux.icepractice.queue.PartyQueue;
import de.kontux.icepractice.queue.Queue;
import de.kontux.icepractice.registries.EventRegistry;
import de.kontux.icepractice.registries.FightRegistry;
import de.kontux.icepractice.registries.PartyRegistry;
import de.kontux.icepractice.tournaments.Tournament;
import de.kontux.icepractice.userdata.PlayerDataManager;
import de.kontux.icepractice.util.ConfigUtil;
import java.util.HashMap;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class InteractListener implements Listener {
  private static final HashMap<UUID, Short> CLICKS = new HashMap<>();
  
  private static final HashMap<UUID, Short> LAST_CPS = new HashMap<>();
  
  public static int getCps(Player player) {
    return ((Short)LAST_CPS.getOrDefault(player.getUniqueId(), Short.valueOf((short)0))).shortValue();
  }
  
  @EventHandler
  public void onInteract(PlayerInteractEvent event) {
    if (!ConfigUtil.useWorld(event.getPlayer().getWorld()))
      return; 
    if (event.getAction() == Action.LEFT_CLICK_AIR) {
      CLICKS.put(event.getPlayer().getUniqueId(), Short.valueOf((short)(((Short)CLICKS.getOrDefault(event.getPlayer().getUniqueId(), Short.valueOf((short)0))).shortValue() + 1)));
      return;
    } 
    if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
      Player player = event.getPlayer();
      switch (PlayerStates.getInstance().getState(player)) {
        case IDLE:
        case EVENT:
          runIdleAction(event);
          break;
        case MATCH:
        case STARTING_MATCH:
          runMatchAction(event);
          break;
        case SPECTATING:
          runSpectatorAction(event);
          break;
        case EDITOR:
          runEditorAction(event);
          break;
      } 
    } 
  }
  
  private void runIdleAction(PlayerInteractEvent event) {
    ItemStack item = event.getItem();
    if (item == null || item.getType() == Material.AIR)
      return; 
    Player player = event.getPlayer();
    int slot = player.getInventory().getHeldItemSlot();
    if (PartyRegistry.isInParty(player)) {
      PartyItem partyItem = PartyItemManager.getInstance().getPartyItem(item, slot);
      if (partyItem != null) {
        PartyItemManager.getInstance().runFunction(player, partyItem.getFunction());
        return;
      } 
      Party party = PartyRegistry.getPartyByPlayer(player);
      if (item.isSimilar(PartyQueue.PARTY_QUEUE_ITEM) && party.isLeader(player))
        party.leaveQueue(); 
    } else {
      JoinItem joinItem = JoinItemManager.getInstance().getJoinItem(item, slot);
      if (joinItem != null) {
        event.setCancelled(true);
        JoinItemManager.getInstance().runFunction(player, joinItem.getFunction());
      } else {
        String name = item.getItemMeta().getDisplayName();
        if (name == null)
          return; 
        if (item.isSimilar(Queue.ITEM)) {
          event.setCancelled(true);
          Queue.removeFromQueue(event.getPlayer(), Queue.getRanked(player));
        } else if (item.isSimilar(Tournament.LEAVE_ITEM)) {
          Tournament eventToLeave = EventRegistry.getEventByPlayer(player);
          if (eventToLeave != null)
            eventToLeave.leave(player); 
        } 
      } 
    } 
  }
  
  private void runMatchAction(PlayerInteractEvent event) {
    ItemStack item = event.getItem();
    if (item == null)
      return; 
    Player player = event.getPlayer();
    if (item.getType() == Material.AIR)
      return; 
    if (item.getType() == Material.BOOK) {
      IcePracticeKit kit = FightRegistry.getInstance().getFightByPlayer(player).getKit();
      CustomUserKit customKit = PlayerDataManager.getSettingsData(player.getUniqueId()).getCustomKit(kit, player.getInventory().getHeldItemSlot() + 1);
      if (customKit != null)
        customKit.equip(player); 
      return;
    } 
    if (item.getType() == Material.MUSHROOM_SOUP) {
      if (player.getHealth() >= player.getMaxHealth() - 7.0D) {
        player.setHealth(player.getMaxHealth());
      } else {
        player.setHealth(player.getHealth() + 7.0D);
        player.setSaturation(player.getSaturation() + 7.0F);
      } 
      player.getItemInHand().setType(Material.BOWL);
    } 
  }
  
  private void runEditorAction(PlayerInteractEvent event) {
    event.setCancelled(true);
    Player player = event.getPlayer();
    KitEditorHandler handler = (KitEditorHandler)KitEditorHandler.HANDLERS.get(player.getUniqueId());
    Block block = event.getClickedBlock();
    if (KitEditorRepository.isAnvil(block)) {
      handler.openSaveMenu();
    } else if (KitEditorRepository.isChest(block)) {
      handler.openEditMenu();
    } else if (KitEditorRepository.isSign(block)) {
      handler.removePlayer();
    } 
  }
  
  private void runSpectatorAction(PlayerInteractEvent event) {
    event.setCancelled(true);
    ItemStack item = event.getItem();
    if (item == null || item.getType() == Material.AIR)
      return; 
    Player player = event.getPlayer();
    if (item.isSimilar(Spectatable.LEAVE_ITEM)) {
      IcePracticeFight fight = FightRegistry.getInstance().getSpectatorFight(player);
      Party party = PartyRegistry.getPartyByPlayer(player);
      if (party != null) {
        party.leavePlayer(player);
      } else if (fight != null) {
        fight.removeSpectator(player, true);
      } 
    } 
  }
  
  public void startScheduler() {
    Bukkit.getScheduler().scheduleAsyncRepeatingTask((Plugin)IcePracticePlugin.getInstance(), () -> {
          LAST_CPS.clear();
          for (UUID uuid : CLICKS.keySet()) {
            short clicks = ((Short)CLICKS.getOrDefault(uuid, Short.valueOf((short)0))).shortValue();
            LAST_CPS.put(uuid, Short.valueOf(clicks));
          } 
          CLICKS.clear();
        },100L, 20L);
  }
}
