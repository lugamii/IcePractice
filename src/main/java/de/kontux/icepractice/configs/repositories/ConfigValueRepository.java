package de.kontux.icepractice.configs.repositories;

import de.kontux.icepractice.IcePracticePlugin;
import org.bukkit.configuration.file.FileConfiguration;

public class ConfigValueRepository {
  FileConfiguration config = IcePracticePlugin.getInstance().getConfig();
  
  public boolean getAllowEventPassword() {
    return this.config.getBoolean("config.events.allow-password-protection");
  }
  
  public boolean getUseChatFormatting() {
    return this.config.getBoolean("config.use-chat-formatting");
  }
}
