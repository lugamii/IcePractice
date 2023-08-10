package de.kontux.icepractice.match;

import de.kontux.icepractice.api.kit.IcePracticeKit;
import java.util.UUID;

public class DuelRequest {
  private final UUID sender;
  
  private final UUID requested;
  
  private final IcePracticeKit kit;
  
  public DuelRequest(UUID sender, UUID requested, IcePracticeKit kit) {
    this.sender = sender;
    this.requested = requested;
    this.kit = kit;
  }
  
  public UUID getSender() {
    return this.sender;
  }
  
  public UUID getRequested() {
    return this.requested;
  }
  
  public IcePracticeKit getKit() {
    return this.kit;
  }
}
