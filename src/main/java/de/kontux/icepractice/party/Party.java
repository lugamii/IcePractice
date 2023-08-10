package de.kontux.icepractice.party;

import de.kontux.icepractice.IcePracticePlugin;
import de.kontux.icepractice.api.kit.IcePracticeKit;
import de.kontux.icepractice.api.playerstates.PlayerState;
import de.kontux.icepractice.configs.Settings;
import de.kontux.icepractice.configs.repositories.messages.PartyMessageRepository;
import de.kontux.icepractice.items.party.PartyItemManager;
import de.kontux.icepractice.locations.SpawnPointHandler;
import de.kontux.icepractice.match.Fight;
import de.kontux.icepractice.party.fights.FFAFight;
import de.kontux.icepractice.party.fights.RedRover;
import de.kontux.icepractice.party.fights.SplitFight;
import de.kontux.icepractice.playermanagement.PlayerStates;
import de.kontux.icepractice.protocol.EntityHider;
import de.kontux.icepractice.queue.PartyQueue;
import de.kontux.icepractice.registries.PartyDuelRequestRegistry;
import de.kontux.icepractice.registries.PartyRegistry;
import de.kontux.icepractice.scoreboard.updaters.PartyScoreBoardUpdater;
import de.kontux.icepractice.userdata.PlayerDataManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

public class Party {
  private static final PartyMessageRepository MESSAGES = new PartyMessageRepository();
  
  private final List<Player> members = new ArrayList<>();
  
  private final List<Player> invited = new ArrayList<>();
  
  private final PartyScoreBoardUpdater scoreBoardUpdater;

  private final FileConfiguration config = IcePracticePlugin.getInstance().getConfig();
  
  private Player leader;
  
  private boolean open = false;
  
  private Fight onGoingFight = null;
  
  public Party(Player leader) {
    this.leader = leader;
    this.scoreBoardUpdater = new PartyScoreBoardUpdater(this);
  }
  
  public void createParty() {
    PartyRegistry.addParty(this);
    this.members.add(this.leader);
    teleportToSpawn(this.leader);
    this.leader.sendMessage(MESSAGES.getCreateMessage());
    this.scoreBoardUpdater.startUpdating();
  }
  
  public void sendMessage(String message, Player sender) {
    IcePracticePlugin.broadCastMessage(this.members, ChatColor.YELLOW + "[Party]" + ChatColor.GREEN + sender.getDisplayName() + ChatColor.WHITE + ": " + message);
  }
  
  public void joinQueue(IcePracticeKit kit) {
    for (Player current : this.members) {
      current.getInventory().clear();
      current.getInventory().setItem(8, PartyQueue.PARTY_QUEUE_ITEM);
    } 
    PartyQueue.addToQueue(this, kit);
  }
  
  public void leaveQueue() {
    PartyQueue.removeFromQueue(this);
    for (Player current : this.members)
      PartyItemManager.getInstance().giveItems(current); 
  }
  
  public void sendDuelRequest(Party opponentParty, IcePracticeKit kit) {
    PartyDuelRequestRegistry.sendRequest(kit, this, opponentParty);
  }
  
  public void teleportToSpawn(Player player) {
    for (PotionEffect effect : player.getActivePotionEffects())
      player.removePotionEffect(effect.getType()); 
    boolean showPlayers = PlayerDataManager.getSettingsData(player.getUniqueId()).isShowPlayers();
    for (Player current : player.getWorld().getPlayers()) {
      boolean currentShowPlayers = PlayerDataManager.getSettingsData(current.getUniqueId()).isShowPlayers();
      if (currentShowPlayers) {
        EntityHider.getInstance().showEntity(player, (Entity)current);
      } else {
        EntityHider.getInstance().hideEntity(player, (Entity)current);
      } 
      if (showPlayers) {
        EntityHider.getInstance().showEntity(current, (Entity)player);
        continue;
      } 
      EntityHider.getInstance().hideEntity(current, (Entity)player);
    } 
    player.setAllowFlight(false);
    player.setFlying(false);
    PlayerStates.getInstance().setState(player, PlayerState.IDLE);
    player.teleport(SpawnPointHandler.getSpawn());
    player.getInventory().clear();
    PartyItemManager.getInstance().giveItems(player);
    this.scoreBoardUpdater.updateBoard();
  }
  
  public void setOpen(Player player, boolean isOpen) {
    if (player.equals(this.leader)) {
      if (!isOpen) {
        this.open = false;
        IcePracticePlugin.broadCastMessage(this.members, MESSAGES.getCloseMessage());
      } else {
        this.open = true;
        IcePracticePlugin.broadCastMessage(this.members, MESSAGES.getOpenMessage());
      } 
    } else {
      player.sendMessage(MESSAGES.getOnlyLeaderMessage());
    } 
  }
  
  public void joinPlayer(Player player) {
    if (this.open) {
      addPlayer(player);
    } else if (this.invited.contains(player)) {
      addPlayer(player);
    } else {
      player.sendMessage(MESSAGES.getNotInvitedMessage());
    } 
  }
  
