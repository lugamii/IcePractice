package de.kontux.icepractice.userdata;

import de.kontux.icepractice.IcePracticePlugin;
import de.kontux.icepractice.api.kit.IcePracticeKit;
import de.kontux.icepractice.kits.KitManager;
import java.util.HashMap;
import org.bukkit.OfflinePlayer;

public class OfflinePlayerData {
  private static final PlayerDataRepository REPO = new PlayerDataRepository(IcePracticePlugin.getInstance().getRepository());
  
  private final HashMap<IcePracticeKit, Integer> eloValues = new HashMap<>();
  
  private final OfflinePlayer player;
  
  public OfflinePlayerData(OfflinePlayer player) {
    this.player = player;
    for (IcePracticeKit kit : KitManager.getInstance().getKits()) {
      if (kit.isRanked())
        this.eloValues.put(kit, Integer.valueOf(REPO.getElo(player.getUniqueId(), kit))); 
    } 
  }
  
  public int getElo(IcePracticeKit kit) {
    return ((Integer)this.eloValues.getOrDefault(kit, Integer.valueOf(1000))).intValue();
  }
  
  public OfflinePlayer getPlayer() {
    return this.player;
  }
}
