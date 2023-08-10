package de.kontux.icepractice.database.statement;

public enum SQLDataType {
  VARCHAR("varchar(64)"),
  INT("int");
  
  private final String name;
  
  SQLDataType(String name) {
    this.name = name;
  }
  
  public String getName() {
    return this.name;
  }
}
