package de.kontux.icepractice.items.party;

public enum PartyItemFunction {
  DUEL, QUEUE, EVENTS, INFO, WRONGLY_SETUP, LEAVE;
  
  public static PartyItemFunction getFunction(String name) {
    if (name == null)
      return WRONGLY_SETUP; 
    return valueOf(name.toUpperCase());
  }
}
