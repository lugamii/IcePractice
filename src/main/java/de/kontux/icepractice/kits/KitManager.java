package de.kontux.icepractice.kits;

import de.kontux.icepractice.IcePracticePlugin;
import de.kontux.icepractice.api.kit.IcePracticeKit;
import de.kontux.icepractice.api.kit.KitHandler;
import de.kontux.icepractice.configs.files.KitConfig;
import de.kontux.icepractice.configs.repositories.messages.KitMessageRepository;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public class KitManager implements KitHandler {
  private static final KitManager INSTANCE = new KitManager();
  
  private final List<IcePracticeKit> kits = new ArrayList<>();
  
  private final KitMessageRepository messages = new KitMessageRepository();
  
  private KitManager() {
    loadFromConfig();
  }
  
  public static KitManager getInstance() {
    return INSTANCE;
  }
  
  public void loadFromConfig() {
    IcePracticePlugin.getInstance().getLogger().info("Loading all kits from config...");
    ConfigurationSection section = KitConfig.get().getConfigurationSection("Kits.");
    if (section == null)
      section = KitConfig.get().createSection("Kits."); 
    for (String key : section.getKeys(false)) {
      Kit kit = new Kit(key);
      this.kits.add(kit);
    } 
  }
  
  public void createKit(Player player, String name) {
    if (getKit(name) == null) {
      Kit kit = new Kit(name);
      kit.setInventory(player.getInventory().getContents(), player.getInventory().getArmorContents());
      player.sendMessage(this.messages.getKitCreateMessage(name));
      this.kits.add(kit);
    } else {
      player.sendMessage("§cThis kit already exists.");
    } 
  }
  
  public void setKitInventory(Player player, String name) {
    IcePracticeKit kit = getKit(name);
    if (kit != null) {
      kit.setInventory(player.getInventory().getContents(), player.getInventory().getArmorContents());
      player.sendMessage(this.messages.getKitSetInvMessage(name));
    } else {
      player.sendMessage(this.messages.getNotExistMessage());
    } 
  }
  
  public void equipKit(Player player, IcePracticeKit kit) {
    if (kit != null) {
      player.sendMessage(this.messages.getGiveMessage(kit.getName()));
      kit.equipKit(player);
    } else {
      player.sendMessage(this.messages.getNotExistMessage());
    } 
  }
  
  public void viewKit(Player player, String name) {
    IcePracticeKit kit = getKit(name);
    if (kit != null) {
      player.sendMessage(this.messages.getGiveMessage(kit.getName()));
      kit.equipKit(player);
    } else {
      player.sendMessage(this.messages.getNotExistMessage());
    } 
  }
  
  public void addCooldown(Player player, String name, int cooldown) {
    IcePracticeKit kit = getKit(name);
    if (kit != null) {
      kit.setPearlCooldown(cooldown);
      player.sendMessage(this.messages.getCooldownMessage(String.valueOf(cooldown)));
    } else {
      player.sendMessage(this.messages.getNotExistMessage());
    } 
  }
  
  public void setIcon(Player player, String name) {
    IcePracticeKit kit = getKit(name);
    if (kit != null) {
      kit.setIcon(player.getItemInHand());
      player.sendMessage(this.messages.getIconChangeMessage(name, player.getItemInHand().getType()));
    } else {
      player.sendMessage(this.messages.getNotExistMessage());
    } 
  }
  
  public void deleteKit(Player player, String name) {
    IcePracticeKit kit = getKit(name);
    if (kit != null) {
      kit.delete();
      this.kits.remove(kit);
      player.sendMessage(this.messages.getKitDeleteMessage(name));
    } else {
      player.sendMessage(this.messages.getNotExistMessage());
    } 
  }
  
  public void setRanked(Player player, String name, boolean ranked) {
    IcePracticeKit kit = getKit(name);
    if (kit != null) {
      kit.setRanked(ranked);
      if (ranked) {
        player.sendMessage(this.messages.getRankedTrueMessage(name));
      } else {
        player.sendMessage(this.messages.getRankedFalseMessage(name));
      } 
    } else {
      player.sendMessage(this.messages.getNotExistMessage());
    } 
  }
  
  public void setCombo(Player player, String name, boolean combo) {
    IcePracticeKit kit = getKit(name);
    if (kit != null) {
      kit.setCombo(combo);
      if (combo) {
        player.sendMessage(this.messages.getComboTrueMessage(name));
      } else {
        player.sendMessage(this.messages.getComboFalseMessage(name));
      } 
    } else {
      player.sendMessage(this.messages.getNotExistMessage());
    } 
  }
  
  public void setSumo(Player player, String name, boolean sumo) {
    IcePracticeKit kit = getKit(name);
    if (kit != null) {
      kit.setSumo(sumo);
      if (sumo) {
        player.sendMessage(this.messages.getSumoTrueMessage(name));
      } else {
        player.sendMessage(this.messages.getSumoFalseMessage(name));
      } 
    } else {
      player.sendMessage(this.messages.getNotExistMessage());
    } 
  }
  
  public boolean isKit(String name) {
    return (getKit(name) != null);
  }
  
  public IcePracticeKit getKit(String name) {
    for (IcePracticeKit current : this.kits) {
      if (current.getName().equals(name))
        return current; 
    } 
    return null;
  }
  
  public List<IcePracticeKit> getKits() {
    return this.kits;
  }
  
  public void reload() {
    this.kits.clear();
    loadFromConfig();
  }
  
  public Kit getSumoEventKit() {
    Kit kit = new Kit("Sumo");
    kit.setSumo(true);
    kit.setInventory(new org.bukkit.inventory.ItemStack[36], new org.bukkit.inventory.ItemStack[4]);
    return kit;
  }
}
