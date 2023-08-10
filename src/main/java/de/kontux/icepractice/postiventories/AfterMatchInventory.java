package de.kontux.icepractice.postiventories;

import de.kontux.icepractice.api.match.IcePracticeFight;
import de.kontux.icepractice.api.match.misc.FightStatistics;
import de.kontux.icepractice.api.match.misc.MatchInventory;
import de.kontux.icepractice.configs.Settings;
import de.kontux.icepractice.registries.InventoryRegistry;
import de.kontux.icepractice.util.ItemBuilder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;

public class AfterMatchInventory implements MatchInventory {
  private final Player target;
  
  private final Player next;
  
  private final ItemStack[] contents = new ItemStack[45];
  
  private final IcePracticeFight fight;
  
  public AfterMatchInventory(Player target, IcePracticeFight fight) {
    this.target = target;
    this.fight = fight;
    this.next = fight.getNextTotal(target);
  }
  
  public void initializeInventory() {
    FightStatistics statistics = this.fight.getMatchStatistics();
    PlayerInventory inventory = this.target.getInventory();
    ItemStack[] items = inventory.getContents();
    ItemStack[] armor = inventory.getArmorContents();
    Collection<PotionEffect> effects = this.target.getActivePotionEffects();
    int potsLeft = 0;
    int thrownPots = statistics.getThrownPots(this.target.getUniqueId());
    int missedPots = statistics.getMissedPots(this.target.getUniqueId());
    int hits = statistics.getHitCount(this.target.getUniqueId());
    for (ItemStack item : items) {
      if (item != null && item.getType() == Material.POTION && item.getDurability() == 16421)
        potsLeft++; 
    } 
    for (int i = 0; i < armor.length; i++)
      inventory.setItem(36 + i, armor[i]); 
    List<String> effectLore = new ArrayList<>();
    for (PotionEffect effect : effects) {
      if (effect != null) {
        int duration = effect.getDuration() / 20;
        int minutes = duration / 60;
        int seconds = duration % 60;
        String durationText = String.format("%d:%02d", new Object[] { Integer.valueOf(minutes), Integer.valueOf(seconds) });
        String effectName = effect.getType().getName();
        String cap = effectName.substring(0, 1).toUpperCase() + effectName.substring(1);
        effectLore.add(Settings.PRIMARY + cap + " " + (effect.getAmplifier() + 1) + " - " + durationText);
      } 
    } 
    ItemStack healthItem = ItemBuilder.create(Material.SKULL_ITEM, Settings.SECONDARY + "Health " + Settings.PRIMARY + Math.round(this.target.getHealth()) + " ❤", null);
    ItemStack effectItem = ItemBuilder.create(Material.BREWING_STAND_ITEM, Settings.SECONDARY + "Active potion effects:", effectLore);
    List<String> potLore = new ArrayList<>();
    potLore.add(Settings.SECONDARY + "Thrown pots: " + Settings.PRIMARY + thrownPots);
    potLore.add(Settings.SECONDARY + "Missed pots: " + Settings.PRIMARY + missedPots);
    ItemStack potItem = ItemBuilder.create(Material.POTION, Settings.SECONDARY + "Pots left: " + Settings.PRIMARY + potsLeft, (short)16421, potLore);
    List<String> hitLore = new ArrayList<>();
    hitLore.add(Settings.SECONDARY + "Hits: " + Settings.PRIMARY + hits);
    ItemStack hitItem = ItemBuilder.create(Material.DIAMOND_SWORD, Settings.SECONDARY + "Hits:", hitLore);
    System.arraycopy(items, 0, this.contents, 0, 36);
    System.arraycopy(armor, 0, this.contents, 36, 4);
    this.contents[40] = healthItem;
    this.contents[41] = effectItem;
    this.contents[42] = potItem;
    this.contents[43] = hitItem;
    this.contents[44] = ItemBuilder.create(Material.ARROW, Settings.PRIMARY + this.next.getName() + "'s Inventory", null);
    InventoryRegistry.addInventory(this.target, this);
  }
  
  public Player getNext() {
    return this.next;
  }
  
  public Player getTarget() {
    return this.target;
  }
  
  public ItemStack[] getContents() {
    return this.contents;
  }
}
