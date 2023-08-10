package de.kontux.icepractice.guis;

import de.kontux.icepractice.api.gui.InventoryGui;
import de.kontux.icepractice.api.kit.IcePracticeKit;
import de.kontux.icepractice.configs.Settings;
import de.kontux.icepractice.kits.KitManager;
import de.kontux.icepractice.queue.Queue;
import de.kontux.icepractice.registries.FightRegistry;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class UnrankedQueueInventory extends InventoryGui {
  public UnrankedQueueInventory(Player player) {
    super(player, Settings.PRIMARY + "Unranked Queues:", 18);
  }
  
  protected void setItems() {
    for (IcePracticeKit kit : KitManager.getInstance().getKits()) {
      ItemStack item = kit.getIcon();
      ItemMeta meta = item.getItemMeta();
      meta.setDisplayName(Settings.PRIMARY + kit.getName());
      int inMatch = FightRegistry.getInstance().getPlayersPlaying(kit, false);
      int inQueue = Queue.getPlayersInQueue(kit, false);
      List<String> lore = new ArrayList<>();
      lore.add(Settings.PRIMARY + "Playing: " + Settings.SECONDARY + inMatch);
      lore.add(Settings.PRIMARY + "In queue: " + Settings.SECONDARY + inQueue);
      meta.setLore(lore);
      item.setItemMeta(meta);
      int amount = inMatch + inQueue;
      amount = (amount > 0) ? Math.min(amount, 64) : 1;
      item.setAmount(amount);
      this.inventory.addItem(new ItemStack[] { item });
    } 
  }
  
  public void runAction(ItemStack item) {
    String name = item.getItemMeta().getDisplayName();
    String kitName = name.replace(Settings.PRIMARY.toString(), "");
    if (KitManager.getInstance().isKit(kitName)) {
      Queue.addToQueue(this.player, KitManager.getInstance().getKit(kitName), false);
      this.player.closeInventory();
    } 
  }
}
