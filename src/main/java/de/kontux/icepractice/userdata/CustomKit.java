package de.kontux.icepractice.userdata;

import de.kontux.icepractice.api.kit.IcePracticeKit;
import de.kontux.icepractice.api.user.CustomUserKit;
import de.kontux.icepractice.configs.repositories.messages.KitMessageRepository;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CustomKit implements CustomUserKit {
  private final int number;
  
  private final IcePracticeKit kit;
  
  private String customName;
  
  private ItemStack[] inventory;
  
  public CustomKit(IcePracticeKit kit, int number, String customName, ItemStack[] inventory) {
    this.kit = kit;
    this.number = number;
    this.customName = customName;
    this.inventory = inventory;
  }
  
  public String getCustomName() {
    return this.customName;
  }
  
  public void equip(Player player) {
    player.sendMessage((new KitMessageRepository()).getGiveMessage(this.customName));
    player.getInventory().clear();
    for (int i = 0; i < 40; i++) {
      ItemStack item = this.inventory[i];
      player.getInventory().setItem(i, item);
    } 
  }
  
  public ItemStack[] getInventory() {
    return this.inventory;
  }
  
  public void setInventory(ItemStack[] inventory) {
    this.inventory = inventory;
  }
  
  public int getNumber() {
    return this.number;
  }
  
  public IcePracticeKit getKit() {
    return this.kit;
  }
  
  public void setCustomName() {
    this.customName = this.customName;
  }
}
