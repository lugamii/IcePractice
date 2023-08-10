package de.kontux.icepractice.arenas;

import de.kontux.icepractice.IcePracticePlugin;
import de.kontux.icepractice.api.arena.ArenaHandler;
import de.kontux.icepractice.api.arena.IcePracticeArena;
import de.kontux.icepractice.api.kit.IcePracticeKit;
import de.kontux.icepractice.configs.files.ArenaConfig;
import de.kontux.icepractice.configs.messages.ArenaMessageRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public class ArenaManager implements ArenaHandler {
  private static final ArenaManager INSTANCE = new ArenaManager();
  
  private final List<IcePracticeArena> arenas = new ArrayList<>();
  
  private final ArenaMessageRepository messages = new ArenaMessageRepository();
  
  private ArenaManager() {
    loadFromConfig();
  }
  
  public static ArenaManager getInstance() {
    return INSTANCE;
  }
  
  private void loadFromConfig() {
    IcePracticePlugin.getInstance().getLogger().info("Loading arenas from config...");
    ConfigurationSection section = ArenaConfig.getConfig().getConfigurationSection("Arenas");
    if (section == null)
      section = ArenaConfig.getConfig().createSection("Arenas"); 
    for (String key : section.getKeys(false)) {
      Arena arena = new Arena(key);
      if (arena.loadFromConfig())
        this.arenas.add(arena); 
    } 
  }
  
  public void create(Player player, String name) {
    if (getArena(name) != null) {
      player.sendMessage(this.messages.getAlreadyExistsMessage());
      return;
    } 
    Arena arena = new Arena(name);
    arena.create(player);
    this.arenas.add(arena);
    player.sendMessage(this.messages.getArenaCreateMessage(name));
  }
  
  public void delete(Player player, String name) {
    IcePracticeArena arena = getArena(name);
    if (arena == null) {
      player.sendMessage(ChatColor.RED + "This arena doesn't exist");
      return;
    } 
    arena.delete();
    this.arenas.remove(arena);
    player.sendMessage(this.messages.getArenaDeleteMessage(name));
  }
  
  public IcePracticeArena getArena(String name) {
    for (IcePracticeArena arena : this.arenas) {
      if (arena.getName().equals(name))
        return arena; 
    } 
    return null;
  }
  
  public IcePracticeArena getRandomFreeArena(IcePracticeKit kit) {
    List<IcePracticeArena> freeArenas = new ArrayList<>();
    for (IcePracticeArena arena : this.arenas) {
      if (matchesKit(arena, kit)) {
        if (!arena.isBuild()) {
          freeArenas.add(arena);
          continue;
        } 
        if (!arena.isInUse())
          freeArenas.add(arena); 
      } 
    } 
    return freeArenas.isEmpty() ? null : freeArenas.get((new Random()).nextInt(freeArenas.size()));
  }
  
  private boolean matchesKit(IcePracticeArena arena, IcePracticeKit kit) {
    return (arena.isSumo() == kit.isSumo() && arena.isSpleef() == kit.isSpleef() && arena.isHcf() == kit.isHcf() && kit.isBuild() == arena.isBuild());
  }
  
  public List<IcePracticeArena> getArenas() {
    return this.arenas;
  }
  
  public void reload() {
    this.arenas.clear();
    loadFromConfig();
  }
}
