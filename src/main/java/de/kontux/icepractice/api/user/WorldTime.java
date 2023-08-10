package de.kontux.icepractice.api.user;

public enum WorldTime {
  DAY(1000L),
  SUNSET(13000L),
  NIGHT(14000L);
  
  private final long timeCode;
  
  WorldTime(long timeCode) {
    this.timeCode = timeCode;
  }
  
  public long getTimeCode() {
    return this.timeCode;
  }
}
