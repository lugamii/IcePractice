package de.kontux.icepractice.queue;

import de.kontux.icepractice.api.kit.IcePracticeKit;
import de.kontux.icepractice.configs.Settings;
import de.kontux.icepractice.configs.repositories.messages.QueueMessageRepository;
import de.kontux.icepractice.items.join.JoinItemManager;
import de.kontux.icepractice.match.types.Duel;
import de.kontux.icepractice.scoreboard.ScoreboardManager;
import de.kontux.icepractice.userdata.PlayerDataManager;
import de.kontux.icepractice.util.ItemBuilder;
import java.util.HashMap;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class Queue {
  public static final ItemStack ITEM = ItemBuilder.create(Settings.QUEUE_LEAVE_ITEM, Settings.QUEUE_LEAVE_ITEM_NAME, null);
  
  private static final HashMap<UUID, Integer> TASK_IDS = new HashMap<>();
  
  private static final QueueMessageRepository messages = new QueueMessageRepository();
  
  private static final HashMap<IcePracticeKit, Player> UNRANKED_QUEUE = new HashMap<>();
  
  private static final HashMap<IcePracticeKit, Player> RANKED_QUEUE = new HashMap<>();
  
  public static void addToQueue(Player player, IcePracticeKit kit, boolean ranked) {
    Player opponent = getOpponent(kit, ranked);
    if (opponent != null) {
      removeFromQueue(opponent, ranked);
      (new Duel(player, opponent, kit, ranked)).runMatch();
    } else {
      if (ranked) {
        RANKED_QUEUE.put(kit, player);
        player.sendMessage(messages.getRankedJoinMessage(kit.getName(), PlayerDataManager.getSettingsData(player.getUniqueId()).getElo(kit)));
      } else {
        UNRANKED_QUEUE.put(kit, player);
        player.sendMessage(messages.getUnrankedJoinMessage(kit.getName()));
      } 
      changeInventory(player);
      setBoard(player, kit);
    } 
  }
  
  private static void changeInventory(Player player) {
    PlayerInventory playerInventory = player.getInventory();
    playerInventory.clear();
    playerInventory.setItem(8, ITEM);
  }
  
  private static void setBoard(Player player, IcePracticeKit kit) {
    int taskId = (new QueueScoreboardUpdateTask(player, kit.getName())).start();
    TASK_IDS.put(player.getUniqueId(), Integer.valueOf(taskId));
  }
  
  private static Player getOpponent(IcePracticeKit kit, boolean ranked) {
    if (ranked) {
      for (IcePracticeKit currentKit : RANKED_QUEUE.keySet()) {
        if (currentKit.equals(kit))
          return RANKED_QUEUE.get(currentKit); 
      } 
    } else {
      for (IcePracticeKit currentKit : UNRANKED_QUEUE.keySet()) {
        if (currentKit.equals(kit))
          return UNRANKED_QUEUE.get(currentKit); 
      } 
    } 
    return null;
  }
  
  public static void removeFromQueue(Player player, boolean ranked) {
    IcePracticeKit kitToRemove = null;
    if (ranked) {
      for (IcePracticeKit kit : RANKED_QUEUE.keySet()) {
        if (((Player)RANKED_QUEUE.get(kit)).equals(player))
          kitToRemove = kit; 
      } 
      if (kitToRemove != null) {
        RANKED_QUEUE.remove(kitToRemove);
        player.sendMessage(messages.getLeaveMessage());
      } else {
        player.sendMessage("§cError while removing you from the queue.");
      } 
    } else {
      for (IcePracticeKit kit : UNRANKED_QUEUE.keySet()) {
        if (((Player)UNRANKED_QUEUE.get(kit)).equals(player))
          kitToRemove = kit; 
      } 
      if (kitToRemove != null) {
        UNRANKED_QUEUE.remove(kitToRemove);
        player.sendMessage(messages.getLeaveMessage());
      } else {
        player.sendMessage("§cError while removing you from the queue.");
      } 
    } 
    Bukkit.getScheduler().cancelTask(((Integer)TASK_IDS.get(player.getUniqueId())).intValue());
    ScoreboardManager.getInstance().setIdleBoard(player);
    JoinItemManager.getInstance().giveItems(player);
  }
  
  public static int getPlayersInQueue(IcePracticeKit kit, boolean ranked) {
    int playersInQueue = 0;
    if (ranked) {
      for (IcePracticeKit currentKit : RANKED_QUEUE.keySet()) {
        if (currentKit.equals(kit))
          playersInQueue++; 
      } 
    } else {
      for (IcePracticeKit currentKit : UNRANKED_QUEUE.keySet()) {
        if (currentKit.equals(kit))
          playersInQueue++; 
      } 
    } 
    return playersInQueue;
  }
  
  public static boolean isInQueue(Player player) {
    return (UNRANKED_QUEUE.containsValue(player) || RANKED_QUEUE.containsValue(player));
  }
  
  public static boolean getRanked(Player player) {
    for (IcePracticeKit kit : RANKED_QUEUE.keySet()) {
      if (((Player)RANKED_QUEUE.get(kit)).equals(player))
        return true; 
    } 
    return false;
  }
  
  public static ItemStack getItem() {
    return ITEM;
  }
}
