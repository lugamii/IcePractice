package de.kontux.icepractice.configs;

import de.kontux.icepractice.IcePracticePlugin;
import de.kontux.icepractice.configs.files.JoinItemConfig;
import de.kontux.icepractice.util.ConfigUtil;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;

public class Settings {
  public static final boolean USE_COLOURS_FOR_ITEMS = JoinItemConfig.get().getBoolean("use-colours", true);
  
  public static final String SCOREBOARD_TITLE = (ConfigUtil.getColouredString(IcePracticePlugin.getInstance().getConfig(), "config.scoreboards.title") != null) ? ConfigUtil.getColouredString(IcePracticePlugin.getInstance().getConfig(), "config.scoreboards.title") : "ICEPRACTICE";
  
  private static final FileConfiguration CONFIG = IcePracticePlugin.getInstance().getConfig();
  
  public static final ChatColor PRIMARY = ChatColor.valueOf(CONFIG.getString("config.colours.primary", "DARK_RED"));
  
  public static final ChatColor SECONDARY = ChatColor.valueOf(CONFIG.getString("config.colours.secondary", "GOLD"));
  
  public static final boolean USE_COLOURS_FOR_BOARDS = CONFIG.getBoolean("config.scoreboards.use-default-colours", true);
  
  public static final boolean ALLOW_PASSWORD_PROTECTION = CONFIG.getBoolean("config.scoreboards.use-default-colours", true);
  
  public static final Material QUEUE_LEAVE_ITEM = CONFIG.isString("config.queue-leave-item") ? Material.matchMaterial(CONFIG.getString("config.queue-leave-item")) : Material.REDSTONE;
  
  public static final String QUEUE_LEAVE_ITEM_NAME = CONFIG.isString("config.queue-leave-item-name") ? ConfigUtil.getColouredString(CONFIG, "config.queue-leave-item-name") : "§cLeave Queue";
  
  public static final boolean TP_ON_JOIN = CONFIG.getBoolean("config.teleport-on-join", true);
  
  public static final List<String> BLACKLISTED_WORLDS = CONFIG.getStringList("config.blacklisted-worlds");
}
