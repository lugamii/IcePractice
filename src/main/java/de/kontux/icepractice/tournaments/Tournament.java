package de.kontux.icepractice.tournaments;

import de.kontux.icepractice.IcePracticePlugin;
import de.kontux.icepractice.api.kit.IcePracticeKit;
import de.kontux.icepractice.api.playerstates.PlayerState;
import de.kontux.icepractice.configs.repositories.messages.EventMessageRepository;
import de.kontux.icepractice.locations.SpawnPointHandler;
import de.kontux.icepractice.match.Fight;
import de.kontux.icepractice.playermanagement.PlayerStates;
import de.kontux.icepractice.protocol.EntityHider;
import de.kontux.icepractice.registries.EventRegistry;
import de.kontux.icepractice.scoreboard.ScoreboardManager;
import de.kontux.icepractice.scoreboard.updaters.event.EventScoreboardUpdater;
import de.kontux.icepractice.util.ItemBuilder;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public abstract class Tournament {
  public static final ItemStack LEAVE_ITEM = ItemBuilder.create(Material.REDSTONE, "§cLeave event", null);
  
  protected final Player host;
  
  protected final List<Player> participants = new ArrayList<>();
  
  protected final List<Player> spectators = new ArrayList<>();
  
  protected final EventMessageRepository messages = new EventMessageRepository();
  
  protected final String eventName;
  
  protected final EventBroadcast broadcast = new EventBroadcast(this);
  
  protected final List<Fight> onGoingFights = new ArrayList<>();
  
  private final EventType type;
  
  private final IcePracticeKit kit;
  
  private final int eventId;
  
  private final String password;
  
  protected EventScoreboardUpdater scoreboard;
  
  protected boolean running = false;
  
  public Tournament(String eventName, int eventId, Player host, String password, EventType type, IcePracticeKit kit) {
    this.type = type;
    this.eventName = eventName;
    this.eventId = eventId;
    this.host = host;
    this.password = password;
    this.kit = kit;
  }
  
  public abstract void hostEvent();
  
  public final void startEvent() {
    this.running = true;
    startMatchCircuit();
  }
  
  public abstract void startMatchCircuit();
  
  public abstract void startNextFights();
  
  public final void endFight(Fight fight, List<Player> winners, List<Player> losers) {
    this.onGoingFights.remove(fight);
    for (Player current : winners) {
      teleportToSpawn(current);
      changeInventory(current);
      PlayerStates.getInstance().setState(current, PlayerState.EVENT);
    } 
    for (Player current : losers)
      leave(current); 
    IcePracticePlugin.broadCastMessage(this.participants, this.messages.getEliminatedMessage(((Player)winners.get(0)).getDisplayName(), ((Player)losers.get(0)).getDisplayName(), this.participants.size()));
    onMatchEnd();
  }
  
  protected abstract void onMatchEnd();
  
  public final void join(Player player) {
    this.participants.add(player);
    IcePracticePlugin.broadCastMessage(this.participants, this.messages.getJoinMessage(player.getDisplayName(), this.participants.size()));
    PlayerStates.getInstance().setState(player, PlayerState.EVENT);
    teleportToSpawn(player);
    changeInventory(player);
    for (Player current : this.participants) {
      EntityHider.getInstance().hideEntity(current, (Entity)player);
      EntityHider.getInstance().showEntity(player, (Entity)current);
    } 
    updateStartingBoard();
  }
  
  public final void leave(Player player) {
    updateStartingBoard();
    this.participants.remove(player);
    IcePracticePlugin.broadCastMessage(this.participants, this.messages.getLeaveMessage(player.getDisplayName(), this.participants.size()));
    IcePracticePlugin.broadCastMessage(this.spectators, this.messages.getLeaveMessage(player.getDisplayName(), this.participants.size()));
    SpawnPointHandler.teleportToSpawn(player);
  }
  
  public final void endEvent() {
    this.scoreboard.stop();
    this.broadcast.stopBroadcast();
    EventRegistry.removeStartingEvent(this);
    EventRegistry.removeRunningEvent(this);
    for (Player participant : this.participants)
      SpawnPointHandler.teleportToSpawn(participant); 
    for (Player spectator : this.spectators)
      SpawnPointHandler.teleportToSpawn(spectator); 
    this.spectators.clear();
    this.participants.clear();
  }
  
  public final void updateStartingBoard() {
    for (Player player : this.participants)
      ScoreboardManager.getInstance().setStartingEventBoard(player, this.broadcast.getFormattedTime(), this.participants.size(), this.kit.getName()); 
  }
  
  protected abstract void teleportToSpawn(Player paramPlayer);
  
  protected abstract void changeInventory(Player paramPlayer);
  
  public final int getId() {
    return this.eventId;
  }
  
  public final EventType getType() {
    return this.type;
  }
  
  public final String getName() {
    return this.eventName;
  }
  
  public final boolean isRunning() {
    return this.running;
  }
  
  public final boolean isProtected() {
    return (this.password != null);
  }
  
  public final Player getHost() {
    return this.host;
  }
  
  public final List<Player> getParticipants() {
    return this.participants;
  }
  
  public final List<Fight> getOnGoingFights() {
    return this.onGoingFights;
  }
  
  public final List<Player> getSpectators() {
    return this.spectators;
  }
  
  public final IcePracticeKit getKit() {
    return this.kit;
  }
}
