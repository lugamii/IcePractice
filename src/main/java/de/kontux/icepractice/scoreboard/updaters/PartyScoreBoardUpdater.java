package de.kontux.icepractice.scoreboard.updaters;

import de.kontux.icepractice.party.Party;
import de.kontux.icepractice.scoreboard.ScoreboardManager;
import org.bukkit.entity.Player;

public class PartyScoreBoardUpdater {
  private final Party party;
  
  public PartyScoreBoardUpdater(Party party) {
    this.party = party;
  }
  
  public void startUpdating() {
    for (Player current : this.party.getMembers())
      ScoreboardManager.getInstance().setPartyBoard(current, this.party.getLeader(), this.party.getMembers().size()); 
  }
  
  public void updateBoard() {
    for (Player current : this.party.getMembers())
      ScoreboardManager.getInstance().setPartyBoard(current, this.party.getLeader(), this.party.getMembers().size()); 
  }
}
