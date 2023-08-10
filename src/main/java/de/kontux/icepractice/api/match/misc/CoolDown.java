package de.kontux.icepractice.api.match.misc;

import de.kontux.icepractice.api.IcePracticeAPI;
import de.kontux.icepractice.api.match.IcePracticeFight;
import org.bukkit.Bukkit;
import org.bukkit.Instrument;
import org.bukkit.Note;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class CoolDown {
  private final Plugin plugin;
  
  private final IcePracticeFight fight;
  
  private int taskId;
  
  private int countdown = 5;
  
  public CoolDown(Plugin plugin, IcePracticeFight fight) {
    this.plugin = plugin;
    this.fight = fight;
  }
  
  public void runCooldown() {
    this.taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(this.plugin, () -> {
          if (this.countdown <= 0) {
            for (Player current : this.fight.getPlayers())
              current.playNote(current.getLocation(), Instrument.PIANO, Note.natural(1, Note.Tone.F)); 
            this.fight.expireCooldown();
            Bukkit.getScheduler().cancelTask(this.taskId);
            return;
          } 
          for (Player player : this.fight.getPlayers()) {
            player.sendMessage(IcePracticeAPI.getMatchMessages().getCooldownMessage(this.countdown));
            player.playNote(player.getLocation(), Instrument.STICKS, Note.sharp(0, Note.Tone.C));
          } 
          for (Player player : this.fight.getSpectators()) {
            player.sendMessage(IcePracticeAPI.getMatchMessages().getCooldownMessage(this.countdown));
            player.playNote(player.getLocation(), Instrument.STICKS, Note.sharp(0, Note.Tone.C));
          } 
          this.countdown--;
        },0L, 20L);
  }
}
