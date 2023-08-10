package de.kontux.icepractice.database.statement.resultless;

public class Update implements ResultLessStatement {
  private final String table;
  
  private final String column;
  
  private final String value;
  
  private final String where;
  
  private final String condition;
  
  public Update(String table, String column, String value, String where, String condition) {
    this.table = table;
    this.column = column;
    this.value = value;
    this.where = where;
    this.condition = condition;
  }
  
  public String constructStatement() {
    return "UPDATE " + this.table + " SET " + this.column + " = '" + this.value + "' WHERE " + this.where + " = '" + this.condition + "';";
  }
  
  public String getTable() {
    return this.table;
  }
  
  public String getColumn() {
    return this.column;
  }
  
  public String getValue() {
    return this.value;
  }
  
  public String getWhere() {
    return this.where;
  }
  
  public String getCondition() {
    return this.condition;
  }
}
