package de.kontux.icepractice.match.misc;

import de.kontux.icepractice.IcePracticePlugin;
import de.kontux.icepractice.api.match.IcePracticeFight;
import de.kontux.icepractice.configs.repositories.messages.MatchMessageRepository;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.Instrument;
import org.bukkit.Note;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class CoolDown {
  private final List<Player> players;
  
  private final IcePracticeFight fight;
  
  private int taskId;
  
  public CoolDown(IcePracticeFight fight) {
    this.fight = fight;
    this.players = fight.getPlayers();
  }
  
  public void runCooldown() {
    final MatchMessageRepository messages = new MatchMessageRepository();
    this.taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask((Plugin)IcePracticePlugin.getInstance(), new Runnable() {
          int countdown = 5;
          
          public void run() {
            if (this.countdown <= 0) {
              for (Player current : CoolDown.this.players) {
                current.sendMessage(messages.getStartMessage());
                current.playNote(current.getLocation(), Instrument.PIANO, Note.natural(1, Note.Tone.F));
              } 
              CoolDown.this.fight.expireCooldown();
              Bukkit.getScheduler().cancelTask(CoolDown.this.taskId);
              return;
            } 
            for (Player current : CoolDown.this.players) {
              current.sendMessage(messages.getCooldownMessage(this.countdown));
              current.playNote(current.getLocation(), Instrument.STICKS, Note.sharp(0, Note.Tone.C));
            } 
            this.countdown--;
          }
        },0L, 20L);
  }
}
