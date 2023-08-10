package de.kontux.icepractice.match;

import de.kontux.icepractice.api.match.Spectatable;
import de.kontux.icepractice.api.playerstates.PlayerState;
import de.kontux.icepractice.playermanagement.PlayerStates;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.entity.Player;

public abstract class SpectatableMatch implements Spectatable {
  protected final List<Player> spectators = new ArrayList<>();
  
  protected final void handleSpectator(Player player) {
    this.spectators.add(player);
    PlayerStates.getInstance().setState(player, PlayerState.SPECTATING);
    for (int i = 0; i < 40; i++)
      player.getInventory().clear(i); 
    player.getInventory().setItem(8, LEAVE_ITEM);
    player.setAllowFlight(true);
    player.setFlying(true);
  }
  
  public final List<Player> getSpectators() {
    return this.spectators;
  }
}