  public void invitePlayer(Player player, Player guest) {
    this.invited.add(guest);
    player.sendMessage(MESSAGES.getInvitedMessage(guest));
    TextComponent msg = new TextComponent(MESSAGES.getInviteRequestMessage(player) + ChatColor.GREEN + " [Click to join]");
    msg.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/party join " + this.leader.getPlayerListName()));
    msg.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, (new ComponentBuilder(ChatColor.GREEN + "Click to join")).create()));
    guest.spigot().sendMessage((BaseComponent)msg);
  }
  
  public void showInfo(Player player) {
    player.sendMessage(" ");
    player.sendMessage(Settings.PRIMARY + "Leader: " + Settings.SECONDARY + this.leader.getDisplayName());
    player.sendMessage(Settings.PRIMARY + "Party members: ");
    for (Player current : this.members)
      player.sendMessage(Settings.PRIMARY + " - " + current.getDisplayName()); 
    player.sendMessage(" ");
  }
  
  public void disband() {
    for (Player current : this.members) {
      current.sendMessage("The party was disbanded.");
      SpawnPointHandler.teleportToSpawn(current);
    } 
    this.members.clear();
    PartyRegistry.removeParty(this);
    PartyQueue.removeFromQueue(this);
  }
  
  private void addPlayer(Player player) {
    this.members.add(player);
    IcePracticePlugin.broadCastMessage(this.members, MESSAGES.getJoinedMessage(player));
    teleportToSpawn(player);
    this.scoreBoardUpdater.updateBoard();
  }
  
  public void leavePlayer(Player player) {
    this.members.remove(player);
    SpawnPointHandler.teleportToSpawn(player);
    if (this.onGoingFight != null) {
      this.onGoingFight.killPlayer(player, null);
      this.onGoingFight.removeSpectator(player, true);
    } else {
      this.scoreBoardUpdater.updateBoard();
    } 
    if (this.members.isEmpty()) {
      disband();
      return;
    } 
    IcePracticePlugin.broadCastMessage(this.members, MESSAGES.getLeftMessage(player));
    if (player.equals(this.leader)) {
      this.leader = this.members.get((new Random()).nextInt(this.members.size()));
      for (Player current : this.members)
        current.sendMessage(MESSAGES.getNowLeaderMessage(this.leader)); 
    } 
  }
  
  public void kickPlayer(Player player, Player playerToKick) {
    if (!player.equals(this.leader)) {
      player.sendMessage(MESSAGES.getOnlyLeaderMessage());
      return;
    } 
    if (playerToKick != null) {
      if (getMembers().contains(playerToKick)) {
        IcePracticePlugin.broadCastMessage(this.members, MESSAGES.getKickedMessage(player));
        this.members.remove(player);
      } else {
        player.sendMessage("§cThis player is not in your party.");
      } 
    } else {
      player.sendMessage("Cannot find that player.");
    } 
    this.scoreBoardUpdater.updateBoard();
  }
  
  public void endMatch() {
    this.onGoingFight = null;
    for (Player current : this.members)
      teleportToSpawn(current); 
  }
  
  public void startSplitMatch(IcePracticeKit kit) {
    if (this.members.size() < 2) {
      this.leader.sendMessage(MESSAGES.getNotEnoughPlayersMessage());
      return;
    } 
    int teamSize = this.members.size() / 2;
    List<Player> team1 = new ArrayList<>(this.members.subList(0, teamSize));
    List<Player> team2 = new ArrayList<>(this.members.subList(teamSize, this.members.size()));
    SplitFight fight = new SplitFight(team1, team2, kit, this);
    fight.runMatch();
    this.onGoingFight = (Fight)fight;
  }
  
  public void startFFAMatch(IcePracticeKit kit) {
    if (this.members.size() < 2) {
      this.leader.sendMessage(MESSAGES.getNotEnoughPlayersMessage());
      return;
    } 
    FFAFight fight = new FFAFight(this, kit);
    fight.runMatch();
    this.onGoingFight = (Fight)fight;
  }
  
  public void startRedRover(IcePracticeKit kit) {
    if (this.members.size() < 2) {
      this.leader.sendMessage(MESSAGES.getNotEnoughPlayersMessage());
      return;
    } 
    int teamSize = this.members.size() / 2;
    List<Player> team1 = new ArrayList<>(this.members.subList(0, teamSize));
    List<Player> team2 = new ArrayList<>(this.members.subList(teamSize, this.members.size()));
    RedRover redRover = new RedRover(team1, team2, kit, this);
    redRover.runMatch();
    this.onGoingFight = (Fight)redRover;
  }
  
  public List<Player> getMembers() {
    return this.members;
  }
  
  public Fight getOnGoingFight() {
    return this.onGoingFight;
  }
  
  public boolean isLeader(Player player) {
    return player.getUniqueId().equals(this.leader.getUniqueId());
  }
  
  public Player getLeader() {
    return this.leader;
  }
}
