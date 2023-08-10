package de.kontux.icepractice.api.locations;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public interface IcePracticeSpawnpoint {
  void setSpawn(Location paramLocation);
  
  void teleportToSpawn(Player paramPlayer);
  
  Location getSpawn();
}
