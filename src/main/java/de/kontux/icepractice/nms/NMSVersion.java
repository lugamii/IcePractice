package de.kontux.icepractice.nms;

import de.kontux.icepractice.IcePracticePlugin;
import org.bukkit.Bukkit;

public enum NMSVersion {
  v1_7, v1_8, v1_12, v1_13, v1_14, v1_15;
  
  public static boolean determineVersion() {
    String versionName = Bukkit.getBukkitVersion();
    if (versionName.contains("1.7")) {
      IcePracticePlugin.nmsVersion = v1_7;
    } else if (versionName.contains("1.8")) {
      IcePracticePlugin.nmsVersion = v1_8;
    } else if (versionName.contains("1.12")) {
      IcePracticePlugin.nmsVersion = v1_12;
    } else if (versionName.contains("1.13")) {
      IcePracticePlugin.nmsVersion = v1_13;
    } else if (versionName.contains("1.14")) {
      IcePracticePlugin.nmsVersion = v1_14;
    } else if (versionName.contains("1.15")) {
      IcePracticePlugin.nmsVersion = v1_15;
    } else {
      return false;
    } 
    return true;
  }
}
