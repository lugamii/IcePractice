package de.kontux.icepractice.userdata;

import de.kontux.icepractice.api.kit.IcePracticeKit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

public class Leaderboards {
  public Collection<OfflinePlayerData> calculateLeaderboards(IcePracticeKit kit, int range) {
    List<OfflinePlayerData> allPlayers = new ArrayList<>();
    for (OfflinePlayer player : Bukkit.getOfflinePlayers())
      allPlayers.add(new OfflinePlayerData(player)); 
    allPlayers.sort(Comparator.comparing(offlinePlayerData -> Integer.valueOf(offlinePlayerData.getElo(kit))));
    Collections.reverse(allPlayers);
    int size = allPlayers.size();
    return (size >= range) ? allPlayers.subList(0, range) : allPlayers.subList(0, size);
  }
}
