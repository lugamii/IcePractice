package de.kontux.icepractice.util;

import de.kontux.icepractice.api.IcePracticeAPI;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;

public class SpigotUtil {
  public static TextComponent buildButton(String text, String command, String hoverText) {
    TextComponent component = new TextComponent(text);
    component.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, command));
    component.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, (new ComponentBuilder(hoverText)).create()));
    return component;
  }
  
  public static int getItemsInInventory(Material type, Inventory inventory) {
    int amount = 0;
    for (ItemStack item : inventory.getContents()) {
      if (item.getType() == type)
        amount += item.getAmount(); 
    } 
    return amount;
  }
  
  public static int getItemsInInventory(Material type, short durability, Inventory inventory) {
    int amount = 0;
    for (ItemStack item : inventory.getContents()) {
      if (item.getType() == type && item.getDurability() == durability)
        amount += item.getAmount(); 
    } 
    return amount;
  }
  
  public static void dropAll(Player player) {
    PlayerInventory inventory = player.getInventory();
    for (int i = 0; i < 40; i++) {
      ItemStack item = inventory.getItem(i);
      if (item != null && item.getType() != Material.AIR) {
        Item drop = player.getWorld().dropItem(player.getLocation(), item);
        for (Player observer : drop.getWorld().getPlayers()) {
          if (IcePracticeAPI.getEntityHider().canSee(observer, (Entity)player))
            IcePracticeAPI.getEntityHider().showEntity(observer, (Entity)drop); 
        } 
      } 
      player.getInventory().clear(i);
    } 
  }
  
  public static void clearInventory(Player player) {
    for (int i = 0; i < 40; i++)
      player.getInventory().clear(i); 
  }
  
  public static void clearPotionEffects(Player player) {
    for (PotionEffect effect : player.getActivePotionEffects())
      player.removePotionEffect(effect.getType()); 
  }
}
