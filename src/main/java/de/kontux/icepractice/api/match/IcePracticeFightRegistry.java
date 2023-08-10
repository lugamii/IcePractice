package de.kontux.icepractice.api.match;

import de.kontux.icepractice.api.kit.IcePracticeKit;
import java.util.List;
import org.bukkit.entity.Player;

public interface IcePracticeFightRegistry {
  void addMatch(IcePracticeFight paramIcePracticeFight);
  
  void removeMatch(IcePracticeFight paramIcePracticeFight);
  
  IcePracticeFight getFightByPlayer(Player paramPlayer);
  
  int getPlayersPlaying(IcePracticeKit paramIcePracticeKit, boolean paramBoolean);
  
  List<IcePracticeFight> getAllFights();
  
  List<Player> getAllPlayers();
}
