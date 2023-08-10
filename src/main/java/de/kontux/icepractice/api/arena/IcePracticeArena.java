package de.kontux.icepractice.api.arena;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public interface IcePracticeArena {
  void create(Player paramPlayer);
  
  boolean loadFromConfig();
  
  void saveToConfig();
  
  void delete();
  
  String getName();
  
  Location getPos1();
  
  void setPos1(Location paramLocation);
  
  Location getPos2();
  
  void setPos2(Location paramLocation);
  
  Location getCenter();
  
  void setCenter(Location paramLocation);
  
  boolean isSumo();
  
  void setSumo(boolean paramBoolean);
  
  void setSpleef(boolean paramBoolean);
  
  void setHcf(boolean paramBoolean);
  
  boolean isSpleef();
  
  boolean isHcf();
  
  boolean isBuild();
  
  void setBuild(boolean paramBoolean);
  
  boolean isInUse();
  
  void setInUse(boolean paramBoolean);
}
