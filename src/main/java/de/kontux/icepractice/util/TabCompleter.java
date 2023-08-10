package de.kontux.icepractice.util;

import de.kontux.icepractice.api.arena.IcePracticeArena;
import de.kontux.icepractice.api.kit.IcePracticeKit;
import de.kontux.icepractice.arenas.ArenaManager;
import de.kontux.icepractice.kits.KitManager;
import de.kontux.icepractice.registries.EventRegistry;
import de.kontux.icepractice.tournaments.Tournament;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class TabCompleter implements org.bukkit.command.TabCompleter {
  public List<String> onTabComplete(CommandSender sender, Command command, String alias, @Nonnull String[] args) {
    List<String> autoCompletions = new ArrayList<>();
    switch (command.getName()) {
      case "kit":
        autoCompletions = getKitAutoCompletions(args);
        break;
      case "arena":
        autoCompletions = getArenaAutoCompletions(args);
        break;
      case "party":
        autoCompletions = getPartyAutoCompletions(args);
        break;
      case "event":
        autoCompletions = getEventAutoCompletions(args);
        break;
      case "editor":
        autoCompletions = getEditorAutoCompletions(args);
        break;
      case "icepractice":
        autoCompletions = getIcePracticeAutoCompletions(args);
        break;
    } 
    return autoCompletions;
  }
  
  private List<String> getKitAutoCompletions(String[] args) {
    List<String> autoCompletions = new ArrayList<>();
    if (args.length == 1) {
      autoCompletions.add("create");
      autoCompletions.add("seticon");
      autoCompletions.add("ranked");
      autoCompletions.add("load");
      autoCompletions.add("delete");
      autoCompletions.add("sumo");
      autoCompletions.add("combo");
      autoCompletions.add("editable");
      autoCompletions.add("hcf");
      autoCompletions.add("spleef");
      autoCompletions.add("build");
      autoCompletions.add("cooldown");
      autoCompletions.add("setinventory");
      autoCompletions.add("list");
      autoCompletions.add("settings");
      autoCompletions.add("options");
    } else if (args.length == 2) {
      for (IcePracticeKit kit : KitManager.getInstance().getKits())
        autoCompletions.add(kit.getName()); 
    } else if ((args.length == 3 && args[0].equals("ranked")) || args[0].equals("sumo") || args[0].equals("combo")) {
      autoCompletions.add("true");
      autoCompletions.add("false");
    } 
    return autoCompletions;
  }
  
  private List<String> getArenaAutoCompletions(String[] args) {
    List<String> autoCompletions = new ArrayList<>();
    if (args.length == 1) {
      autoCompletions.add("create");
      autoCompletions.add("pos1");
      autoCompletions.add("pos2");
      autoCompletions.add("teleport");
      autoCompletions.add("delete");
      autoCompletions.add("sumo");
      autoCompletions.add("hcf");
      autoCompletions.add("spleef");
      autoCompletions.add("build");
      autoCompletions.add("center");
      autoCompletions.add("list");
      autoCompletions.add("settings");
      autoCompletions.add("opetions");
    } else if (args.length == 2) {
      for (IcePracticeArena arena : ArenaManager.getInstance().getArenas())
        autoCompletions.add(arena.getName()); 
    } else if (args.length == 3 && args[0].equals("sumo")) {
      autoCompletions.add("true");
      autoCompletions.add("false");
    } 
    return autoCompletions;
  }
  
  private List<String> getEventAutoCompletions(String[] args) {
    List<String> autoCompletions = new ArrayList<>();
    if (args.length == 1) {
      autoCompletions.add("host");
      autoCompletions.add("expireCooldown");
      autoCompletions.add("join");
      autoCompletions.add("stop");
    } else if (args.length == 2 && args[0].equalsIgnoreCase("join")) {
      for (Tournament tournament : EventRegistry.getStarting())
        autoCompletions.add(String.valueOf(tournament.getId())); 
    } 
    return autoCompletions;
  }
  
  private List<String> getPartyAutoCompletions(String[] args) {
    List<String> autoCompletions = new ArrayList<>();
    if (args.length == 1) {
      autoCompletions.add("create");
      autoCompletions.add("open");
      autoCompletions.add("close");
      autoCompletions.add("join");
      autoCompletions.add("info");
      autoCompletions.add("disband");
      autoCompletions.add("invite");
      autoCompletions.add("kick");
      autoCompletions.add("accept");
    } else {
      return null;
    } 
    return autoCompletions;
  }
  
  private List<String> getEditorAutoCompletions(String[] args) {
    List<String> autoCompletions = new ArrayList<>();
    if (args.length == 1) {
      autoCompletions.add("setlocation");
      autoCompletions.add("setanvil");
      autoCompletions.add("setsign");
      autoCompletions.add("setchest");
    } 
    return autoCompletions;
  }
  
  private List<String> getIcePracticeAutoCompletions(String[] args) {
    List<String> autoCompletions = new ArrayList<>();
    if (args.length == 1) {
      autoCompletions.add("setspawn");
      autoCompletions.add("reload");
      autoCompletions.add("reset");
      autoCompletions.add("update");
    } else if (args.length == 2 && args[0].equalsIgnoreCase("reset")) {
      autoCompletions.add("arena");
      autoCompletions.add("kits");
      autoCompletions.add("joinitems");
      autoCompletions.add("messages");
      autoCompletions.add("playerdata");
    } 
    return autoCompletions;
  }
}
