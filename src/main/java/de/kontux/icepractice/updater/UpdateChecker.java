package de.kontux.icepractice.updater;

import de.kontux.icepractice.IcePracticePlugin;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;
import java.util.function.Consumer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;

public class UpdateChecker {
  private final IcePracticePlugin plugin;
  
  private final int resourceId;
  
  public UpdateChecker(IcePracticePlugin plugin, int resourceId) {
    this.plugin = plugin;
    this.resourceId = resourceId;
  }
  
  public void check() {
    getVersion(version -> {
          String currentVersion = this.plugin.getDescription().getVersion();
          this.plugin.log(ChatColor.RED + "Your version: " + currentVersion);
          this.plugin.log(ChatColor.RED + "Version found on SpigotMC.org: " + version);
          int idx1 = currentVersion.lastIndexOf(".");
          if (idx1 >= 0)
            currentVersion = currentVersion.substring(0, idx1) + currentVersion.substring(idx1 + 1); 
          int idx2 = version.lastIndexOf(".");
          if (idx2 >= 0)
            version = version.substring(0, idx2) + version.substring(idx2 + 1); 
          float current = Float.parseFloat(currentVersion);
          float latest = Float.parseFloat(version);
          if (current >= latest) {
            this.plugin.log(ChatColor.GREEN + "Your plugin seems to be up to date.");
          } else {
            this.plugin.log(ChatColor.RED + "There is a new version available. Please visit https://www.spigotmc.org/resources/icepractice-queues-sumo-combo-sumo-events-kit-editor-and-more.75338/ to expireCooldown the plugin.");
          } 
        });
  }
  
  public void getVersion(Consumer<String> consumer) {
    Bukkit.getScheduler().runTaskAsynchronously((Plugin)this.plugin, () -> {
          try (InputStream inputStream = (new URL("https://api.spigotmc.org/legacy/update.php?resource=" + this.resourceId)).openStream()) {
            Scanner scanner = new Scanner(inputStream);
            if (scanner.hasNext())
              consumer.accept(scanner.next()); 
          } catch (IOException exception) {
            this.plugin.log(ChatColor.RED + "Cannot connect to spigotmc.org, please check your internet connection.");
          } 
        });
  }
}
