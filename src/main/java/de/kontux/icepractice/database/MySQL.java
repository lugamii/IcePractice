package de.kontux.icepractice.database;

import de.kontux.icepractice.IcePracticePlugin;
import de.kontux.icepractice.database.statement.resulted.ResultedStatement;
import de.kontux.icepractice.database.statement.resultless.ResultLessStatement;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.function.Consumer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;

public class MySQL {
  public static final String TABLE = "IcePracticeElo";
  
  private final IcePracticePlugin plugin;
  
  private Connection connection;
  
  private DatabaseMetaData meta;
  
  private boolean useMySQL;
  
  public MySQL(IcePracticePlugin plugin) {
    this.plugin = plugin;
    this.useMySQL = plugin.getConfig().getBoolean("config.mysql.use", false);
  }
  
  public void connect(String host, String database, String user, String password, int port, Consumer<Boolean> callback) {
    if (!this.useMySQL) {
      callback.accept(Boolean.valueOf(false));
      return;
    } 
    this.plugin.log(ChatColor.YELLOW + "Connecting to your MySQL Database...");
    try {
      this.connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database + "?autoReconnect=true", user, password);
      this.meta = this.connection.getMetaData();
      this.plugin.log(ChatColor.GREEN + "Successfully connected to your MySQL Database.");
      callback.accept(Boolean.valueOf(true));
    } catch (SQLException e) {
      callback.accept(Boolean.valueOf(false));
      this.useMySQL = false;
      logError(e, "Connecting to Database, check your credentials!");
    } 
  }
  
  public void runUpdateStatement(ResultLessStatement command, boolean async) {
    if (!this.useMySQL || this.connection == null)
      return; 
    if (async) {
      Bukkit.getScheduler().runTaskAsynchronously((Plugin)this.plugin, () -> update(command.constructStatement()));
    } else {
      update(command.constructStatement());
    } 
  }
  
  private void update(String query) {
    try {
      Statement statement = this.connection.createStatement();
      statement.execute(query);
    } catch (SQLException e) {
      logError(e, query);
    } 
  }
  
  public QueryResult query(ResultedStatement command) {
    if (!this.useMySQL || this.connection == null)
      return null; 
    ResultSet rs = null;
    try {
      Statement statement = this.connection.createStatement();
      rs = statement.executeQuery(command.constructStatement());
    } catch (SQLException e) {
      logError(e, command.constructStatement());
    } 
    return new QueryResult(command, rs);
  }
  
  public void disconnect() {
    if (!this.useMySQL)
      return; 
    try {
      if (this.connection == null || this.connection.isClosed())
        return; 
      this.connection.close();
      this.plugin.log(ChatColor.GREEN + "Successfully disconnected from your MySQL Database.");
    } catch (SQLException e) {
      logError(e, "Disconnecting from Database.");
    } 
  }
  
  private void logError(SQLException exception, String statement) {
    Bukkit.getScheduler().runTask((Plugin)this.plugin, () -> {
          this.plugin.log(" ");
          this.plugin.log(ChatColor.RED + "Error while executing a MySQL Statement!");
          this.plugin.log(ChatColor.RED + "Cause: " + exception.getMessage());
          this.plugin.log(ChatColor.RED + "Statement: " + statement);
          this.plugin.log(" ");
        });
  }
  
  public boolean hasColumn(String table, String name) {
    if (name == null || this.meta == null)
      return false; 
    try {
      return this.meta.getColumns(null, null, table, name).next();
    } catch (SQLException e) {
      return false;
    } 
  }
  
  public boolean useMySQL() {
    return this.useMySQL;
  }
  
  public DatabaseMetaData getMeta() {
    return this.meta;
  }
  
  public IcePracticePlugin getPlugin() {
    return this.plugin;
  }
}
