package de.kontux.icepractice.tournaments.fights;

import de.kontux.icepractice.IcePracticePlugin;
import de.kontux.icepractice.configs.repositories.messages.MatchMessageRepository;
import de.kontux.icepractice.match.Fight;
import de.kontux.icepractice.tournaments.Tournament;
import org.bukkit.Bukkit;
import org.bukkit.Instrument;
import org.bukkit.Note;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class EventMatchCooldown {
  private final Tournament tournament;
  
  private final MatchMessageRepository messages = new MatchMessageRepository();
  
  private int taskId;
  
  public EventMatchCooldown(Tournament tournament) {
    this.tournament = tournament;
  }
  
  public void runCooldown() {
    this.taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask((Plugin)IcePracticePlugin.getInstance(), new Runnable() {
          int countdown = 5;
          
          public void run() {
            if (this.countdown <= 0) {
              for (Player current : EventMatchCooldown.this.tournament.getParticipants()) {
                current.sendMessage(EventMatchCooldown.this.messages.getStartMessage());
                current.playNote(current.getLocation(), Instrument.PIANO, Note.natural(1, Note.Tone.F));
              } 
              for (Fight fight : EventMatchCooldown.this.tournament.getOnGoingFights())
                fight.expireCooldown(); 
              Bukkit.getScheduler().cancelTask(EventMatchCooldown.this.taskId);
              return;
            } 
            for (Player current : EventMatchCooldown.this.tournament.getParticipants()) {
              current.sendMessage(EventMatchCooldown.this.messages.getCooldownMessage(this.countdown));
              current.playNote(current.getLocation(), Instrument.PIANO, Note.natural(1, Note.Tone.F));
            } 
            this.countdown--;
          }
        },0L, 20L);
  }
}
