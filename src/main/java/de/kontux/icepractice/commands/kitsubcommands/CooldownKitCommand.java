package de.kontux.icepractice.commands.kitsubcommands;

import de.kontux.icepractice.kits.KitManager;
import org.bukkit.entity.Player;

public class CooldownKitCommand implements KitCommand {
  private final Player player;
  
  private final String kit;
  
  private final int cooldown;
  
  public CooldownKitCommand(Player player, int cooldown, String kit) {
    this.player = player;
    this.cooldown = cooldown;
    this.kit = kit;
  }
  
  public void execute() {
    KitManager.getInstance().addCooldown(this.player, this.kit, this.cooldown);
  }
}
