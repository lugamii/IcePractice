package de.kontux.icepractice.scoreboard.updaters.event;

import de.kontux.icepractice.scoreboard.ScoreboardManager;
import de.kontux.icepractice.tournaments.Tournament;
import de.kontux.icepractice.tournaments.fights.solo.SumoTournamentDuel;
import de.kontux.icepractice.tournaments.types.solo.SumoSoloEvent;
import org.bukkit.entity.Player;

public class SumoSoloEventUpdater extends EventScoreboardUpdater {
  public SumoSoloEventUpdater(Tournament tournament) {
    super(tournament);
  }
  
  protected Runnable getTask() {
    return () -> {
        SumoTournamentDuel fight = ((SumoSoloEvent)this.tournament).getOnGoingFight();
        Player player1 = fight.getPlayer1();
        Player player2 = fight.getPlayer2();
        for (Player player : this.tournament.getParticipants())
          ScoreboardManager.getInstance().setSumoSoloEventBoard(player, player1, player2, this.tournament.getParticipants().size()); 
        for (Player player : this.tournament.getSpectators())
          ScoreboardManager.getInstance().setSumoSoloEventBoard(player, player1, player2, this.tournament.getParticipants().size()); 
      };
  }
}
