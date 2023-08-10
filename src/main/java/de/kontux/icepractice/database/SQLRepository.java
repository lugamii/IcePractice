package de.kontux.icepractice.database;

import de.kontux.icepractice.api.kit.IcePracticeKit;
import de.kontux.icepractice.database.statement.SQLDataType;
import de.kontux.icepractice.database.statement.resulted.ResultedStatement;
import de.kontux.icepractice.database.statement.resulted.Select;
import de.kontux.icepractice.database.statement.resultless.CreateColumn;
import de.kontux.icepractice.database.statement.resultless.CreateTable;
import de.kontux.icepractice.database.statement.resultless.Insert;
import de.kontux.icepractice.database.statement.resultless.ResultLessStatement;
import de.kontux.icepractice.database.statement.resultless.Update;
import de.kontux.icepractice.kits.KitManager;
import java.util.HashMap;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class SQLRepository {
  private final MySQL mySQL;
  
  public SQLRepository(MySQL mySQL) {
    this.mySQL = mySQL;
  }
  
  public void addPlayer(UUID uuid, String name) {
    if (!this.mySQL.useMySQL() || playerExists(uuid))
      return; 
    HashMap<String, String> values = new HashMap<>();
    values.put("UUID", uuid.toString());
    values.put("NAME", name);
    for (IcePracticeKit kit : KitManager.getInstance().getKits()) {
      if (kit.isRanked())
        values.put(kit.getName(), "1000"); 
    } 
    Insert insert = new Insert("IcePracticeElo", values);
    this.mySQL.runUpdateStatement((ResultLessStatement)insert, true);
  }
  
  private boolean playerExists(UUID uuid) {
    if (!this.mySQL.useMySQL())
      return false; 
    Select select = new Select("IcePracticeElo", "*", "UUID", uuid.toString());
    QueryResult result = this.mySQL.query((ResultedStatement)select);
    return (result.isSuccess() && result.getString("UUID") != null);
  }
  
  public int getElo(UUID uuid, IcePracticeKit kit) {
    if (!this.mySQL.useMySQL())
      return 1000; 
    Select select = new Select("IcePracticeElo", "*", "UUID", uuid.toString());
    QueryResult result = this.mySQL.query((ResultedStatement)select);
    return result.isSuccess() ? result.getInt(kit.getName()) : 1000;
  }
  
  public void setElo(UUID uuid, IcePracticeKit kit, int elo) {
    if (!this.mySQL.useMySQL())
      return; 
    if (!playerExists(uuid))
      return; 
    Update update = new Update("IcePracticeElo", kit.getName(), String.valueOf(elo), "UUID", uuid.toString());
    this.mySQL.runUpdateStatement((ResultLessStatement)update, true);
  }
  
  public boolean useMySql() {
    return this.mySQL.useMySQL();
  }
  
  public void setUp() {
    HashMap<String, SQLDataType> values = new HashMap<>();
    values.put("UUID", SQLDataType.VARCHAR);
    values.put("NAME", SQLDataType.VARCHAR);
    this.mySQL.runUpdateStatement((ResultLessStatement)new CreateTable("IcePracticeElo", values), true);
    if (!this.mySQL.hasColumn("IcePracticeElo", "NAME"))
      this.mySQL.runUpdateStatement((ResultLessStatement)new CreateColumn("IcePracticeElo", "NAME", "UUID", SQLDataType.VARCHAR), false); 
    Bukkit.getScheduler().scheduleSyncDelayedTask((Plugin)this.mySQL.getPlugin(), () -> {
          for (IcePracticeKit kit : KitManager.getInstance().getKits()) {
            if (kit.isRanked() && !this.mySQL.hasColumn("IcePracticeElo", kit.getName())) {
              CreateColumn createColumn = new CreateColumn("IcePracticeElo", kit.getName(), "UUID", SQLDataType.INT);
              this.mySQL.runUpdateStatement((ResultLessStatement)createColumn, true);
            } 
          } 
        },1L);
  }
}
