package de.kontux.icepractice.tournaments.fights.team;

import de.kontux.icepractice.api.kit.IcePracticeKit;
import de.kontux.icepractice.match.types.TeamFight;
import de.kontux.icepractice.tournaments.Tournament;
import java.util.List;
import org.bukkit.entity.Player;

public abstract class TournamentTeamFight extends TeamFight {
  protected final Tournament tournament;
  
  public TournamentTeamFight(List<Player> team1, List<Player> team2, IcePracticeKit kit, Tournament tournament) {
    super(team1, team2, kit, tournament.getParticipants());
    this.tournament = tournament;
  }
}
