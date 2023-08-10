package de.kontux.icepractice.api.match;

import de.kontux.icepractice.api.IcePracticeAPI;
import de.kontux.icepractice.api.arena.IcePracticeArena;
import de.kontux.icepractice.api.config.MatchMessages;
import de.kontux.icepractice.api.kit.IcePracticeKit;
import de.kontux.icepractice.api.match.misc.CoolDown;
import de.kontux.icepractice.api.match.misc.FightStatistics;
import de.kontux.icepractice.api.playerstates.PlayerState;
import de.kontux.icepractice.api.user.CustomUserKit;
import de.kontux.icepractice.api.util.ItemBuilder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public abstract class CustomFight implements IcePracticeFight {
  protected static final MatchMessages MESSAGES = IcePracticeAPI.getMatchMessages();
  
  protected final List<Player> players;
  
  protected final IcePracticeKit kit;
  
  protected IcePracticeArena arena;
  
  private final FightStatistics statistics = IcePracticeAPI.constructFightStatistics(this);
  
  protected final boolean ranked;
  
  protected final Plugin plugin;
  
  private final List<Player> all;
  
  protected final List<Player> spectators = new ArrayList<>();
  
  public CustomFight(Plugin plugin, List<Player> players, IcePracticeKit kit, boolean ranked) {
    this.plugin = Objects.<Plugin>requireNonNull(plugin);
    this.players = Objects.<List<Player>>requireNonNull(players);
    this.all = Collections.unmodifiableList(players);
    this.kit = Objects.<IcePracticeKit>requireNonNull(kit);
    this.ranked = ranked;
  }
  
  protected final void startCooldown() {
    (new CoolDown(this.plugin, this)).runCooldown();
  }
  
  protected void spawnPlayer(Player player, Location location) {
    player.teleport(location);
    IcePracticeAPI.getPlayerStateManager().setState(player, PlayerState.STARTING_MATCH);
    handleEntityHider(player);
  }
  
  protected final void equipKit(Player player) {
    List<CustomUserKit> customKits = null;
    if (!player.hasMetadata("NPC"))
      customKits = IcePracticeAPI.getUserData(player).getCustomKits(this.kit); 
    if (customKits == null || customKits.isEmpty()) {
      this.kit.equipKit(player);
    } else {
      for (CustomUserKit customKit : customKits) {
        player.getInventory().addItem(new ItemStack[] { ItemBuilder.create(Material.BOOK, customKit.getCustomName(), null) });
      } 
      player.sendMessage(ChatColor.YELLOW + "Please select a custom kit.");
    } 
  }
  
  public final void killPlayer(Player dead, Player killer) {
    IcePracticeAPI.getPlayerStateManager().setState(dead, PlayerState.IDLE);
    IcePracticeAPI.generateMatchInventory(dead, this).initializeInventory();
    Bukkit.getScheduler().scheduleSyncDelayedTask(this.plugin, () -> IcePracticeAPI.getNmsApi().sendEntityStatusPacket((Entity)dead, this.players, (byte)3), 3L);
    Bukkit.getScheduler().scheduleSyncDelayedTask(this.plugin, () -> IcePracticeAPI.getNmsApi().sendEntityStatusPacket((Entity)dead, this.spectators, (byte)3), 3L);
    if (killer != null) {
      IcePracticeAPI.broadcast(this.players, MESSAGES.getTeamFightDeathMessage(dead, killer));
      IcePracticeAPI.broadcast(this.spectators, MESSAGES.getTeamFightDeathMessage(dead, killer));
    } else {
      IcePracticeAPI.broadcast(this.players, ChatColor.RED + dead.getDisplayName() + " died.");
      IcePracticeAPI.broadcast(this.spectators, ChatColor.RED + dead.getDisplayName() + " died.");
    } 
    this.players.remove(dead);
    processDeath(dead, killer);
    if (isMatchOver()) {
      this.spectators.add(dead);
      endFight(dead);
    } else {
      addSpectator(dead, false);
    } 
  }
  
  public void removeSpectator(Player player, boolean announce) {
    this.spectators.remove(player);
    if (announce) {
      IcePracticeAPI.broadcast(this.players, MESSAGES.getNoLongerSpectatingMessage(player));
      IcePracticeAPI.broadcast(this.spectators, MESSAGES.getNoLongerSpectatingMessage(player));
    } 
    IcePracticeAPI.getSpawnpointManager().teleportToSpawn(player);
    player.sendMessage(MESSAGES.getLeftSpectatingMessage());
  }
  
  public final Player getNext(Player player) {
    if (!this.players.contains(player))
      throw new IllegalArgumentException("This player was not part of this match"); 
    int ownIndex = this.players.indexOf(player);
    if (ownIndex == this.players.size() - 1)
      return this.players.get(0); 
    return this.players.get(ownIndex + 1);
  }
  
  public final Player getNextTotal(Player player) {
    int ownIndex = this.all.indexOf(player);
    if (ownIndex == this.players.size() - 1)
      return this.all.get(0); 
    return this.all.get(ownIndex + 1);
  }
  
  protected final void registerFight() {
    IcePracticeAPI.getFightRegistry().addMatch(this);
  }
  
  protected final void unregisterFight() {
    IcePracticeAPI.getFightRegistry().addMatch(this);
  }
  
  public void expireCooldown() {
    for (Player player : this.players)
      IcePracticeAPI.getPlayerStateManager().setState(player, PlayerState.MATCH); 
    IcePracticeAPI.broadcast(this.players, MESSAGES.getStartMessage());
    IcePracticeAPI.broadcast(this.spectators, MESSAGES.getStartMessage());
  }
  
  protected final void handleEntityHider(Player player) {
    for (Player current : player.getWorld().getPlayers()) {
      if (this.players.contains(current)) {
        IcePracticeAPI.getEntityHider().showEntity(current, (Entity)player);
        IcePracticeAPI.getEntityHider().showEntity(player, (Entity)current);
        continue;
      } 
      if (this.spectators.contains(current)) {
        IcePracticeAPI.getEntityHider().showEntity(player, (Entity)current);
        IcePracticeAPI.getEntityHider().hideEntity(player, (Entity)current);
        continue;
      } 
      IcePracticeAPI.getEntityHider().hideEntity(player, (Entity)current);
      IcePracticeAPI.getEntityHider().hideEntity(current, (Entity)player);
    } 
  }
  
  public final FightStatistics getMatchStatistics() {
    return this.statistics;
  }
  
  public final boolean isRanked() {
    return this.ranked;
  }
  
  public final List<Player> getPlayers() {
    return this.players;
  }
  
  public final IcePracticeKit getKit() {
    return this.kit;
  }
  
  public final IcePracticeArena getArena() {
    return this.arena;
  }
  
  protected abstract void processDeath(Player paramPlayer1, Player paramPlayer2);
  
  protected abstract boolean isMatchOver();
}
