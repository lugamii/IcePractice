package de.kontux.icepractice.api.playerstates;

import org.bukkit.entity.Player;

public interface PlayerStateManager {
  PlayerState getState(Player paramPlayer);
  
  void setState(Player paramPlayer, PlayerState paramPlayerState);
}
