package de.kontux.icepractice.guis;

import de.kontux.icepractice.api.gui.InventoryGui;
import de.kontux.icepractice.api.kit.IcePracticeKit;
import de.kontux.icepractice.configs.Settings;
import de.kontux.icepractice.database.SQLRepository;
import de.kontux.icepractice.kits.KitManager;
import de.kontux.icepractice.userdata.PlayerDataRepository;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class StatsInventory extends InventoryGui {
  private final SQLRepository repository;
  
  public StatsInventory(Player player, SQLRepository repository) {
    super(player, Settings.PRIMARY + "Your stats:", 36);
    this.repository = repository;
  }
  
  protected void setItems() {
    PlayerDataRepository repo = new PlayerDataRepository(this.repository);
    for (IcePracticeKit kit : KitManager.getInstance().getKits()) {
      if (kit.isRanked()) {
        ItemStack item = kit.getIcon();
        ItemMeta meta = item.getItemMeta();
        List<String> lore = new ArrayList<>();
        lore.add(Settings.SECONDARY + "Elo: " + Settings.PRIMARY + repo.getElo(this.player.getUniqueId(), kit));
        meta.setLore(lore);
        item.setItemMeta(meta);
        this.inventory.addItem(new ItemStack[] { item });
      } 
    } 
  }
  
  public void runAction(ItemStack item) {}
}
