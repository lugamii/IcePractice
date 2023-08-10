package de.kontux.icepractice.items.join;

public enum JoinItemFunction {
  UNRANKED, RANKED, EVENTS, SETTINGS, STATS, EDITOR, PARTY, WRONGLY_SETUP;
  
  public static JoinItemFunction getFunction(String name) {
    if (name == null)
      return WRONGLY_SETUP; 
    return valueOf(name.toUpperCase());
  }
}
