package de.kontux.icepractice.database.statement.resultless;

import de.kontux.icepractice.database.statement.SQLDataType;

public class CreateColumn implements ResultLessStatement {
  private final String table;
  
  private final String name;
  
  private final String after;
  
  private final SQLDataType type;
  
  public CreateColumn(String table, String name, String after, SQLDataType type) {
    this.table = table;
    this.name = name;
    this.after = after;
    this.type = type;
  }
  
  public String constructStatement() {
    return "ALTER TABLE " + this.table + " ADD COLUMN " + this.name + " " + this.type.getName() + " AFTER " + this.after;
  }
  
  public String getTable() {
    return this.table;
  }
  
  public String getAfter() {
    return this.after;
  }
  
  public SQLDataType getType() {
    return this.type;
  }
}
