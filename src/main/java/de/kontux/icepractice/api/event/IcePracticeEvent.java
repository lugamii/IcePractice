package de.kontux.icepractice.api.event;

import java.util.Objects;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;

public abstract class IcePracticeEvent extends Event implements Cancellable {
  private final String name;
  
  private boolean cancel = false;
  
  public IcePracticeEvent(String name) {
    this.name = Objects.<String>requireNonNull(name);
  }
  
  public final void setCancelled(boolean cancel) {
    this.cancel = cancel;
  }
  
  public final boolean isCancelled() {
    return this.cancel;
  }
  
  public final String getEventName() {
    return this.name;
  }
}
