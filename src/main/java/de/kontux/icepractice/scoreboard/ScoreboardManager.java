package de.kontux.icepractice.scoreboard;

import de.kontux.icepractice.IcePracticePlugin;
import de.kontux.icepractice.api.playerstates.PlayerState;
import de.kontux.icepractice.configs.repositories.ScoreBoardRepository;
import de.kontux.icepractice.playermanagement.PlayerStates;
import de.kontux.icepractice.registries.PartyRegistry;
import de.kontux.icepractice.scoreboard.types.DuelScoreboard;
import de.kontux.icepractice.scoreboard.types.FFAScoreboard;
import de.kontux.icepractice.scoreboard.types.IdleScoreboard;
import de.kontux.icepractice.scoreboard.types.KothScoreboard;
import de.kontux.icepractice.scoreboard.types.PartyScoreboard;
import de.kontux.icepractice.scoreboard.types.QueueScoreboard;
import de.kontux.icepractice.scoreboard.types.StartingEventScoreboard;
import de.kontux.icepractice.scoreboard.types.SumoSoloEventScoreboard;
import de.kontux.icepractice.scoreboard.types.SumoTeamEventScoreboard;
import de.kontux.icepractice.scoreboard.types.TeamFightScoreboard;
import de.kontux.icepractice.util.ConfigUtil;
import java.util.HashMap;
import java.util.List;
import org.bukkit.entity.Player;

public class ScoreboardManager {
  private static final HashMap<ScoreboardType, AbstractScoreboard> BOARDS = new HashMap<>();
  
  private static final ScoreboardManager INSTANCE = new ScoreboardManager();
  
  private ScoreboardManager() {
    loadFromConfig();
  }
  
  public static ScoreboardManager getInstance() {
    return INSTANCE;
  }
  
  private void loadFromConfig() {
    IcePracticePlugin.getInstance().getLogger().info("Loading scoreboards from config...");
    ScoreBoardRepository repo = new ScoreBoardRepository();
    BOARDS.put(ScoreboardType.DUEL, new DuelScoreboard(repo.getScores(ScoreboardType.DUEL)));
    BOARDS.put(ScoreboardType.FFA, new FFAScoreboard(repo.getScores(ScoreboardType.FFA)));
    BOARDS.put(ScoreboardType.IDLE, new IdleScoreboard(repo.getScores(ScoreboardType.IDLE)));
    BOARDS.put(ScoreboardType.KOTH, new KothScoreboard(repo.getScores(ScoreboardType.KOTH)));
    BOARDS.put(ScoreboardType.PARTY, new PartyScoreboard(repo.getScores(ScoreboardType.PARTY)));
    BOARDS.put(ScoreboardType.STARTINGEVENT, new StartingEventScoreboard(repo.getScores(ScoreboardType.STARTINGEVENT)));
    BOARDS.put(ScoreboardType.SUMOSOLOEVENT, new SumoSoloEventScoreboard(repo.getScores(ScoreboardType.SUMOSOLOEVENT)));
    BOARDS.put(ScoreboardType.SUMOTEAMEVENT, new SumoTeamEventScoreboard(repo.getScores(ScoreboardType.SUMOTEAMEVENT)));
    BOARDS.put(ScoreboardType.TEAMFIGHT, new TeamFightScoreboard(repo.getScores(ScoreboardType.TEAMFIGHT)));
    BOARDS.put(ScoreboardType.QUEUE, new QueueScoreboard(repo.getScores(ScoreboardType.QUEUE)));
  }
  
  public void setIdleBoard(Player player) {
    if (PlayerStates.getInstance().getState(player) != PlayerState.IDLE || PartyRegistry.isInParty(player) || !ConfigUtil.useWorld(player.getWorld()))
      return; 
    IdleScoreboard scoreboard = (IdleScoreboard)BOARDS.get(ScoreboardType.IDLE);
    scoreboard.setBoard(player, new Object[0]);
  }
  
  public void setDuelBoard(Player player, Player opponent, String duration) {
    DuelScoreboard scoreboard = (DuelScoreboard)BOARDS.get(ScoreboardType.DUEL);
    scoreboard.setBoard(player, new Object[] { player, opponent, duration });
  }
  
  public void setTeamFightBoard(Player player, int ownSize, int opponentSize, String duration) {
    TeamFightScoreboard scoreboard = (TeamFightScoreboard)BOARDS.get(ScoreboardType.TEAMFIGHT);
    scoreboard.setBoard(player, new Object[] { player, Integer.valueOf(ownSize), Integer.valueOf(opponentSize), duration });
  }
  
  public void setFFABoard(Player player, int playersLeft, int kills, String duration) {
    FFAScoreboard scoreboard = (FFAScoreboard)BOARDS.get(ScoreboardType.FFA);
    scoreboard.setBoard(player, new Object[] { player, Integer.valueOf(playersLeft), Integer.valueOf(kills), duration });
  }
  
  public void setPartyBoard(Player player, Player leader, int members) {
    PartyScoreboard scoreboard = (PartyScoreboard)BOARDS.get(ScoreboardType.PARTY);
    scoreboard.setBoard(player, new Object[] { leader, Integer.valueOf(members) });
  }
  
  public void setKothBoard(Player player, int team1Points, int team2Points, Player captain, String duration) {
    KothScoreboard scoreboard = (KothScoreboard)BOARDS.get(ScoreboardType.KOTH);
    scoreboard.setBoard(player, new Object[] { Integer.valueOf(team1Points), Integer.valueOf(team2Points), captain, duration });
  }
  
  public void setQueueBoard(Player player, String kit, String time) {
    QueueScoreboard scoreboard = (QueueScoreboard)BOARDS.get(ScoreboardType.QUEUE);
    scoreboard.setBoard(player, new Object[] { kit, time });
  }
  
  public void setStartingEventBoard(Player player, String timeLeft, int participants, String kitName) {
    StartingEventScoreboard scoreboard = (StartingEventScoreboard)BOARDS.get(ScoreboardType.STARTINGEVENT);
    scoreboard.setBoard(player, new Object[] { timeLeft, Integer.valueOf(participants), kitName });
  }
  
  public void setSumoSoloEventBoard(Player player, Player player1, Player player2, int remaining) {
    SumoSoloEventScoreboard scoreboard = (SumoSoloEventScoreboard)BOARDS.get(ScoreboardType.SUMOSOLOEVENT);
    scoreboard.setBoard(player, new Object[] { player1, player2, Integer.valueOf(remaining) });
  }
  
  public void setSumoTeamEventBoard(Player player, List<Player> team1, List<Player> team2, int remaining) {
    SumoTeamEventScoreboard scoreboard = (SumoTeamEventScoreboard)BOARDS.get(ScoreboardType.SUMOTEAMEVENT);
    scoreboard.setBoard(player, new Object[] { team1, team2, Integer.valueOf(remaining) });
  }
  
  public void reload() {
    loadFromConfig();
  }
}
