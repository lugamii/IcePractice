package de.kontux.icepractice.database.statement.resultless;

import java.util.HashMap;

public class Insert implements ResultLessStatement {
  private final String table;
  
  private final HashMap<String, String> values;
  
  public Insert(String table, HashMap<String, String> values) {
    this.table = table;
    this.values = values;
  }
  
  public String constructStatement() {
    StringBuilder builder = new StringBuilder();
    builder.append("INSERT INTO ").append(this.table).append(" (");
    int columnCount = 0;
    for (String column : this.values.keySet()) {
      columnCount++;
      builder.append(column);
      if (columnCount < this.values.size())
        builder.append(", "); 
    } 
    builder.append(") VALUES (");
    int valueCount = 0;
    for (String column : this.values.keySet()) {
      valueCount++;
      String value = this.values.get(column);
      builder.append("'").append(value).append("'");
      if (valueCount < this.values.size())
        builder.append(", "); 
    } 
    builder.append(");");
    return builder.toString();
  }
  
  public String getTable() {
    return this.table;
  }
  
  public HashMap<String, String> getValues() {
    return this.values;
  }
}
