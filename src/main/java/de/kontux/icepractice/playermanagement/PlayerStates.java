package de.kontux.icepractice.playermanagement;

import de.kontux.icepractice.api.playerstates.PlayerState;
import de.kontux.icepractice.api.playerstates.PlayerStateManager;
import java.util.HashMap;
import java.util.UUID;
import org.bukkit.entity.Player;

public class PlayerStates implements PlayerStateManager {
  private static final PlayerStates INSTANCE = new PlayerStates();
  
  private final HashMap<UUID, PlayerState> playerStates = new HashMap<>();
  
  public static PlayerStates getInstance() {
    return INSTANCE;
  }
  
  public void setState(Player player, PlayerState state) {
    if (player == null)
      throw new IllegalArgumentException("Player may not be null!"); 
    this.playerStates.put(player.getUniqueId(), state);
  }
  
  public PlayerState getState(Player player) {
    if (player == null)
      throw new IllegalArgumentException("Player may not be null!"); 
    return this.playerStates.getOrDefault(player.getUniqueId(), PlayerState.IDLE);
  }
}
