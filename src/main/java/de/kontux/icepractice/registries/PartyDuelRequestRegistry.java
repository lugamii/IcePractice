package de.kontux.icepractice.registries;

import de.kontux.icepractice.IcePracticePlugin;
import de.kontux.icepractice.api.kit.IcePracticeKit;
import de.kontux.icepractice.configs.repositories.messages.PartyMessageRepository;
import de.kontux.icepractice.match.PartyDuelRequest;
import de.kontux.icepractice.party.Party;
import java.util.ArrayList;
import java.util.List;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class PartyDuelRequestRegistry {
  private static final List<PartyDuelRequest> REQUESTS = new ArrayList<>();
  
  public static void sendRequest(IcePracticeKit kit, Party sender, Party challenged) {
    PartyDuelRequest request = new PartyDuelRequest(sender, challenged, kit);
    REQUESTS.add(request);
    IcePracticePlugin.broadCastMessage(sender.getMembers(), (new PartyMessageRepository()).getSentRequestMessage(sender.getLeader(), kit.getName()));
    IcePracticePlugin.broadCastMessage(challenged.getMembers(), (new PartyMessageRepository()).getReceivedRequestMessage(sender.getLeader(), kit.getName()));
    TextComponent button = new TextComponent(ChatColor.GREEN + "[Click to accept]");
    button.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, (new ComponentBuilder(ChatColor.GREEN + "Click to accept")).create()));
    button.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/p accept " + sender.getLeader().getPlayerListName()));
    challenged.getLeader().spigot().sendMessage((BaseComponent)button);
  }
  
  public static void removeRequest(Party sender, Party challenged) {
    PartyDuelRequest requestToRemove = null;
    for (PartyDuelRequest request : REQUESTS) {
      if (sender.equals(request.getSender()) && challenged.equals(request.getRequested()))
        requestToRemove = request; 
    } 
    if (requestToRemove != null) {
      REQUESTS.remove(requestToRemove);
    } else {
      IcePracticePlugin.getInstance().getLogger().info("Failed to remove the duel request from the PartyDuelRequestRegistry.");
    } 
  }
  
  public static boolean hasSentRequest(Party sender, Party challenged) {
    boolean hasSentRequest = false;
    for (PartyDuelRequest request : REQUESTS) {
      if (request.getSender().getLeader().equals(sender.getLeader()) && request.getRequested().getLeader().equals(challenged.getLeader())) {
        hasSentRequest = true;
        break;
      } 
    } 
    return hasSentRequest;
  }
  
  public static IcePracticeKit getKit(Party sender, Party challenged) {
    IcePracticeKit kit = null;
    for (PartyDuelRequest request : REQUESTS) {
      if (sender.equals(request.getSender()) && challenged.equals(request.getRequested()))
        kit = request.getKit(); 
    } 
    return kit;
  }
}
