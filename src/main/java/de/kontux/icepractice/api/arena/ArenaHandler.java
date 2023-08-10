package de.kontux.icepractice.api.arena;

import de.kontux.icepractice.api.kit.IcePracticeKit;
import java.util.List;
import org.bukkit.entity.Player;

public interface ArenaHandler {
  void create(Player paramPlayer, String paramString);
  
  void delete(Player paramPlayer, String paramString);
  
  IcePracticeArena getArena(String paramString);
  
  IcePracticeArena getRandomFreeArena(IcePracticeKit paramIcePracticeKit);
  
  List<IcePracticeArena> getArenas();
  
  void reload();
}
