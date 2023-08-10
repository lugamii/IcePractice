package de.kontux.icepractice.match.types;

import de.kontux.icepractice.IcePracticePlugin;
import de.kontux.icepractice.api.event.fight.FightStartEvent;
import de.kontux.icepractice.api.kit.IcePracticeKit;
import de.kontux.icepractice.match.Fight;
import de.kontux.icepractice.party.fights.SplitFight;
import de.kontux.icepractice.protocol.EntityHider;
import de.kontux.icepractice.scoreboard.updaters.fight.FightScoreboardUpdater;
import de.kontux.icepractice.scoreboard.updaters.fight.TeamFightScoreBoardUpdaterFight;
import de.kontux.icepractice.util.LangUtil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class TeamFight extends Fight {
  protected final List<Player> team1All;
  
  protected final List<Player> team2All;
  
  protected final List<Player> team1Alive;
  
  protected final List<Player> team2Alive;
  
  public TeamFight(List<Player> team1, List<Player> team2, IcePracticeKit kit) {
    super(LangUtil.combineLists(new List[] { team1, team2 }), kit, false);
    this.team1All = Collections.unmodifiableList(new ArrayList<>(team1));
    this.team2All = Collections.unmodifiableList(new ArrayList<>(team2));
    this.team1Alive = team1;
    this.team2Alive = team2;
    if (team1.isEmpty() || team2.isEmpty())
      throw new IllegalArgumentException("You may not pass a match an empty team list!"); 
  }
  
  public TeamFight(List<Player> team1, List<Player> team2, IcePracticeKit kit, List<Player> spectators) {
    this(team1, team2, kit);
    this.spectators.addAll(spectators);
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
      IcePracticePlugin.broadCastMessage(this.spectators, this.messages.getNoArenaMessage());
      if (this instanceof SplitFight)
        ((SplitFight)this).getParty().endMatch(); 
      return;
    } 
    if (this.kit.isSumo() || this.kit.isSpleef())
      handleSumo(); 
    sendBeginningMessages();
    startScoreboardUpdater();
    registerMatch();
    startCooldown();
    for (Player current : this.team1Alive)
      spawnPlayer(current, this.arena.getPos1()); 
    for (Player current : this.team2Alive)
      spawnPlayer(current, this.arena.getPos2()); 
  }
  
  protected boolean isMatchOver() {
    return (this.team1Alive.isEmpty() || this.team2Alive.isEmpty());
  }
  
  public void sendEndMessages(List<Player> winnerTeam, List<Player> loserTeam) {
    BaseComponent[] winnerButtons = new BaseComponent[winnerTeam.size() + 1];
    BaseComponent[] loserButtons = new BaseComponent[loserTeam.size() + 1];
    winnerButtons[0] = (BaseComponent)new TextComponent(ChatColor.GREEN + "Winners: ");
    loserButtons[0] = (BaseComponent)new TextComponent(ChatColor.RED + "Losers: ");
    if (!winnerTeam.isEmpty())
      for (int i = 0; i < winnerTeam.size(); i++) {
        TextComponent button;
        if (i + 1 == winnerTeam.size()) {
          button = new TextComponent(((Player)winnerTeam.get(i)).getPlayerListName());
        } else {
          button = new TextComponent(((Player)winnerTeam.get(i)).getPlayerListName() + ", ");
        } 
        button.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/inv " + ((Player)winnerTeam.get(i)).getUniqueId()));
        winnerButtons[i + 1] = (BaseComponent)button;
      }  
    if (!loserTeam.isEmpty())
      for (int i = 0; i < loserTeam.size(); i++) {
        TextComponent button;
        if (i + 1 == loserTeam.size()) {
          button = new TextComponent(((Player)loserTeam.get(i)).getPlayerListName());
        } else {
          button = new TextComponent(((Player)loserTeam.get(i)).getPlayerListName() + ", ");
        } 
        button.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/inv " + ((Player)loserTeam.get(i)).getUniqueId()));
        loserButtons[i + 1] = (BaseComponent)button;
      }  
    for (Player current : this.players) {
      current.sendMessage(" ");
      current.spigot().sendMessage(winnerButtons);
      current.spigot().sendMessage(loserButtons);
      current.sendMessage(" ");
    } 
    for (Player current : this.spectators) {
      current.sendMessage(" ");
      current.spigot().sendMessage(winnerButtons);
      current.spigot().sendMessage(loserButtons);
      current.sendMessage(" ");
    } 
  }
  
  public void addSpectator(Player player, boolean announce) {
    player.sendMessage(this.messages.getTeamSpectatorMessage(this.team1Alive.get(0), this.team2Alive.get(0), this.kit.getName()));
    handleSpectator(player);
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
  
  protected void sendBeginningMessages() {
    IcePracticePlugin.broadCastMessage(this.team1Alive, this.messages.getTeamStartingMessage(this.team2Alive.get(0), this.kit.getName()));
    IcePracticePlugin.broadCastMessage(this.team2Alive, this.messages.getTeamStartingMessage(this.team1Alive.get(0), this.kit.getName()));
  }
  
  protected void startScoreboardUpdater() {
    this.scoreboard = (FightScoreboardUpdater)new TeamFightScoreBoardUpdaterFight(this);
    this.scoreboard.startUpdater();
  }
  
  protected void processDeath(Player dead, Player killer) {}
  
  public List<Player> getTeam1() {
    return this.team1Alive;
  }
  
  public List<Player> getTeam2() {
    return this.team2Alive;
  }
  
  public List<Player> getWinnerTeam(Player lastDead) {
    if (this.team1Alive.contains(lastDead))
      return this.team2All; 
    return this.team1All;
  }
  
  public List<Player> getLoserTeam(Player lastDead) {
    if (this.team1Alive.contains(lastDead))
      return this.team1All; 
    return this.team2All;
  }
}
