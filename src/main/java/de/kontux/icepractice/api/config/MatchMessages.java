package de.kontux.icepractice.api.config;

import org.bukkit.entity.Player;

public interface MatchMessages {
  String getCooldownMessage(int paramInt);
  
  String getFFAStartingMessage(String paramString);
  
  String getStartMessage();
  
  String getStartingMessage(Player paramPlayer, String paramString);
  
  String getTeamStartingMessage(Player paramPlayer, String paramString);
  
  String getEndMessage();
  
  String getNoArenaMessage();
  
  String getComboMessage();
  
  String getNowSpectatingMessage(Player paramPlayer);
  
  String getNoLongerSpectatingMessage(Player paramPlayer);
  
  String getLeftSpectatingMessage();
  
  String getSoloSpectatorMessage(Player paramPlayer1, Player paramPlayer2, String paramString);
  
  String getTeamSpectatorMessage(Player paramPlayer1, Player paramPlayer2, String paramString);
  
  String getFFASpectatorMessage(String paramString);
  
  String getTeamFightDeathMessage(Player paramPlayer1, Player paramPlayer2);
  
  String getTextWithColour(String paramString);
}
