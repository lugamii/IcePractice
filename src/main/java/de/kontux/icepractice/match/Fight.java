package de.kontux.icepractice.match;

import de.kontux.icepractice.IcePracticePlugin;
import de.kontux.icepractice.api.arena.IcePracticeArena;
import de.kontux.icepractice.api.event.fight.FightEndEvent;
import de.kontux.icepractice.api.event.fight.FightStartEvent;
import de.kontux.icepractice.api.kit.IcePracticeKit;
import de.kontux.icepractice.api.match.IcePracticeFight;
import de.kontux.icepractice.api.match.misc.FightStatistics;
import de.kontux.icepractice.api.playerstates.PlayerState;
import de.kontux.icepractice.api.user.CustomUserKit;
import de.kontux.icepractice.arenas.ArenaManager;
import de.kontux.icepractice.configs.Settings;
import de.kontux.icepractice.configs.repositories.messages.MatchMessageRepository;
import de.kontux.icepractice.listeners.fight.SumoListeners;
import de.kontux.icepractice.listeners.player.PlayerFreeze;
import de.kontux.icepractice.locations.SpawnPointHandler;
import de.kontux.icepractice.match.misc.CoolDown;
import de.kontux.icepractice.match.misc.MatchStatistics;
import de.kontux.icepractice.match.types.Duel;
import de.kontux.icepractice.playermanagement.PlayerStates;
import de.kontux.icepractice.postiventories.AfterMatchInventory;
import de.kontux.icepractice.protocol.EntityHider;
import de.kontux.icepractice.registries.FightRegistry;
import de.kontux.icepractice.registries.PartyRegistry;
import de.kontux.icepractice.scoreboard.updaters.fight.FightScoreboardUpdater;
import de.kontux.icepractice.userdata.PlayerDataManager;
import de.kontux.icepractice.util.ItemBuilder;
import de.kontux.icepractice.util.LangUtil;
import de.kontux.icepractice.util.SpigotUtil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public abstract class Fight extends SpectatableMatch implements IcePracticeFight {
  protected final List<Player> all;
  
  protected final MatchMessageRepository messages = new MatchMessageRepository();
  
  protected final MatchStatistics matchStatistics = new MatchStatistics(this);
  
  protected final List<Player> players;
  
  protected final IcePracticeKit kit;
  
  private final boolean ranked;
  
  protected SumoListeners sumoListeners;
  
  protected PlayerFreeze playerFreeze;
  
  protected FightScoreboardUpdater scoreboard;
  
  protected IcePracticeArena arena;
  
  public Fight(List<Player> players, IcePracticeKit kit, boolean ranked) {
    this.players = players;
    this.all = Collections.unmodifiableList(new ArrayList<>(players));
    this.kit = kit;
    this.ranked = ranked;
    this.arena = ArenaManager.getInstance().getRandomFreeArena(kit);
  }
  
  public Fight(List<Player> players, IcePracticeKit kit, boolean ranked, List<Player> spectators) {
    this(players, kit, ranked);
    for (Player spectator : spectators) {
      if (!players.contains(spectator))
        spectators.add(spectator); 
    } 
  }
  
  public void expireCooldown() {
    for (Player current : this.players)
      PlayerStates.getInstance().setState(current, PlayerState.MATCH); 
    if (this.playerFreeze != null)
      this.playerFreeze.unregister(); 
  }
  
  public void endFight(Player lastDead) {
    FightEndEvent event = fireEndEvent(getWinnerTeam(lastDead), getLoserTeam(lastDead));
    if (event.isCancelled())
      return; 
    IcePracticePlugin.broadCastMessage(this.players, this.messages.getEndMessage());
    IcePracticePlugin.broadCastMessage(this.spectators, this.messages.getEndMessage());
    for (Player current : this.players) {
      (new AfterMatchInventory(current, this)).initializeInventory();
      PlayerStates.getInstance().setState(current, PlayerState.IDLE);
    } 
    sendEndMessages(getWinnerTeam(lastDead), getLoserTeam(lastDead));
    if (this.scoreboard != null)
      this.scoreboard.stopUpdater(); 
    if (this.sumoListeners != null)
      this.sumoListeners.unregister(); 
    Bukkit.getScheduler().scheduleSyncDelayedTask((Plugin)IcePracticePlugin.getInstance(), () -> {
          this.matchStatistics.recoverArena();
          if (this.ranked && this instanceof Duel)
            ((Duel)this).changeElo(getWinnerTeam(lastDead).get(0), getLoserTeam(lastDead).get(0)); 
          for (Player current : this.players)
            SpawnPointHandler.teleportToSpawn(current); 
          for (Player current : this.spectators)
            SpawnPointHandler.teleportToSpawn(current); 
          unregisterMatch();
        },80L);
  }
  
  public final void killPlayer(Player dead, Player killer) {
    PlayerStates.getInstance().setState(dead, PlayerState.IDLE);
    (new AfterMatchInventory(dead, this)).initializeInventory();
    resetPlayer(dead);
    Bukkit.getScheduler().scheduleSyncDelayedTask((Plugin)IcePracticePlugin.getInstance(), () -> IcePracticePlugin.getNmsHandler().sendEntityStatusPacket((Entity)dead, this.players, (byte)3), 3L);
    Bukkit.getScheduler().scheduleSyncDelayedTask((Plugin)IcePracticePlugin.getInstance(), () -> IcePracticePlugin.getNmsHandler().sendEntityStatusPacket((Entity)dead, this.spectators, (byte)3), 3L);
    if (killer != null) {
      IcePracticePlugin.broadCastMessage(this.players, this.messages.getTeamFightDeathMessage(dead, killer));
      IcePracticePlugin.broadCastMessage(this.spectators, this.messages.getTeamFightDeathMessage(dead, killer));
    } else {
      IcePracticePlugin.broadCastMessage(this.players, ChatColor.RED + dead.getDisplayName() + " died.");
      IcePracticePlugin.broadCastMessage(this.spectators, ChatColor.RED + dead.getDisplayName() + " died.");
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
  
  protected void spawnPlayer(Player player, Location location) {
    player.setHealth(player.getMaxHealth());
    player.setSaturation(20.0F);
    player.setFlying(false);
    player.setAllowFlight(false);
    PlayerStates.getInstance().setState(player, PlayerState.STARTING_MATCH);
    player.teleport(location);
    for (Player current : player.getWorld().getPlayers()) {
      if (this.players.contains(current)) {
        EntityHider.getInstance().showEntity(current, (Entity)player);
        EntityHider.getInstance().showEntity(player, (Entity)current);
        continue;
      } 
      if (this.spectators.contains(current)) {
        EntityHider.getInstance().showEntity(current, (Entity)player);
        EntityHider.getInstance().hideEntity(player, (Entity)current);
        continue;
      } 
      EntityHider.getInstance().hideEntity(player, (Entity)current);
      EntityHider.getInstance().hideEntity(current, (Entity)player);
    } 
    equipKit(player);
  }
  
  protected final void equipKit(Player player) {
    List<CustomUserKit> customKits = null;
    player.getInventory().clear();
    if (!player.hasMetadata("NPC"))
      customKits = PlayerDataManager.getSettingsData(player.getUniqueId()).getCustomKits(this.kit); 
    if (customKits != null && !customKits.isEmpty()) {
      for (CustomUserKit customKit : customKits) {
        player.getInventory().addItem(new ItemStack[] { ItemBuilder.create(Material.BOOK, customKit.getCustomName(), null) });
      } 
      player.sendMessage(Settings.PRIMARY + "Please select a custom kit.");
    } else {
      this.kit.equipKit(player);
    } 
  }
  
  public final void removeSpectator(Player player, boolean announce) {
    this.spectators.remove(player);
    if (announce) {
      IcePracticePlugin.broadCastMessage(this.players, this.messages.getNoLongerSpectatingMessage(player));
      IcePracticePlugin.broadCastMessage(this.spectators, this.messages.getNoLongerSpectatingMessage(player));
    } 
    if (PartyRegistry.isInParty(player)) {
      PartyRegistry.getPartyByPlayer(player).teleportToSpawn(player);
    } else {
      SpawnPointHandler.teleportToSpawn(player);
    } 
    player.sendMessage(this.messages.getLeftSpectatingMessage());
  }
  
  protected final void handleSumo() {
    if (!this.kit.isSumo() && !this.kit.isSpleef())
      throw new IllegalStateException("handleSumo() may only be called if the match is a sumo or spleef match."); 
    this.sumoListeners = new SumoListeners(this.players, this);
    this.sumoListeners.register();
    this.playerFreeze = new PlayerFreeze(this.players);
    this.playerFreeze.register();
  }
  
  protected final void registerMatch() {
    FightRegistry.getInstance().addMatch(this);
  }
  
  protected final void unregisterMatch() {
    this.players.clear();
    this.spectators.clear();
    FightRegistry.getInstance().removeMatch(this);
  }
  
  protected final FightStartEvent fireStartEvent() {
    FightStartEvent event = new FightStartEvent(this);
    Bukkit.getPluginManager().callEvent((Event)event);
    return event;
  }
  
  protected final FightEndEvent fireEndEvent(List<Player> winners, List<Player> losers) {
    FightEndEvent event = new FightEndEvent(this, winners, losers);
    Bukkit.getPluginManager().callEvent((Event)event);
    return event;
  }
  
  public final Player getNext(Player player) {
    if (!this.players.contains(player))
      throw new IllegalArgumentException("This player was not part of this match"); 
    return (Player)LangUtil.getNext(this.players, player);
  }
  
  public final Player getNextTotal(Player player) {
    return (Player)LangUtil.getNext(this.all, player);
  }
  
  protected final void startCooldown() {
    (new CoolDown(this)).runCooldown();
  }
  
  public final List<Player> getPlayers() {
    return this.players;
  }
  
  public final MatchStatistics getMatchStatistics() {
    return this.matchStatistics;
  }
  
  public final IcePracticeKit getKit() {
    return this.kit;
  }
  
  public final IcePracticeArena getArena() {
    return this.arena;
  }
  
  public final boolean isRanked() {
    return this.ranked;
  }
  
  private void resetPlayer(Player player) {
    player.setMaximumNoDamageTicks(20);
    player.setNoDamageTicks(20);
    SpigotUtil.clearPotionEffects(player);
    SpigotUtil.dropAll(player);
  }
  
  public abstract void runMatch();
  
  protected abstract void sendBeginningMessages();
  
  protected abstract void startScoreboardUpdater();
  
  protected abstract void processDeath(Player paramPlayer1, Player paramPlayer2);
  
  protected abstract boolean isMatchOver();
  
  public abstract List<Player> getWinnerTeam(Player paramPlayer);
  
  public abstract List<Player> getLoserTeam(Player paramPlayer);
  
  public abstract void sendEndMessages(List<Player> paramList1, List<Player> paramList2);
}
