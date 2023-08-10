package de.kontux.icepractice.registries;

import de.kontux.icepractice.api.kit.IcePracticeKit;
import de.kontux.icepractice.configs.repositories.messages.BasicMessageRepository;
import de.kontux.icepractice.match.DuelRequest;
import java.util.ArrayList;
import java.util.List;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;

public class DuelRequestRegistry {
  private static final List<DuelRequest> DUEL_REQUESTS = new ArrayList<>();
  
  public static void sendRequest(IcePracticeKit kit, Player sender, Player challenged) {
    DuelRequest request = new DuelRequest(sender.getUniqueId(), challenged.getUniqueId(), kit);
    DUEL_REQUESTS.add(request);
    BasicMessageRepository messages = new BasicMessageRepository();
    TextComponent button = new TextComponent(messages.getRequestMessage(sender, kit.getName()) + ChatColor.GREEN + "[Click to accept]");
    button.setColor(ChatColor.GREEN);
    button.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/accept " + sender.getName()));
    button.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, (new ComponentBuilder(ChatColor.GREEN + "Click to accept the request")).create()));
    sender.sendMessage(messages.getSendRequestMessage(sender, kit.getName()));
    challenged.spigot().sendMessage((BaseComponent)button);
  }
  
  public static boolean hasSentRequest(Player sender, Player challenged) {
    for (DuelRequest request : DUEL_REQUESTS) {
      if (request.getSender().equals(sender.getUniqueId()) && request.getRequested().equals(challenged.getUniqueId()))
        return true; 
    } 
    return false;
  }
  
  public static void removeRequest(Player sender, Player challenged) {
    DuelRequest requestToRemove = null;
    for (DuelRequest request : DUEL_REQUESTS) {
      if (sender.getUniqueId().equals(request.getSender()) && challenged.getUniqueId().equals(request.getRequested()))
        requestToRemove = request; 
    } 
    if (requestToRemove != null)
      DUEL_REQUESTS.remove(requestToRemove); 
  }
  
  public static IcePracticeKit getKit(Player sender, Player challenged) {
    for (DuelRequest request : DUEL_REQUESTS) {
      if (request.getSender().equals(sender.getUniqueId()) && request.getRequested().equals(challenged.getUniqueId()))
        return request.getKit(); 
    } 
    return null;
  }
}
