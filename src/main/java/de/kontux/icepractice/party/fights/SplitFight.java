package de.kontux.icepractice.party.fights;

import de.kontux.icepractice.IcePracticePlugin;
import de.kontux.icepractice.api.event.fight.FightEndEvent;
import de.kontux.icepractice.api.kit.IcePracticeKit;
import de.kontux.icepractice.api.match.IcePracticeFight;
import de.kontux.icepractice.api.playerstates.PlayerState;
import de.kontux.icepractice.match.types.TeamFight;
import de.kontux.icepractice.party.Party;
import de.kontux.icepractice.playermanagement.PlayerStates;
import de.kontux.icepractice.postiventories.AfterMatchInventory;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class SplitFight extends TeamFight {
  protected final Party party;
  
  public SplitFight(List<Player> team1, List<Player> team2, IcePracticeKit kit, Party party) {
    super(team1, team2, kit);
    this.party = party;
  }
  
  public void endFight(Player lastDead) {
    FightEndEvent event = fireEndEvent(getWinnerTeam(lastDead), getLoserTeam(lastDead));
    if (event.isCancelled())
      return; 
    IcePracticePlugin.broadCastMessage(this.players, this.messages.getEndMessage());
    IcePracticePlugin.broadCastMessage(this.spectators, this.messages.getEndMessage());
    if (this.sumoListeners != null)
      this.sumoListeners.unregister(); 
    if (this.scoreboard != null)
      this.scoreboard.stopUpdater(); 
    for (Player winner : this.players) {
      (new AfterMatchInventory(winner, (IcePracticeFight)this)).initializeInventory();
      PlayerStates.getInstance().setState(winner, PlayerState.IDLE);
    } 
    Bukkit.getScheduler().scheduleSyncDelayedTask((Plugin)IcePracticePlugin.getInstance(), () -> {
          this.matchStatistics.recoverArena();
          sendEndMessages(getWinnerTeam(lastDead), getLoserTeam(lastDead));
          this.party.endMatch();
          this.players.clear();
          for (Player current : new ArrayList<>(this.spectators))
            removeSpectator(current, false); 
          this.spectators.clear();
          unregisterMatch();
        },80L);
  }
  
  public final Party getParty() {
    return this.party;
  }
}
