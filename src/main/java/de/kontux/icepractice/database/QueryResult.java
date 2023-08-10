package de.kontux.icepractice.database;

import de.kontux.icepractice.database.statement.resulted.ResultedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class QueryResult {
  private final boolean success;
  
  private final ResultedStatement statement;
  
  private final ResultSet resultSet;
  
  public QueryResult(ResultedStatement statement, ResultSet resultSet) {
    this.success = successfulResultSet(resultSet);
    this.resultSet = resultSet;
    this.statement = statement;
  }
  
  public String getString(String column) {
    try {
      return this.resultSet.getString(column);
    } catch (SQLException e) {
      return null;
    } 
  }
  
  public int getInt(String column) {
    try {
      return this.resultSet.getInt(column);
    } catch (SQLException e) {
      return -1;
    } 
  }
  
  public ResultedStatement getStatement() {
    return this.statement;
  }
  
  public boolean isSuccess() {
    return this.success;
  }
  
  private boolean successfulResultSet(ResultSet resultSet) {
    try {
      return (resultSet != null && resultSet.next());
    } catch (SQLException e) {
      return false;
    } 
  }
}
