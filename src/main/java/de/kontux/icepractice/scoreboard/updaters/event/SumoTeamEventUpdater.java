package de.kontux.icepractice.scoreboard.updaters.event;

import de.kontux.icepractice.scoreboard.ScoreboardManager;
import de.kontux.icepractice.tournaments.Tournament;
import de.kontux.icepractice.tournaments.fights.team.SumoTournamentTeamFight;
import de.kontux.icepractice.tournaments.types.team.SumoTeamTournament;
import org.bukkit.entity.Player;

public class SumoTeamEventUpdater extends EventScoreboardUpdater {
  public SumoTeamEventUpdater(Tournament tournament) {
    super(tournament);
  }
  
  protected Runnable getTask() {
    return () -> {
        SumoTournamentTeamFight fight = ((SumoTeamTournament)this.tournament).getOnGoingFight();
        for (Player player : this.tournament.getParticipants())
          ScoreboardManager.getInstance().setSumoTeamEventBoard(player, fight.getTeam1(), fight.getTeam2(), this.tournament.getParticipants().size()); 
        for (Player player : this.tournament.getSpectators())
          ScoreboardManager.getInstance().setSumoTeamEventBoard(player, fight.getTeam1(), fight.getTeam2(), this.tournament.getParticipants().size()); 
      };
  }
}
