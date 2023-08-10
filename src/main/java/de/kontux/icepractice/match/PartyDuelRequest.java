package de.kontux.icepractice.match;

import de.kontux.icepractice.api.kit.IcePracticeKit;
import de.kontux.icepractice.party.Party;

public class PartyDuelRequest {
  private final Party sender;
  
  private final Party requested;
  
  private final IcePracticeKit kit;
  
  public PartyDuelRequest(Party sender, Party requested, IcePracticeKit kit) {
    this.sender = sender;
    this.requested = requested;
    this.kit = kit;
  }
  
  public Party getSender() {
    return this.sender;
  }
  
  public Party getRequested() {
    return this.requested;
  }
  
  public IcePracticeKit getKit() {
    return this.kit;
  }
}
