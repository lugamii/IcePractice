package de.kontux.icepractice.configs.messages;

import de.kontux.icepractice.configs.Config;
import org.bukkit.ChatColor;

public abstract class MessageRepository {
  protected final String getOrDefault(String path, String defaultMessage) {
    return ChatColor.translateAlternateColorCodes('&', Config.MESSAGES.getConfig().getString(path, defaultMessage));
  }
}
