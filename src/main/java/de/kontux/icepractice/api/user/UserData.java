package de.kontux.icepractice.api.user;

import de.kontux.icepractice.api.kit.IcePracticeKit;
import java.util.List;

public interface UserData {
  void saveCustomKit(IcePracticeKit paramIcePracticeKit, int paramInt);
  
  void deleteCustomKit(IcePracticeKit paramIcePracticeKit, int paramInt);
  
  List<CustomUserKit> getCustomKits(IcePracticeKit paramIcePracticeKit);
  
  void setCustomKitName(IcePracticeKit paramIcePracticeKit, String paramString, int paramInt);
  
  CustomUserKit getCustomKit(IcePracticeKit paramIcePracticeKit, int paramInt);
  
  WorldTime getWorldTime();
  
  void setWorldTime(WorldTime paramWorldTime);
  
  boolean isSendRequests();
  
  void setSendRequests(boolean paramBoolean);
  
  boolean isShowBoard();
  
  void setShowBoard(boolean paramBoolean);
  
  boolean isShowPlayers();
  
  void setShowPlayers(boolean paramBoolean);
}
