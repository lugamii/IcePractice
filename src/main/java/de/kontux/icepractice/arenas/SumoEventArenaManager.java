package de.kontux.icepractice.arenas;

import de.kontux.icepractice.IcePracticePlugin;
import de.kontux.icepractice.configs.files.ArenaConfig;
import de.kontux.icepractice.configs.messages.ArenaMessageRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public class SumoEventArenaManager {
  private static final SumoEventArenaManager INSTANCE = new SumoEventArenaManager();
  
  private final List<SumoEventArena> sumoEventArenas = new ArrayList<>();
  
  private SumoEventArenaManager() {
    loadFromConfig();
  }
  
  public static SumoEventArenaManager getInstance() {
    return INSTANCE;
  }
  
  private void loadFromConfig() {
    IcePracticePlugin.getInstance().getLogger().info("Loading sumo event arenas from config...");
    ConfigurationSection section = ArenaConfig.getConfig().getConfigurationSection("SumoEventArenas");
    if (section == null)
      section = ArenaConfig.getConfig().createSection("SumoEventArenas"); 
    for (String key : section.getKeys(false)) {
      SumoEventArena arena = new SumoEventArena(key);
      if (arena.loadFromConfig())
        this.sumoEventArenas.add(arena); 
    } 
  }
  
  public void create(Player player, String name) {
    if (getArena(name) != null) {
      player.sendMessage((new ArenaMessageRepository()).getAlreadyExistsMessage());
      return;
    } 
    SumoEventArena arena = new SumoEventArena(name);
    arena.create(player);
    this.sumoEventArenas.add(arena);
    player.sendMessage((new ArenaMessageRepository()).getArenaCreateMessage(name));
  }
  
  public void delete(Player player, String name) {
    SumoEventArena arena = getArena(name);
    if (arena == null) {
      player.sendMessage(ChatColor.RED + "This arena doesn't exist");
      return;
    } 
    arena.delete();
    this.sumoEventArenas.remove(arena);
    player.sendMessage((new ArenaMessageRepository()).getArenaDeleteMessage(name));
  }
  
  public SumoEventArena getArena(String name) {
    for (SumoEventArena arena : this.sumoEventArenas) {
      if (arena.getName().equals(name))
        return arena; 
    } 
    return null;
  }
  
  public SumoEventArena getRandomFreeArena() {
    return this.sumoEventArenas.isEmpty() ? null : this.sumoEventArenas.get((new Random()).nextInt(this.sumoEventArenas.size()));
  }
  
  public List<SumoEventArena> getSumoEventArenas() {
    return this.sumoEventArenas;
  }
}
