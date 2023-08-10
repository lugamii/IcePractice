package de.kontux.icepractice.userdata;

import java.util.HashMap;
import java.util.UUID;
import org.bukkit.entity.Player;

public class PlayerDataManager {
  private static final HashMap<UUID, PlayerData> USERS = new HashMap<>();
  
  public static void addPlayer(Player player) {
    USERS.put(player.getUniqueId(), new PlayerData(player));
  }
  
  public static void removePlayer(Player player) {
    USERS.remove(player.getUniqueId());
  }
  
  public static PlayerData getSettingsData(UUID uuid) {
    return USERS.get(uuid);
  }
}
