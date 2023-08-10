package de.kontux.icepractice.registries;

import de.kontux.icepractice.api.kit.IcePracticeKit;
import de.kontux.icepractice.api.match.IcePracticeFight;
import de.kontux.icepractice.api.match.IcePracticeFightRegistry;
import de.kontux.icepractice.api.match.misc.FightStatistics;
import de.kontux.icepractice.api.playerstates.PlayerState;
import de.kontux.icepractice.playermanagement.PlayerStates;
import de.kontux.icepractice.scoreboard.ScoreboardManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class FightRegistry implements IcePracticeFightRegistry {
  private final List<IcePracticeFight> runningFights = new ArrayList<>();
  
  private final HashMap<UUID, IcePracticeFight> fights = new HashMap<>();
  
  private static final FightRegistry INSTANCE = new FightRegistry();
  
  public static FightRegistry getInstance() {
    return INSTANCE;
  }
  
  public void addMatch(IcePracticeFight fight) {
    this.runningFights.add(fight);
    for (Player current : Bukkit.getOnlinePlayers()) {
      if (PlayerStates.getInstance().getState(current) == PlayerState.IDLE)
        ScoreboardManager.getInstance().setIdleBoard(current); 
    } 
    if (fight.getArena().isBuild())
      fight.getArena().setInUse(true); 
    for (Player player : fight.getPlayers())
      this.fights.put(player.getUniqueId(), fight); 
  }
  
  public void removeMatch(IcePracticeFight fight) {
    this.runningFights.remove(fight);
    for (Player current : Bukkit.getOnlinePlayers()) {
      if (PlayerStates.getInstance().getState(current) == PlayerState.IDLE)
        ScoreboardManager.getInstance().setIdleBoard(current); 
    } 
    fight.getArena().setInUse(false);
    for (Player player : fight.getPlayers())
      this.fights.remove(player.getUniqueId()); 
  }
  
  public IcePracticeFight getFightByPlayer(Player player) {
    return this.fights.get(player.getUniqueId());
  }
  
  public IcePracticeFight getSpectatorFight(Player player) {
    for (IcePracticeFight fight : this.runningFights) {
      if (fight.getSpectators().contains(player))
        return fight; 
    } 
    return null;
  }
  
  public int getPlayersPlaying(IcePracticeKit kit, boolean ranked) {
    int playerAmount = 0;
    for (IcePracticeFight fight : this.runningFights) {
      if (fight.getKit().equals(kit) && fight.isRanked() == ranked)
        playerAmount += fight.getPlayers().size(); 
    } 
    return playerAmount;
  }
  
  public FightStatistics getMatchStatistics(Player player) {
    return ((IcePracticeFight)this.fights.get(player.getUniqueId())).getMatchStatistics();
  }
  
  public List<IcePracticeFight> getAllFights() {
    return this.runningFights;
  }
  
  public List<Player> getAllPlayers() {
    List<Player> allPlayers = new ArrayList<>();
    for (IcePracticeFight fight : this.runningFights)
      allPlayers.addAll(fight.getPlayers()); 
    return allPlayers;
  }
}
