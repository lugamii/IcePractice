package de.kontux.icepractice.tournaments.types.team;

import de.kontux.icepractice.IcePracticePlugin;
import de.kontux.icepractice.api.kit.IcePracticeKit;
import de.kontux.icepractice.arenas.SumoEventArena;
import de.kontux.icepractice.arenas.SumoEventArenaManager;
import de.kontux.icepractice.registries.EventRegistry;
import de.kontux.icepractice.scoreboard.updaters.event.EventScoreboardUpdater;
import de.kontux.icepractice.scoreboard.updaters.event.SumoTeamEventUpdater;
import de.kontux.icepractice.tournaments.EventType;
import de.kontux.icepractice.tournaments.fights.team.SumoTournamentTeamFight;
import java.util.List;
import java.util.Random;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class SumoTeamTournament extends TeamTournament {
  private SumoEventArena arena;
  
  private SumoTournamentTeamFight onGoingFight;
  
  public SumoTeamTournament(int eventId, Player host, String password, int teamSize, IcePracticeKit kit) {
    super("Sumo Event (" + teamSize + "v" + teamSize + ")", eventId, host, teamSize, password, EventType.SUMO, kit);
    this.scoreboard = (EventScoreboardUpdater)new SumoTeamEventUpdater(this);
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
    if (this.teams.size() < 2) {
      List<Player> winnerTeam = this.teams.get(0);
      StringBuilder winners = new StringBuilder();
      for (Player winner : winnerTeam) {
        winners.append(winner.getDisplayName());
        if (winnerTeam.indexOf(winner) + 1 != winnerTeam.size())
          winners.append(", "); 
      } 
      Bukkit.broadcastMessage("");
      Bukkit.broadcastMessage(this.messages.getWinnerMessage(winners.toString(), this.eventName));
      Bukkit.broadcastMessage("");
      endEvent();
    } else {
      List<Player> team1 = this.teams.get((new Random()).nextInt(this.teams.size()));
      List<Player> team2 = this.teams.get((new Random()).nextInt(this.teams.size()));
      while (team1.equals(team2))
        team2 = this.teams.get((new Random()).nextInt(this.teams.size())); 
      SumoTournamentTeamFight fight = new SumoTournamentTeamFight(team1, team2, this);
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
  
  public SumoTournamentTeamFight getOnGoingFight() {
    return this.onGoingFight;
  }
}
