package de.kontux.icepractice;

import de.kontux.icepractice.api.IcePractice;
import de.kontux.icepractice.api.IcePracticeAPI;
import de.kontux.icepractice.api.arena.ArenaHandler;
import de.kontux.icepractice.api.config.MatchMessages;
import de.kontux.icepractice.api.config.PluginSettings;
import de.kontux.icepractice.api.gui.InventoryGui;
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
import de.kontux.icepractice.arenas.ArenaManager;
import de.kontux.icepractice.configs.SettingsAdapter;
import de.kontux.icepractice.configs.repositories.messages.MatchMessageRepository;
import de.kontux.icepractice.kits.KitManager;
import de.kontux.icepractice.listeners.inventory.InventoryClickListener;
import de.kontux.icepractice.locations.SpawnPointAdapter;
import de.kontux.icepractice.match.misc.MatchStatistics;
import de.kontux.icepractice.playermanagement.PlayerStates;
import de.kontux.icepractice.postiventories.AfterMatchInventory;
import de.kontux.icepractice.protocol.EntityHider;
import de.kontux.icepractice.registries.FightRegistry;
import de.kontux.icepractice.userdata.PlayerDataManager;
import java.util.List;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

final class IcePracticeImpl implements IcePractice {
  private static boolean injected = false;
  
  private final IcePracticePlugin plugin;
  
  private final SettingsAdapter settings = new SettingsAdapter();
  
  private final SpawnPointAdapter spawnPointAdapter = new SpawnPointAdapter();
  
  private final MatchMessages matchMessages = (MatchMessages)new MatchMessageRepository();
  
  IcePracticeImpl(IcePracticePlugin plugin) {
    this.plugin = plugin;
  }
  
  final void initialize() {
    if (injected)
      throw new IllegalStateException("The API has already been injected!"); 
    injected = true;
    IcePracticeAPI.setIcePractice((Plugin)this.plugin, this);
  }
  
  public void log(String message) {
    this.plugin.log(message);
  }
  
  public String getVersion() {
    return this.plugin.getDescription().getVersion();
  }
  
  public NmsApi getNmsApi() {
    return (NmsApi)IcePracticePlugin.getNmsHandler();
  }
  
  public PlayerStateManager getPlayerStateManager() {
    return (PlayerStateManager)PlayerStates.getInstance();
  }
  
  public IcePracticeFightRegistry getFightRegistry() {
    return (IcePracticeFightRegistry)FightRegistry.getInstance();
  }
  
  public void broadcast(List<Player> players, String message) {
    IcePracticePlugin.broadCastMessage(players, message);
  }
  
  public void broadcast(List<Player> players, BaseComponent message) {
    IcePracticePlugin.broadCastMessage(players, message);
  }
  
  public MatchInventory generateMatchInventory(Player player, IcePracticeFight fight) {
    return (MatchInventory)new AfterMatchInventory(player, fight);
  }
  
  public MatchMessages getMatchMessages() {
    return this.matchMessages;
  }
  
  public IcePracticeSpawnpoint getSpawnpointManager() {
    return (IcePracticeSpawnpoint)this.spawnPointAdapter;
  }
  
  public UserData getUserData(Player player) {
    return (UserData)PlayerDataManager.getSettingsData(player.getUniqueId());
  }
  
  public FightStatistics constructFightStatistics(IcePracticeFight fight) {
    return (FightStatistics)new MatchStatistics(fight);
  }
  
  public ArenaHandler getArenaHandler() {
    return (ArenaHandler)ArenaManager.getInstance();
  }
  
  public IcePracticeEntityHider getEntityHider() {
    return (IcePracticeEntityHider)EntityHider.getInstance();
  }
  
  public IcePracticeFight getFightByPlayer(Player player) {
    return FightRegistry.getInstance().getFightByPlayer(player);
  }
  
  public void registerInventoryMenu(Player player, InventoryGui inventory) {
    InventoryClickListener.OPEN_INVENTORIES.put(player.getUniqueId(), inventory);
  }
  
  public KitHandler getKitHandler() {
    return (KitHandler)KitManager.getInstance();
  }
  
  public PluginSettings getPluginSettings() {
    return (PluginSettings)this.settings;
  }
}
