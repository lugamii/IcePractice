package de.kontux.icepractice.api.playerstates;

public enum PlayerState {
  IDLE(false, true, false),
  STARTING_MATCH(false, true, true),
  MATCH(true, false, true),
  EVENT(false, true, false),
  SPECTATING(false, true, false),
  EDITOR(false, true, true);
  
  private final boolean pvp;
  
  private final boolean interact;
  
  private final boolean moveInventory;
  
  PlayerState(boolean pvp, boolean interact, boolean moveInventory) {
    this.pvp = pvp;
    this.interact = interact;
    this.moveInventory = moveInventory;
  }
  
  public boolean isPvp() {
    return this.pvp;
  }
  
  public boolean isInteract() {
    return this.interact;
  }
  
  public boolean isMoveInventory() {
    return this.moveInventory;
  }
}
