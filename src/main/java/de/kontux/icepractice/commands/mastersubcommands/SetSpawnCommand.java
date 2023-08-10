package de.kontux.icepractice.commands.mastersubcommands;

import de.kontux.icepractice.configs.Settings;
import de.kontux.icepractice.locations.SpawnPointHandler;
import org.bukkit.entity.Player;

public class SetSpawnCommand implements MasterSubCommand {
  private final Player player;
  
  public SetSpawnCommand(Player player) {
    this.player = player;
  }
  
  public void execute() {
    SpawnPointHandler.setSpawn(this.player.getLocation());
    this.player.sendMessage(Settings.PRIMARY + "You changed the spawnpoint to your current position.");
  }
}
