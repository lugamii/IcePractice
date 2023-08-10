package de.kontux.icepractice.userdata;

import de.kontux.icepractice.IcePracticePlugin;
import de.kontux.icepractice.api.kit.IcePracticeKit;
import de.kontux.icepractice.api.user.CustomUserKit;
import de.kontux.icepractice.api.user.UserData;
import de.kontux.icepractice.api.user.WorldTime;
import de.kontux.icepractice.configs.Settings;
import de.kontux.icepractice.configs.repositories.messages.KitEditorMessageRepository;
import de.kontux.icepractice.kits.KitManager;
import de.kontux.icepractice.protocol.EntityHider;
import de.kontux.icepractice.scoreboard.ScoreboardManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scoreboard.DisplaySlot;

public class PlayerData implements UserData {
  private static final PlayerDataRepository REPO = new PlayerDataRepository(IcePracticePlugin.getInstance().getRepository());
  
  private final Player player;
  
  private final UUID uuid;
  
  private final HashMap<IcePracticeKit, List<CustomUserKit>> customKits = new HashMap<>();
  
  private boolean sendRequests;
  
  private boolean showBoard;
  
  private boolean showPlayers;
  
  private WorldTime worldTime;
  
  public PlayerData(Player player) {
    this.player = player;
    this.uuid = this.player.getUniqueId();
    this.showBoard = REPO.useScoreboard(this.uuid);
    this.showPlayers = REPO.showPlayers(this.uuid);
    this.worldTime = REPO.getWorldTime(this.uuid);
    this.sendRequests = REPO.sendRequests(this.uuid);
    for (IcePracticeKit kit : KitManager.getInstance().getKits())
      loadCustomKits(kit); 
  }
  
  private void loadCustomKits(IcePracticeKit kit) {
    List<CustomUserKit> currentCustomKits = REPO.getAllCustomKits(this.uuid, kit);
    this.customKits.put(kit, currentCustomKits);
  }
  
  public void saveCustomKit(IcePracticeKit kit, int number) {
    if (number < 1 || number > 9)
      throw new IllegalArgumentException("The kit number may not be less than 1 or greater than 9! Given number was " + number); 
    List<ItemStack> items = new ArrayList<>(Arrays.asList(this.player.getInventory().getContents()));
    items.addAll(Arrays.asList(this.player.getInventory().getArmorContents()));
    REPO.saveCustomKit(this.uuid, kit, number, items);
    this.player.sendMessage((new KitEditorMessageRepository()).getSavedKitMessage(kit.getName() + "# " + number));
    this.customKits.replace(kit, REPO.getAllCustomKits(this.uuid, kit));
  }
  
  public void deleteCustomKit(IcePracticeKit kit, int number) {
    REPO.deleteCustomKit(this.uuid, kit, number);
    this.player.sendMessage((new KitEditorMessageRepository()).getDeleteMessage(kit.getName()));
    loadCustomKits(kit);
  }
  
  public List<CustomUserKit> getCustomKits(IcePracticeKit kit) {
    return this.customKits.getOrDefault(kit, new ArrayList<>());
  }
  
  public void setCustomKitName(IcePracticeKit kit, String customName, int number) {
    REPO.setCustomName(this.uuid, kit, number, customName);
    loadCustomKits(kit);
    this.player.sendMessage(Settings.PRIMARY + "You renamed the kit to " + Settings.SECONDARY + customName);
  }
  
  public CustomUserKit getCustomKit(IcePracticeKit kit, int number) {
    for (CustomUserKit cK : this.customKits.get(kit)) {
      if (cK.getNumber() == number)
        return cK; 
    } 
    return null;
  }
  
  public WorldTime getWorldTime() {
    return this.worldTime;
  }
  
  public void setWorldTime(WorldTime worldTime) {
    this.worldTime = worldTime;
    this.player.setPlayerTime(worldTime.getTimeCode(), false);
    REPO.setWorldTime(this.uuid, worldTime);
  }
  
  public boolean isSendRequests() {
    return this.sendRequests;
  }
  
  public void setSendRequests(boolean sendRequests) {
    this.sendRequests = sendRequests;
    if (sendRequests) {
      this.player.sendMessage(Settings.PRIMARY + "Players can now send you duel requests.");
    } else {
      this.player.sendMessage(Settings.PRIMARY + "Players can no longer send you duel requests.");
    } 
    REPO.setDuelRequests(this.uuid, sendRequests);
  }
  
  public boolean isShowBoard() {
    return this.showBoard;
  }
  
  public void setShowBoard(boolean showBoard) {
    this.showBoard = showBoard;
    if (showBoard) {
      this.player.sendMessage(Settings.PRIMARY + "Enabled scoreboard.");
      ScoreboardManager.getInstance().setIdleBoard(this.player);
    } else {
      this.player.sendMessage(Settings.PRIMARY + "Disabled scoreboard.");
      this.player.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
    } 
    REPO.setUseScoreboard(this.uuid, showBoard);
  }
  
  public boolean isShowPlayers() {
    return this.showPlayers;
  }
  
  public void setShowPlayers(boolean showPlayers) {
    this.showPlayers = showPlayers;
    if (showPlayers) {
      this.player.sendMessage(Settings.PRIMARY + "Enabled spawn players.");
    } else {
      this.player.sendMessage(Settings.PRIMARY + "Disabled spawn players.");
    } 
    for (Player current : this.player.getWorld().getPlayers()) {
      if (showPlayers) {
        EntityHider.getInstance().showEntity(this.player, (Entity)current);
        continue;
      } 
      EntityHider.getInstance().hideEntity(this.player, (Entity)current);
    } 
    REPO.setShowPlayers(this.uuid, showPlayers);
  }
  
  public int getElo(IcePracticeKit kit) {
    return REPO.getElo(this.uuid, kit);
  }
  
  public void setElo(IcePracticeKit kit, int elo) {
    REPO.setElo(this.uuid, elo, kit);
  }
}
