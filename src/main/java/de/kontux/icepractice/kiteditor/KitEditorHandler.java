package de.kontux.icepractice.kiteditor;

import de.kontux.icepractice.api.kit.IcePracticeKit;
import de.kontux.icepractice.api.playerstates.PlayerState;
import de.kontux.icepractice.configs.repositories.messages.KitEditorMessageRepository;
import de.kontux.icepractice.guis.editormenus.KitEditInventory;
import de.kontux.icepractice.guis.editormenus.KitSaveInventory;
import de.kontux.icepractice.kits.KitManager;
import de.kontux.icepractice.locations.SpawnPointHandler;
import de.kontux.icepractice.playermanagement.PlayerStates;
import de.kontux.icepractice.protocol.EntityHider;
import java.util.HashMap;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class KitEditorHandler {
  public static final HashMap<UUID, KitEditorHandler> HANDLERS = new HashMap<>();
  
  private static final Location LOCATION = (new KitEditorRepository()).getLocation();
  
  private final Player player;
  
  private final IcePracticeKit kit;
  
  public int currentlyEditing = -1;
  
  public KitEditorHandler(Player player, IcePracticeKit kit) {
    this.kit = kit;
    this.player = player;
  }
  
  public void teleportPlayer() {
    if (LOCATION != null) {
      HANDLERS.put(this.player.getUniqueId(), this);
      PlayerStates.getInstance().setState(this.player, PlayerState.EDITOR);
      for (Player current : Bukkit.getOnlinePlayers()) {
        EntityHider.getInstance().hideEntity(this.player, (Entity)current);
        EntityHider.getInstance().hideEntity(current, (Entity)this.player);
      } 
      PlayerStates.getInstance().setState(this.player, PlayerState.EDITOR);
      this.player.teleport(LOCATION);
      KitManager.getInstance().equipKit(this.player, this.kit);
    } else {
      this.player.sendMessage((new KitEditorMessageRepository()).getNotSetupMessage());
    } 
  }
  
  public void openSaveMenu() {
    (new KitSaveInventory(this.player, this.kit)).openMenu();
  }
  
  public void openEditMenu() {
    if (this.kit.allowChestEditing())
      (new KitEditInventory(this.player, this.kit)).openMenu(); 
  }
  
  public void removePlayer() {
    HANDLERS.remove(this.player.getUniqueId());
    SpawnPointHandler.teleportToSpawn(this.player);
  }
  
  public Player getPlayer() {
    return this.player;
  }
  
  public IcePracticeKit getKit() {
    return this.kit;
  }
}
