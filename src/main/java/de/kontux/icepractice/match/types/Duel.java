package de.kontux.icepractice.match.types;

import de.kontux.icepractice.IcePracticePlugin;
import de.kontux.icepractice.api.event.fight.FightStartEvent;
import de.kontux.icepractice.api.kit.IcePracticeKit;
import de.kontux.icepractice.configs.Settings;
import de.kontux.icepractice.match.Fight;
import de.kontux.icepractice.protocol.EntityHider;
import de.kontux.icepractice.scoreboard.updaters.fight.DuelFightScoreboardUpdater;
import de.kontux.icepractice.scoreboard.updaters.fight.FightScoreboardUpdater;
import de.kontux.icepractice.userdata.PlayerDataRepository;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class Duel extends Fight {
  protected Player player1;
  
  protected Player player2;
  
  public Duel(Player player1, Player player2, IcePracticeKit kit, boolean ranked) {
    super(new LinkedList(Arrays.asList((Object[])new Player[] { player1, player2 })), kit, ranked);
    this.player1 = player1;
    this.player2 = player2;
  }
  
  public Duel(Player player1, Player player2, IcePracticeKit kit, boolean ranked, List<Player> spectators) {
    super(new LinkedList(Arrays.asList((Object[])new Player[] { player1, player2 })), kit, ranked, spectators);
    this.player1 = player1;
    this.player2 = player2;
  }
  
  public void runMatch() {
    FightStartEvent event = fireStartEvent();
    if (event.isCancelled()) {
      this.players.clear();
      return;
    } 
    if (this.arena == null) {
      IcePracticePlugin.broadCastMessage(this.players, this.messages.getNoArenaMessage());
      IcePracticePlugin.broadCastMessage(this.spectators, this.messages.getNoArenaMessage());
      return;
    } 
    if (this.kit.isSumo() || this.kit.isSpleef())
      handleSumo(); 
    sendBeginningMessages();
    startScoreboardUpdater();
    registerMatch();
    startCooldown();
    spawnPlayer(this.player1, this.arena.getPos1());
    spawnPlayer(this.player2, this.arena.getPos2());
  }
  
  protected void sendBeginningMessages() {
    this.player1.sendMessage(this.messages.getStartingMessage(this.player2, this.kit.getName()));
    this.player2.sendMessage(this.messages.getStartingMessage(this.player1, this.kit.getName()));
  }
  
  protected void startScoreboardUpdater() {
    this.scoreboard = (FightScoreboardUpdater)new DuelFightScoreboardUpdater(this);
    this.scoreboard.startUpdater();
  }
  
  protected void processDeath(Player dead, Player killer) {}
  
  public void addSpectator(Player player, boolean announce) {
    handleSpectator(player);
    player.sendMessage(this.messages.getSoloSpectatorMessage(this.player1, this.player2, this.kit.getName()));
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
  
  public void sendEndMessages(List<Player> winners, List<Player> losers) {
    Player winner = winners.get(0);
    Player loser = losers.get(0);
    TextComponent winnerButton = new TextComponent(ChatColor.GREEN + "Winner: " + winner.getDisplayName());
    TextComponent loserButton = new TextComponent(ChatColor.RED + "Loser: " + loser.getDisplayName());
    winnerButton.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/inv " + winner.getUniqueId()));
    loserButton.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/inv " + loser.getUniqueId()));
    winnerButton.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, (new ComponentBuilder("Click to view")).create()));
    loserButton.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, (new ComponentBuilder("Click to view")).create()));
    for (Player current : winners) {
      current.sendMessage("§7§m------------------------------");
      current.sendMessage(Settings.PRIMARY + "§lPost-Match Inventories");
      current.sendMessage("");
      current.spigot().sendMessage(winnerButton);
      current.spigot().sendMessage(loserButton);
      current.sendMessage("§7§m------------------------------");
    } 
    for (Player current : losers) {
      current.sendMessage("§7§m------------------------------");
      current.sendMessage(Settings.PRIMARY + "§lPost-Match Inventories");
      current.sendMessage("");
      current.spigot().sendMessage(winnerButton);
      current.spigot().sendMessage(loserButton);
      current.sendMessage("§7§m------------------------------");
    } 
    for (Player current : this.spectators) {
      if (!losers.contains(current)) {
        current.sendMessage("§7§m------------------------------");
        current.sendMessage(Settings.PRIMARY + "§lPost-Match Inventories");
        current.sendMessage("");
        current.spigot().sendMessage(winnerButton);
        current.spigot().sendMessage(loserButton);
        current.sendMessage("§7§m------------------------------");
      }
    } 
  }
  
  protected boolean isMatchOver() {
    return (this.players.size() == 1);
  }
  
  public final void changeElo(Player winner, Player loser) {
    PlayerDataRepository repo = new PlayerDataRepository(IcePracticePlugin.getInstance().getRepository());
    int eloChange = (new Random()).nextInt(IcePracticePlugin.getInstance().getConfig().getInt("config.match.max-elo-change"));
    int winnerElo = repo.getElo(winner.getUniqueId(), this.kit) + eloChange;
    int loserElo = repo.getElo(loser.getUniqueId(), this.kit) - eloChange;
    for (Player current : this.players) {
      current.sendMessage(ChatColor.GREEN + "Elo changes:");
      current.sendMessage(ChatColor.GRAY + winner.getDisplayName() + ": " + ChatColor.GREEN + winnerElo);
      current.sendMessage(ChatColor.GRAY + loser.getDisplayName() + ": " + ChatColor.RED + loserElo);
      current.sendMessage("");
    } 
    for (Player current : this.spectators) {
      current.sendMessage(ChatColor.GREEN + "Elo changes:");
      current.sendMessage(ChatColor.GRAY + winner.getDisplayName() + ": " + ChatColor.GREEN + winnerElo);
      current.sendMessage(ChatColor.GRAY + loser.getDisplayName() + ": " + ChatColor.RED + loserElo);
      current.sendMessage("");
    } 
    repo.setElo(winner.getUniqueId(), winnerElo, this.kit);
    repo.setElo(loser.getUniqueId(), loserElo, this.kit);
  }
  
  public List<Player> getWinnerTeam(Player lastDead) {
    if (this.player1.getUniqueId().equals(lastDead.getUniqueId()))
      return Collections.singletonList(this.player2); 
    return Collections.singletonList(this.player1);
  }
  
  public List<Player> getLoserTeam(Player lastDead) {
    if (this.player1.getUniqueId().equals(lastDead.getUniqueId()))
      return Collections.singletonList(this.player1); 
    return Collections.singletonList(this.player2);
  }
  
  public Player getPlayer1() {
    return this.player1;
  }
  
  public Player getPlayer2() {
    return this.player2;
  }
}
