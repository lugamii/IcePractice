package de.kontux.icepractice.api.match;

import de.kontux.icepractice.api.arena.IcePracticeArena;
import de.kontux.icepractice.api.kit.IcePracticeKit;
import de.kontux.icepractice.api.match.misc.FightStatistics;
import java.util.List;
import org.bukkit.entity.Player;

public interface IcePracticeFight extends Spectatable {
  void runMatch();
  
  void killPlayer(Player paramPlayer1, Player paramPlayer2);
  
  void endFight(Player paramPlayer);
  
  void expireCooldown();
  
  List<Player> getWinnerTeam(Player paramPlayer);
  
  List<Player> getLoserTeam(Player paramPlayer);
  
  void sendEndMessages(List<Player> paramList1, List<Player> paramList2);
  
  Player getNext(Player paramPlayer);
  
  Player getNextTotal(Player paramPlayer);
  
  List<Player> getPlayers();
  
  IcePracticeKit getKit();
  
  IcePracticeArena getArena();
  
  boolean isRanked();
  
  FightStatistics getMatchStatistics();
}
