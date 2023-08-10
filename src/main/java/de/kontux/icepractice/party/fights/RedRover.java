package de.kontux.icepractice.party.fights;

import de.kontux.icepractice.IcePracticePlugin;
import de.kontux.icepractice.api.event.fight.FightStartEvent;
import de.kontux.icepractice.api.kit.IcePracticeKit;
import de.kontux.icepractice.api.playerstates.PlayerState;
import de.kontux.icepractice.configs.Settings;
import de.kontux.icepractice.party.Party;
import de.kontux.icepractice.playermanagement.PlayerStates;
import de.kontux.icepractice.protocol.EntityHider;
import java.util.List;
import java.util.Random;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class RedRover extends SplitFight {
  public RedRover(List<Player> team1, List<Player> team2, IcePracticeKit kit, Party party) {
    super(team1, team2, kit, party);
  }
  
  public void runMatch() {
    FightStartEvent fightStartEvent = fireStartEvent();
    if (fightStartEvent.isCancelled()) {
      this.players.clear();
      this.team1Alive.clear();
      this.team2Alive.clear();
      return;
    } 
    if (this.arena == null) {
      IcePracticePlugin.broadCastMessage(this.players, this.messages.getNoArenaMessage());
      this.party.endMatch();
      return;
    } 
    Player first1 = getRandomNextPlayer(this.team1Alive);
    Player first2 = getRandomNextPlayer(this.team2Alive);
    if (first1 == null || first2 == null)
      return; 
    sendBeginningMessages();
    spawnPlayer(first1, this.arena.getPos1());
    spawnPlayer(first2, this.arena.getPos2());
    for (Player player : this.players) {
      if (player != first1 && player != first2)
        addSpectator(player, false); 
    } 
    if (this.kit.isSumo() || this.kit.isSpleef())
      handleSumo(); 
    startScoreboardUpdater();
    startCooldown();
    registerMatch();
  }
  
  public void expireCooldown() {
    for (Player current : this.players) {
      if (PlayerStates.getInstance().getState(current) == PlayerState.STARTING_MATCH)
        PlayerStates.getInstance().setState(current, PlayerState.MATCH); 
    } 
    if (this.playerFreeze != null)
      this.playerFreeze.unregister(); 
  }
  
  protected void processDeath(Player dead, Player killer) {
    boolean wasTeam1 = this.team1Alive.remove(dead);
    boolean wasTeam2 = this.team2Alive.remove(dead);
    if (this.team1Alive.remove(dead)) {
      wasTeam1 = true;
    } else if (this.team2Alive.remove(dead)) {
      wasTeam2 = true;
    } 
    if (isMatchOver())
      return; 
    Player next = wasTeam1 ? getRandomNextPlayer(this.team1Alive) : (wasTeam2 ? getRandomNextPlayer(this.team2Alive) : null);
    if (next != null) {
      Location location = wasTeam1 ? this.arena.getPos1() : this.arena.getPos2();
      if (!this.players.contains(next))
        this.players.add(next); 
      spawnPlayer(next, location);
      PlayerStates.getInstance().setState(next, PlayerState.MATCH);
    } else {
      endFight(dead);
    } 
  }
  
  public void addSpectator(Player player, boolean announce) {
    handleSpectator(player);
    if (this.players.contains(player))
      this.spectators.remove(player); 
    if (announce) {
      IcePracticePlugin.broadCastMessage(this.players, this.messages.getNowSpectatingMessage(player));
      IcePracticePlugin.broadCastMessage(this.spectators, this.messages.getNowSpectatingMessage(player));
    } 
    player.teleport(this.arena.getCenter());
    for (Player current : this.players) {
      EntityHider.getInstance().hideEntity(current, (Entity)player);
      EntityHider.getInstance().showEntity(player, (Entity)current);
    } 
  }
  
  private Player getRandomNextPlayer(List<Player> team) {
    return team.get((new Random()).nextInt(team.size()));
  }
  
  protected void sendBeginningMessages() {
    IcePracticePlugin.broadCastMessage(this.team1Alive, Settings.PRIMARY + "Starting RedRover match against " + ((Player)this.team2Alive.get(0)).getName() + "'s team.");
    IcePracticePlugin.broadCastMessage(this.team2Alive, Settings.PRIMARY + "Starting RedRover match against " + ((Player)this.team1Alive.get(0)).getName() + "'s team.");
  }
}
