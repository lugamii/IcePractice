package de.kontux.icepractice.commands.partysubcommands;

import de.kontux.icepractice.api.kit.IcePracticeKit;
import de.kontux.icepractice.configs.repositories.messages.BasicMessageRepository;
import de.kontux.icepractice.match.types.TeamFight;
import de.kontux.icepractice.party.Party;
import de.kontux.icepractice.registries.PartyDuelRequestRegistry;
import de.kontux.icepractice.registries.PartyRegistry;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PartyAcceptCommand implements PartyCommand {
  private final Player player;
  
  private final String leaderName;
  
  private final BasicMessageRepository messages = new BasicMessageRepository();
  
  public PartyAcceptCommand(Player player, String leaderName) {
    this.player = player;
    this.leaderName = leaderName;
  }
  
  public void execute() {
    Player leader = Bukkit.getServer().getPlayer(this.leaderName);
    if (leader == null) {
      this.player.sendMessage("§cCannot find player.");
      return;
    } 
    if (PartyRegistry.isInParty(this.player)) {
      Party ownParty = PartyRegistry.getPartyByPlayer(this.player);
      Party opponentParty = PartyRegistry.getPartyByPlayer(leader);
      if (opponentParty != null) {
        if (PartyDuelRequestRegistry.hasSentRequest(opponentParty, ownParty)) {
          IcePracticeKit kit = PartyDuelRequestRegistry.getKit(opponentParty, ownParty);
          callMatch(opponentParty, ownParty, kit);
          PartyDuelRequestRegistry.removeRequest(opponentParty, ownParty);
        } else {
          this.player.sendMessage(this.messages.getHasNotSentRequest(this.leaderName));
        } 
      } else {
        this.player.sendMessage("§cThis player is not hosting a party at the moment.");
      } 
    } else {
      this.player.sendMessage("§cYou are not in a party.");
    } 
  }
  
  private void callMatch(Party party1, Party party2, IcePracticeKit kit) {
    List<Player> team1 = new ArrayList<>(party1.getMembers());
    List<Player> team2 = new ArrayList<>(party2.getMembers());
    TeamFight teamFight = new TeamFight(team1, team2, kit);
    teamFight.runMatch();
  }
}
