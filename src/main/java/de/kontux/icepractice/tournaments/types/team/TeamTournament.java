package de.kontux.icepractice.tournaments.types.team;

import de.kontux.icepractice.IcePracticePlugin;
import de.kontux.icepractice.api.kit.IcePracticeKit;
import de.kontux.icepractice.configs.Settings;
import de.kontux.icepractice.registries.EventRegistry;
import de.kontux.icepractice.tournaments.EventType;
import de.kontux.icepractice.tournaments.Tournament;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.entity.Player;

public abstract class TeamTournament extends Tournament {
  private final int teamSize;
  
  protected List<List<Player>> teams = new ArrayList<>();
  
  public TeamTournament(String eventName, int eventId, Player host, int teamSize, String password, EventType type, IcePracticeKit kit) {
    super(eventName, eventId, host, password, type, kit);
    this.teamSize = teamSize;
  }
  
  protected void matchRemainingPlayers() {
    for (Player player : this.participants) {
      boolean hasTeam = false;
      for (List<Player> team : this.teams) {
        if (team.contains(player)) {
          hasTeam = true;
          break;
        } 
      } 
      if (!hasTeam)
        for (List<Player> team : this.teams) {
          if (team.size() < this.teamSize) {
            team.add(player);
            int index = this.teams.indexOf(team);
            this.teams.set(index, team);
            for (Player teamMember : team)
              teamMember.sendMessage(Settings.PRIMARY + "You were put into a team with " + Settings.SECONDARY + ((Player)team.get(0)).getDisplayName()); 
            hasTeam = true;
          } 
        }  
      if (!hasTeam) {
        List<Player> newTeam = new ArrayList<>();
        newTeam.add(player);
        this.teams.add(newTeam);
      } 
    } 
  }
  
  public void startMatchCircuit() {
    matchRemainingPlayers();
    if (this.teams.size() < 2) {
      IcePracticePlugin.broadCastMessage(this.participants, this.messages.getNotEnoughPlayersMessage());
      endEvent();
      return;
    } 
    EventRegistry.removeStartingEvent(this);
    EventRegistry.addRunningEvent(this);
    this.broadcast.stopBroadcast();
    IcePracticePlugin.broadCastMessage(this.participants, this.messages.getStartMessage());
    startNextFights();
  }
}
