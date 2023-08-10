package de.kontux.icepractice.locations;

import de.kontux.icepractice.api.locations.IcePracticeSpawnpoint;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class SpawnPointAdapter implements IcePracticeSpawnpoint {
  public void teleportToSpawn(Player player) {
    SpawnPointHandler.teleportToSpawn(player);
  }
  
  public Location getSpawn() {
    return SpawnPointHandler.getSpawn();
  }
  
  public void setSpawn(Location location) {
    SpawnPointHandler.setSpawn(location);
  }
}
