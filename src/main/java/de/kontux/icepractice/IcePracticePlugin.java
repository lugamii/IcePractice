package de.kontux.icepractice;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketListener;
import de.kontux.icepractice.arenas.ArenaManager;
import de.kontux.icepractice.commands.ArenaCommandExecutor;
import de.kontux.icepractice.commands.DuelAcceptCommand;
import de.kontux.icepractice.commands.DuelCommandExecutor;
import de.kontux.icepractice.commands.EventCommandExecutor;
import de.kontux.icepractice.commands.GoldenHeadCommandExecutor;
import de.kontux.icepractice.commands.IcePracticeCommandExecutor;
import de.kontux.icepractice.commands.InventoryCommandExecutor;
import de.kontux.icepractice.commands.KitCommandExecutor;
import de.kontux.icepractice.commands.KitEditorCommandExecutor;
import de.kontux.icepractice.commands.LeaderboardsCommandExecutor;
import de.kontux.icepractice.commands.PartyCommandExecutor;
import de.kontux.icepractice.commands.PingCommandExecutor;
import de.kontux.icepractice.commands.QueueCommandExecutor;
import de.kontux.icepractice.commands.SettingsCommandExecutor;
import de.kontux.icepractice.commands.SpawnCommandExecutor;
import de.kontux.icepractice.commands.SpectateCommandExecutor;
import de.kontux.icepractice.commands.SumoEventArenaCommandExecutor;
import de.kontux.icepractice.commands.TimeCommandExecutor;
import de.kontux.icepractice.configs.defaults.ConfigDefaults;
import de.kontux.icepractice.configs.files.ArenaConfig;
import de.kontux.icepractice.configs.files.JoinItemConfig;
import de.kontux.icepractice.configs.files.KitConfig;
import de.kontux.icepractice.configs.files.MessageConfig;
import de.kontux.icepractice.configs.files.PlayerConfig;
import de.kontux.icepractice.database.MySQL;
import de.kontux.icepractice.database.SQLRepository;
import de.kontux.icepractice.items.join.JoinItemManager;
import de.kontux.icepractice.items.party.PartyItemManager;
import de.kontux.icepractice.kits.KitManager;
import de.kontux.icepractice.listeners.fight.DamageListener;
import de.kontux.icepractice.listeners.fight.PotionListener;
import de.kontux.icepractice.listeners.fight.ProjectileListener;
import de.kontux.icepractice.listeners.fight.RegenerationListener;
import de.kontux.icepractice.listeners.interaction.InteractListener;
import de.kontux.icepractice.listeners.inventory.InventoryClickListener;
import de.kontux.icepractice.listeners.inventory.InventoryCloseListener;
import de.kontux.icepractice.listeners.item.BlockBreakListener;
import de.kontux.icepractice.listeners.item.BlockPlaceListener;
import de.kontux.icepractice.listeners.item.ItemDropListener;
import de.kontux.icepractice.listeners.item.ItemPickUpListener;
import de.kontux.icepractice.listeners.item.ItemSpawnListener;
import de.kontux.icepractice.listeners.player.FoodListener;
import de.kontux.icepractice.listeners.player.JoinListeners;
import de.kontux.icepractice.listeners.player.QuitListener;
import de.kontux.icepractice.listeners.protocol.EntityOutListener;
import de.kontux.icepractice.listeners.protocol.ParticleOutListener;
import de.kontux.icepractice.listeners.protocol.WorldEventOutListener;
import de.kontux.icepractice.nms.NMSHandler;
import de.kontux.icepractice.nms.NMSVersion;
import de.kontux.icepractice.scoreboard.ScoreboardManager;
import de.kontux.icepractice.updater.UpdateChecker;
import de.kontux.icepractice.userdata.PlayerDataManager;
import de.kontux.icepractice.util.TabCompleter;
import java.io.File;
import java.util.List;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class IcePracticePlugin extends JavaPlugin {
  public static final String NAME = "IcePractice";
  
  public static NMSVersion nmsVersion;
  
  private static NMSHandler nmsHandler;
  
  private static IcePracticePlugin plugin;
  
  private ProtocolManager protocolManager;
  
  private MySQL mySQL;
  
  private SQLRepository repository;
  
  public static void broadCastMessage(List<Player> players, String message) {
    for (Player current : players)
      current.sendMessage(message); 
  }
  
  public static void broadCastMessage(List<Player> players, BaseComponent message) {
    for (Player current : players)
      current.spigot().sendMessage(message); 
  }
  
  public static NMSHandler getNmsHandler() {
    return nmsHandler;
  }
  
  public static File getFolder() {
    return plugin.getDataFolder();
  }
  
  public static IcePracticePlugin getInstance() {
    return plugin;
  }
  
  public void onEnable() {
    plugin = this;
    if (!NMSVersion.determineVersion()) {
      log("§cThis version of Minecraft (" + Bukkit.getBukkitVersion() + ") is not supported! Disabling plugin...");
      Bukkit.getPluginManager().disablePlugin((Plugin)this);
      return;
    }
    nmsHandler = new NMSHandler();
    this.protocolManager = ProtocolLibrary.getProtocolManager();
    loadConfigFiles();
    handleMySql();
    registerCommandExecutors();
    registerEventListeners();
    for (Player player : Bukkit.getOnlinePlayers())
      PlayerDataManager.addPlayer(player); 
    (new IcePracticeImpl(this)).initialize();
    (new UpdateChecker(this, 75338)).check();
  }
  
  private void loadConfigFiles() {
    log("§eLoading files...");
    saveDefaultConfig();
    ArenaConfig.load();
    KitConfig.load();
    JoinItemConfig.load();
    PlayerConfig.load();
    MessageConfig.load();
    (new ConfigDefaults(getConfig())).provideDefaults();
  }
  
  private void handleMySql() {
    this.mySQL = new MySQL(this);
    this.repository = new SQLRepository(this.mySQL);
    FileConfiguration config = getConfig();
    String host = config.getString("config.mysql.host", "host");
    String database = config.getString("config.mysql.database", "database");
    String user = config.getString("config.mysql.user", "user");
    String password = config.getString("config.mysql.password", "password");
    int port = config.isInt("config.mysql.port") ? config.getInt("config.mysql.port") : 3306;
    this.mySQL.connect(host, database, user, password, port, success -> {
          if (success.booleanValue())
            this.repository.setUp(); 
        });
  }
  
  private void registerEventListeners() {
    PluginManager pm = Bukkit.getPluginManager();
    pm.registerEvents((Listener)new JoinListeners(this.repository), (Plugin)this);
    pm.registerEvents((Listener)new DamageListener(), (Plugin)this);
    pm.registerEvents((Listener)new InventoryClickListener(), (Plugin)this);
    pm.registerEvents((Listener)new InventoryCloseListener(), (Plugin)this);
    pm.registerEvents((Listener)new ItemDropListener(), (Plugin)this);
    pm.registerEvents((Listener)new BlockPlaceListener(), (Plugin)this);
    pm.registerEvents((Listener)new BlockBreakListener(), (Plugin)this);
    pm.registerEvents((Listener)new PotionListener(), (Plugin)this);
    pm.registerEvents((Listener)new FoodListener(), (Plugin)this);
    pm.registerEvents((Listener)new ProjectileListener(), (Plugin)this);
    pm.registerEvents((Listener)new ItemPickUpListener(), (Plugin)this);
    pm.registerEvents((Listener)new RegenerationListener(), (Plugin)this);
    pm.registerEvents((Listener)new QuitListener(this), (Plugin)this);
    pm.registerEvents((Listener)new ItemSpawnListener(this), (Plugin)this);
    InteractListener interactListener = new InteractListener();
    pm.registerEvents((Listener)interactListener, (Plugin)this);
    interactListener.startScheduler();
    this.protocolManager.addPacketListener((PacketListener)new EntityOutListener(this));
    this.protocolManager.addPacketListener((PacketListener)new WorldEventOutListener((Plugin)this));
    this.protocolManager.addPacketListener((PacketListener)new ParticleOutListener((Plugin)this));
  }
  
  private void registerCommandExecutors() {
    TabCompleter tabCompleter = new TabCompleter();
    PluginCommand icePracticeCommand = getCommand("icepractice");
    icePracticeCommand.setExecutor((CommandExecutor)new IcePracticeCommandExecutor(this));
    icePracticeCommand.setTabCompleter((TabCompleter) tabCompleter);
    PluginCommand eventCommand = getCommand("event");
    eventCommand.setExecutor((CommandExecutor)new EventCommandExecutor());
    eventCommand.setTabCompleter((TabCompleter)tabCompleter);
    PluginCommand partyCommand = getCommand("party");
    partyCommand.setExecutor((CommandExecutor)new PartyCommandExecutor());
    partyCommand.setTabCompleter((TabCompleter)tabCompleter);
    PluginCommand arenaCommand = getCommand("arena");
    arenaCommand.setExecutor((CommandExecutor)new ArenaCommandExecutor());
    arenaCommand.setTabCompleter((TabCompleter)tabCompleter);
    PluginCommand editorCommand = getCommand("editor");
    editorCommand.setExecutor((CommandExecutor)new KitEditorCommandExecutor());
    editorCommand.setTabCompleter((TabCompleter)tabCompleter);
    PluginCommand kitCommand = getCommand("kit");
    kitCommand.setExecutor((CommandExecutor)new KitCommandExecutor());
    kitCommand.setTabCompleter((TabCompleter)tabCompleter);
    getCommand("duel").setExecutor((CommandExecutor)new DuelCommandExecutor());
    getCommand("leaderboards").setExecutor((CommandExecutor)new LeaderboardsCommandExecutor(this));
    getCommand("accept").setExecutor((CommandExecutor)new DuelAcceptCommand());
    getCommand("inventory").setExecutor((CommandExecutor)new InventoryCommandExecutor());
    getCommand("settings").setExecutor((CommandExecutor)new SettingsCommandExecutor());
    getCommand("spectate").setExecutor((CommandExecutor)new SpectateCommandExecutor());
    getCommand("ping").setExecutor((CommandExecutor)new PingCommandExecutor());
    getCommand("queue").setExecutor((CommandExecutor)new QueueCommandExecutor());
    getCommand("spawn").setExecutor((CommandExecutor)new SpawnCommandExecutor());
    getCommand("sumoeventarena").setExecutor((CommandExecutor)new SumoEventArenaCommandExecutor());
    getCommand("day").setExecutor((CommandExecutor)new TimeCommandExecutor());
    getCommand("sunset").setExecutor((CommandExecutor)new TimeCommandExecutor());
    getCommand("night").setExecutor((CommandExecutor)new TimeCommandExecutor());
    getCommand("goldenhead").setExecutor((CommandExecutor)new GoldenHeadCommandExecutor());
  }
  
  public void onDisable() {
    if (this.mySQL.useMySQL())
      this.mySQL.disconnect(); 
    for (Player player : Bukkit.getOnlinePlayers())
      PlayerDataManager.removePlayer(player); 
    log("§eSaving all files...");
    KitConfig.save();
    ArenaConfig.save();
    PlayerConfig.save();
    log("§ePlugin was disabled.");
  }
  
  public void reloadPlugin(CommandSender sender) {
    sender.sendMessage("Reloading all configuration files...");
    reloadConfig();
    ArenaConfig.reload();
    KitConfig.reload();
    JoinItemConfig.reload();
    MessageConfig.reload();
    PlayerConfig.reload();
    JoinItemManager.getInstance().reload();
    PartyItemManager.getInstance().reload();
    ScoreboardManager.getInstance().reload();
    KitManager.getInstance().reload();
    ArenaManager.getInstance().reload();
    sender.sendMessage("Plugin was reloaded");
  }
  
  public void log(String message) {
    getServer().getConsoleSender().sendMessage(ChatColor.BLUE + "[" + "IcePractice" + "] " + message);
  }
  
  public SQLRepository getRepository() {
    return this.repository;
  }
  
  public ProtocolManager getProtocolManager() {
    return this.protocolManager;
  }
}
