package de.kontux.icepractice.tournaments.fights.solo;

import de.kontux.icepractice.IcePracticePlugin;
import de.kontux.icepractice.api.arena.IcePracticeArena;
import de.kontux.icepractice.api.event.fight.FightEndEvent;
import de.kontux.icepractice.api.match.IcePracticeFight;
import de.kontux.icepractice.api.playerstates.PlayerState;
import de.kontux.icepractice.configs.repositories.messages.EventMessageRepository;
import de.kontux.icepractice.match.Fight;
import de.kontux.icepractice.playermanagement.PlayerStates;
import de.kontux.icepractice.postiventories.AfterMatchInventory;
import de.kontux.icepractice.tournaments.Tournament;
import de.kontux.icepractice.tournaments.types.solo.SumoSoloEvent;
import java.util.List;
import org.bukkit.entity.Player;

public class SumoTournamentDuel extends TournamentDuel {
  private final EventMessageRepository messages = new EventMessageRepository();
  
  public SumoTournamentDuel(Player player1, Player player2, SumoSoloEvent event) {
    super(player1, player2, event.getKit(), (Tournament)event);
    this.arena = (IcePracticeArena)event.getArena();
  }
  
  protected void sendBeginningMessages() {
    IcePracticePlugin.broadCastMessage(this.tournament.getParticipants(), this.messages.getStartingMatchMessage(this.player1.getDisplayName(), this.player2.getDisplayName()));
  }
  
  public void sendEndMessages(List<Player> winnerTeam, List<Player> loserTeam) {}
  
  public void endFight(Player lastDead) {
    FightEndEvent event = fireEndEvent(getWinnerTeam(lastDead), getLoserTeam(lastDead));
    if (event.isCancelled())
      return; 
    for (Player current : this.players) {
      (new AfterMatchInventory(current, (IcePracticeFight)this)).initializeInventory();
      PlayerStates.getInstance().setState(current, PlayerState.IDLE);
    } 
    if (this.scoreboard != null)
      this.scoreboard.stopUpdater(); 
    if (this.sumoListeners != null)
      this.sumoListeners.unregister(); 
    this.tournament.endFight((Fight)this, getWinnerTeam(lastDead), getLoserTeam(lastDead));
    this.sumoListeners.unregister();
    unregisterMatch();
  }
}
