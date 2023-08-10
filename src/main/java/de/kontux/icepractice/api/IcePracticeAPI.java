package de.kontux.icepractice.api;

import de.kontux.icepractice.api.arena.ArenaHandler;
import de.kontux.icepractice.api.arena.IcePracticeArena;
import de.kontux.icepractice.api.config.MatchMessages;
import de.kontux.icepractice.api.config.PluginSettings;
import de.kontux.icepractice.api.gui.InventoryGui;
import de.kontux.icepractice.api.kit.IcePracticeKit;
import de.kontux.icepractice.api.kit.KitHandler;
import de.kontux.icepractice.api.locations.IcePracticeSpawnpoint;
import de.kontux.icepractice.api.match.IcePracticeFight;
import de.kontux.icepractice.api.match.IcePracticeFightRegistry;
import de.kontux.icepractice.api.match.misc.FightStatistics;
import de.kontux.icepractice.api.match.misc.MatchInventory;
import de.kontux.icepractice.api.nms.NmsApi;
import de.kontux.icepractice.api.playerstates.PlayerStateManager;
import de.kontux.icepractice.api.protocol.IcePracticeEntityHider;
import de.kontux.icepractice.api.user.UserData;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

@Deprecated
public final class IcePracticeAPI {
  private static IcePractice practice;
  
  public static void setIcePractice(Plugin plugin, IcePractice icePractice) {
    if (practice != null)
      throw new IllegalStateException("Practice instance for IcePracticeAPI has already been set."); 
    plugin.getServer().getConsoleSender().sendMessage(ChatColor.BLUE + "[IcePractice] " + ChatColor.YELLOW + "Initializing IcePractice API...");
    if (plugin.getName().equals("IcePractice")) {
      practice = icePractice;
      log(ChatColor.GREEN + "IcePracticeAPI is on version " + icePractice.getVersion());
    } else {
      log(plugin.getName() + " attempted to set the IcePractice instance. Only IcePractice itself may do that!");
    } 
  }
  
  public static IcePracticeKit getKit(String name) {
    return getKitHandler().getKit(name);
  }
  
  public static IcePracticeArena getArena(String name) {
    return getArenaHandler().getArena(name);
  }
  
  public static NmsApi getNmsApi() {
    return practice.getNmsApi();
  }
  
  public static IcePracticeFight getFightByPlayer(Player player) {
    return practice.getFightByPlayer(player);
  }
  
  public static void log(String message) {
    practice.log(message);
  }
  
  public static void openInventoryMenu(Player player, InventoryGui inventory) {
    practice.registerInventoryMenu(player, inventory);
  }
  
  public static PluginSettings getPluginSettings() {
    return practice.getPluginSettings();
  }
  
  public static IcePracticeSpawnpoint getSpawnpointManager() {
    return practice.getSpawnpointManager();
  }
  
  public static MatchMessages getMatchMessages() {
    return practice.getMatchMessages();
  }
  
  public static KitHandler getKitHandler() {
    return practice.getKitHandler();
  }
  
  public static ArenaHandler getArenaHandler() {
    return practice.getArenaHandler();
  }
  
  public static PlayerStateManager getPlayerStateManager() {
    return practice.getPlayerStateManager();
  }
  
  public static MatchInventory generateMatchInventory(Player player, IcePracticeFight fight) {
    return practice.generateMatchInventory(player, fight);
  }
  
  public static FightStatistics constructFightStatistics(IcePracticeFight fight) {
    return practice.constructFightStatistics(fight);
  }
  
  public static UserData getUserData(Player player) {
    return practice.getUserData(player);
  }
  
  public static void broadcast(List<Player> players, String message) {
    practice.broadcast(players, message);
  }
  
  public static IcePracticeFightRegistry getFightRegistry() {
    return practice.getFightRegistry();
  }
  
  public static IcePracticeEntityHider getEntityHider() {
    return practice.getEntityHider();
  }
  
  public static ChatColor getPrimary() {
    return getPluginSettings().getPrimaryColour();
  }
  
  public static ChatColor getSecondary() {
    return getPluginSettings().getSecondaryColour();
  }
}
