package de.kontux.icepractice.database.statement.resulted;

public class Select implements ResultedStatement {
  private final String table;
  
  private final String column;
  
  private final String where;
  
  private final String condition;
  
  public Select(String table, String column, String where, String condition) {
    this.table = table;
    this.column = column;
    this.where = where;
    this.condition = condition;
  }
  
  public String constructStatement() {
    return "SELECT " + this.column + " FROM " + this.table + " WHERE " + this.where + " = '" + this.condition + "';";
  }
  
  public String getTable() {
    return this.table;
  }
  
  public String getColumn() {
    return this.column;
  }
  
  public String getWhere() {
    return this.where;
  }
  
  public String getCondition() {
    return this.condition;
  }
}
