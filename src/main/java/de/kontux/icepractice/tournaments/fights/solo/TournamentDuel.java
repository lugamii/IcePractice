package de.kontux.icepractice.tournaments.fights.solo;

import de.kontux.icepractice.api.kit.IcePracticeKit;
import de.kontux.icepractice.match.types.Duel;
import de.kontux.icepractice.tournaments.Tournament;
import org.bukkit.entity.Player;

public abstract class TournamentDuel extends Duel {
  protected Tournament tournament;
  
  public TournamentDuel(Player player1, Player player2, IcePracticeKit kit, Tournament tournament) {
    super(player1, player2, kit, false, tournament.getParticipants());
    this.tournament = tournament;
  }
}
