package de.kontux.icepractice.tournaments.types.solo;

import de.kontux.icepractice.IcePracticePlugin;
import de.kontux.icepractice.api.kit.IcePracticeKit;
import de.kontux.icepractice.arenas.SumoEventArena;
import de.kontux.icepractice.arenas.SumoEventArenaManager;
import de.kontux.icepractice.registries.EventRegistry;
import de.kontux.icepractice.scoreboard.updaters.event.EventScoreboardUpdater;
import de.kontux.icepractice.scoreboard.updaters.event.SumoSoloEventUpdater;
import de.kontux.icepractice.tournaments.EventType;
import de.kontux.icepractice.tournaments.fights.solo.SumoTournamentDuel;
import java.util.Random;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class SumoSoloEvent extends SoloTournament {
  private SumoTournamentDuel onGoingFight;
  
  private SumoEventArena arena;
  
  public SumoSoloEvent(int eventId, Player host, String password, IcePracticeKit kit) {
    super("Sumo Event (1v1)", eventId, host, password, EventType.SUMO, kit);
    this.scoreboard = (EventScoreboardUpdater)new SumoSoloEventUpdater(this);
  }
  
  public void hostEvent() {
    this.arena = SumoEventArenaManager.getInstance().getRandomFreeArena();
    if (this.arena == null) {
      this.host.sendMessage(ChatColor.RED + "There is no arena for sumo events.");
      return;
    } 
    EventRegistry.addStartingEvent(this);
    join(this.host);
    this.broadcast.startBroadcast();
  }
  
  public void startNextFights() {
    if (this.participants.size() < 2) {
      Player winner = this.participants.get(0);
      Bukkit.broadcastMessage("");
      Bukkit.broadcastMessage(this.messages.getWinnerMessage(winner.getDisplayName(), this.eventName));
      Bukkit.broadcastMessage("");
      endEvent();
    } else {
      Player player1 = this.participants.get((new Random()).nextInt(this.participants.size()));
      Player player2 = this.participants.get((new Random()).nextInt(this.participants.size()));
      while (player1.equals(player2))
        player2 = this.participants.get((new Random()).nextInt(this.participants.size())); 
      SumoTournamentDuel fight = new SumoTournamentDuel(player1, player2, this);
      this.onGoingFight = fight;
      this.onGoingFights.add(fight);
      fight.runMatch();
    } 
  }
  
  protected void onMatchEnd() {
    Bukkit.getScheduler().scheduleSyncDelayedTask((Plugin)IcePracticePlugin.getInstance(), this::startNextFights, 40L);
  }
  
  protected void teleportToSpawn(Player player) {
    player.teleport(this.arena.getCenter());
  }
  
  protected void changeInventory(Player player) {
    for (int i = 0; i < 40; i++)
      player.getInventory().clear(i); 
    player.getInventory().setItem(8, LEAVE_ITEM);
  }
  
  public SumoEventArena getArena() {
    return this.arena;
  }
  
  public SumoTournamentDuel getOnGoingFight() {
    return this.onGoingFight;
  }
}
