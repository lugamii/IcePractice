package de.kontux.icepractice.commands.arenasubcommands;

import de.kontux.icepractice.arenas.ArenaManager;
import de.kontux.icepractice.configs.Settings;
import dev.lugami.spigot.utils.CC;
import org.bukkit.entity.Player;

public class ArenaCreateCommand implements ArenaCommand {
  private final String arenaName;
  
  private final Player player;
  
  public ArenaCreateCommand(String arenaName, Player player) {
    this.arenaName = arenaName;
    this.player = player;
  }
  
  public void execute() {
    ArenaManager.getInstance().create(this.player, this.arenaName);
    player.sendMessage(Settings.PRIMARY + "Arena " + arenaName + " was created successfully!");
  }
}
