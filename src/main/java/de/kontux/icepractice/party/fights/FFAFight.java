package de.kontux.icepractice.party.fights;

import de.kontux.icepractice.IcePracticePlugin;
import de.kontux.icepractice.api.event.fight.FightStartEvent;
import de.kontux.icepractice.api.kit.IcePracticeKit;
import de.kontux.icepractice.api.match.IcePracticeFight;
import de.kontux.icepractice.api.playerstates.PlayerState;
import de.kontux.icepractice.locations.SpawnPointHandler;
import de.kontux.icepractice.match.Fight;
import de.kontux.icepractice.party.Party;
import de.kontux.icepractice.playermanagement.PlayerStates;
import de.kontux.icepractice.postiventories.AfterMatchInventory;
import de.kontux.icepractice.protocol.EntityHider;
import de.kontux.icepractice.scoreboard.updaters.fight.FFAScoreBoardUpdaterFight;
import de.kontux.icepractice.scoreboard.updaters.fight.FightScoreboardUpdater;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class FFAFight extends Fight {
  private final Party party;
  
  private final HashMap<Player, Integer> kills = new HashMap<>();
  
  public FFAFight(Party party, IcePracticeKit kit) {
    super(party.getMembers(), kit, false);
    this.party = party;
  }
  
  public void runMatch() {
    FightStartEvent fightStartEvent = fireStartEvent();
    if (fightStartEvent.isCancelled()) {
      this.players.clear();
      return;
    } 
    if (this.arena == null) {
      IcePracticePlugin.broadCastMessage(this.players, this.messages.getNoArenaMessage());
      IcePracticePlugin.broadCastMessage(this.spectators, this.messages.getNoArenaMessage());
      this.party.endMatch();
      return;
    } 
    if (this.kit.isSumo() || this.kit.isSpleef())
      handleSumo(); 
    sendBeginningMessages();
    startScoreboardUpdater();
    registerMatch();
    startCooldown();
    for (Player player : this.players)
      spawnPlayer(player, this.arena.getPos1()); 
  }
  
  protected void sendBeginningMessages() {
    IcePracticePlugin.broadCastMessage(this.players, this.messages.getFFAStartingMessage(this.kit.getName()));
  }
  
  protected void processDeath(Player dead, Player killer) {
    if (killer != null)
      this.kills.put(killer, this.kills.getOrDefault(killer, Integer.valueOf(0))); 
  }
  
  public void endFight(Player lastDead) {
    IcePracticePlugin.broadCastMessage(this.players, this.messages.getEndMessage());
    IcePracticePlugin.broadCastMessage(this.spectators, this.messages.getEndMessage());
    if (this.sumoListeners != null)
      this.sumoListeners.unregister(); 
    if (this.scoreboard != null)
      this.scoreboard.stopUpdater(); 
    Player winner = this.players.get(0);
    PlayerStates.getInstance().setState(winner, PlayerState.IDLE);
    (new AfterMatchInventory(winner, (IcePracticeFight)this)).initializeInventory();
    sendEndMessages(getWinnerTeam(lastDead), getLoserTeam(lastDead));
    Bukkit.getScheduler().scheduleSyncDelayedTask((Plugin)IcePracticePlugin.getInstance(), () -> {
          this.matchStatistics.recoverArena();
          for (Player current : this.spectators) {
            if (!this.party.getMembers().contains(current))
              SpawnPointHandler.teleportToSpawn(current); 
          } 
          this.party.endMatch();
          unregisterMatch();
        },80L);
  }
  
  protected void startScoreboardUpdater() {
    this.scoreboard = (FightScoreboardUpdater)new FFAScoreBoardUpdaterFight(this);
    this.scoreboard.stopUpdater();
  }
  
  protected boolean isMatchOver() {
    return (this.players.size() == 1);
  }
  
  public List<Player> getWinnerTeam(Player lastDead) {
    return this.players;
  }
  
  public List<Player> getLoserTeam(Player lastDead) {
    return new ArrayList<>();
  }
  
  public void addSpectator(Player player, boolean announce) {
    player.sendMessage(this.messages.getFFASpectatorMessage(this.kit.getName()));
    handleSpectator(player);
    if (announce) {
      IcePracticePlugin.broadCastMessage(this.players, this.messages.getNowSpectatingMessage(player));
      IcePracticePlugin.broadCastMessage(this.spectators, this.messages.getNowSpectatingMessage(player));
    } 
    player.teleport(this.arena.getCenter());
    for (Player current : this.players) {
      EntityHider.getInstance().hideEntity(player, (Entity)current);
      EntityHider.getInstance().showEntity(current, (Entity)player);
    } 
  }
  
  public void sendEndMessages(List<Player> winnerTeam, List<Player> loserTeam) {
    Player winner = winnerTeam.get(0);
    BaseComponent[] winnerButtons = new BaseComponent[2];
    winnerButtons[0] = (BaseComponent)new TextComponent(ChatColor.GREEN + "Winner: ");
    TextComponent winnerButton = new TextComponent(winner.getDisplayName());
    winnerButton.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, (new ComponentBuilder("Click to view Inventory")).create()));
    winnerButton.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/inv " + winner.getUniqueId()));
    BaseComponent[] loserButtons = new BaseComponent[loserTeam.size() + 1];
    loserButtons[0] = (BaseComponent)new TextComponent(ChatColor.RED + "Losers: ");
    for (int i = 1; i <= this.all.size(); i++) {
      Player next = this.all.get(i - 1);
      if (!winnerTeam.contains(next)) {
        TextComponent button = new TextComponent(next.getDisplayName() + ", ");
        button.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, (new ComponentBuilder("Click to view Inventory")).create()));
        button.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/inv " + next.getUniqueId()));
        loserButtons[i] = (BaseComponent)button;
      } 
    } 
    winner.spigot().sendMessage(winnerButtons);
    winner.spigot().sendMessage(loserButtons);
    for (Player player : this.spectators) {
      player.spigot().sendMessage(winnerButtons);
      player.spigot().sendMessage(loserButtons);
    } 
  }
  
  public int getKills(Player player) {
    return ((Integer)this.kills.getOrDefault(player, Integer.valueOf(0))).intValue();
  }
}
