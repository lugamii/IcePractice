package de.kontux.icepractice.commands.eventsubcommands;

import de.kontux.icepractice.configs.repositories.messages.EventMessageRepository;
import de.kontux.icepractice.guis.EventHostInventory;
import org.bukkit.entity.Player;

public class EventHostCommand implements EventSubcommand {
  private final Player player;
  
  public EventHostCommand(Player player) {
    this.player = player;
  }
  
  public void execute() {
    if (this.player.hasPermission("icepractice.host")) {
      (new EventHostInventory(this.player)).openMenu();
    } else {
      this.player.sendMessage((new EventMessageRepository()).getNoPermMessage());
    } 
  }
}
