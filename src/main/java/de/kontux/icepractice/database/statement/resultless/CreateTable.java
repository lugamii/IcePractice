package de.kontux.icepractice.database.statement.resultless;

import de.kontux.icepractice.database.statement.SQLDataType;
import java.util.HashMap;

public class CreateTable implements ResultLessStatement {
  private final String name;
  
  private final HashMap<String, SQLDataType> values;
  
  public CreateTable(String name, HashMap<String, SQLDataType> values) {
    this.name = name;
    this.values = values;
  }
  
  public String constructStatement() {
    StringBuilder builder = new StringBuilder();
    builder.append("CREATE TABLE IF NOT EXISTS ").append(this.name).append(" (");
    int i = 0;
    for (String value : this.values.keySet()) {
      i++;
      SQLDataType type = this.values.get(value);
      builder.append(value).append(" ").append(type.getName());
      if (i < this.values.size())
        builder.append(", "); 
    } 
    builder.append(")");
    return builder.toString();
  }
  
  public String getName() {
    return this.name;
  }
  
  public HashMap<String, SQLDataType> getValues() {
    return this.values;
  }
}
