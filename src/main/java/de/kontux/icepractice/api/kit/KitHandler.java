package de.kontux.icepractice.api.kit;

import java.util.List;
import org.bukkit.entity.Player;

public interface KitHandler {
  void createKit(Player paramPlayer, String paramString);
  
  void setKitInventory(Player paramPlayer, String paramString);
  
  void equipKit(Player paramPlayer, IcePracticeKit paramIcePracticeKit);
  
  void viewKit(Player paramPlayer, String paramString);
  
  void addCooldown(Player paramPlayer, String paramString, int paramInt);
  
  void setIcon(Player paramPlayer, String paramString);
  
  void deleteKit(Player paramPlayer, String paramString);
  
  void setSumo(Player paramPlayer, String paramString, boolean paramBoolean);
  
  boolean isKit(String paramString);
  
  IcePracticeKit getKit(String paramString);
  
  List<IcePracticeKit> getKits();
  
  void reload();
  
  IcePracticeKit getSumoEventKit();
}
