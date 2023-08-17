package todolisttutorial;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomOpenHelper;
import androidx.room.RoomOpenHelper.Delegate;
import androidx.room.RoomOpenHelper.ValidationResult;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.room.util.TableInfo.Column;
import androidx.room.util.TableInfo.ForeignKey;
import androidx.room.util.TableInfo.Index;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Configuration;

import com.example.todolisttutorial.TaskItemDao;
import com.example.todolisttutorial.TaskItemDao_Impl;
import com.example.todolisttutorial.TaskItemDatabase;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@SuppressWarnings({"unchecked", "deprecation"})
public final class TaskItemDatabase_Impl extends TaskItemDatabase {
  private volatile TaskItemDao _taskItemDao;

  @Override
  protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(configuration, new Delegate(1) {
      @Override
      public void createAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("CREATE TABLE IF NOT EXISTS `task_item_table` (`name` TEXT NOT NULL, `desc` TEXT NOT NULL, `dueTimeString` TEXT, `completedDateString` TEXT, `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '0514c6dda1691bfe96150bb3cd9dcaaf')");
      }

      @Override
      public void dropAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("DROP TABLE IF EXISTS `task_item_table`");
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onDestructiveMigration(_db);
          }
        }
      }

      @Override
      protected void onCreate(SupportSQLiteDatabase _db) {
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onCreate(_db);
          }
        }
      }

      @Override
      public void onOpen(SupportSQLiteDatabase _db) {
        mDatabase = _db;
        internalInitInvalidationTracker(_db);
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onOpen(_db);
          }
        }
      }

      @Override
      public void onPreMigrate(SupportSQLiteDatabase _db) {
        DBUtil.dropFtsSyncTriggers(_db);
      }

      @Override
      public void onPostMigrate(SupportSQLiteDatabase _db) {
      }

      @Override
      protected ValidationResult onValidateSchema(SupportSQLiteDatabase _db) {
        final HashMap<String, Column> _columnsTaskItemTable = new HashMap<String, Column>(5);
        _columnsTaskItemTable.put("name", new Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTaskItemTable.put("desc", new Column("desc", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTaskItemTable.put("dueTimeString", new Column("dueTimeString", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTaskItemTable.put("completedDateString", new Column("completedDateString", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTaskItemTable.put("id", new Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<ForeignKey> _foreignKeysTaskItemTable = new HashSet<ForeignKey>(0);
        final HashSet<Index> _indicesTaskItemTable = new HashSet<Index>(0);
        final TableInfo _infoTaskItemTable = new TableInfo("task_item_table", _columnsTaskItemTable, _foreignKeysTaskItemTable, _indicesTaskItemTable);
        final TableInfo _existingTaskItemTable = TableInfo.read(_db, "task_item_table");
        if (! _infoTaskItemTable.equals(_existingTaskItemTable)) {
          return new ValidationResult(false, "task_item_table(com.example.todolist.TaskItem).\n"
                  + " Expected:\n" + _infoTaskItemTable + "\n"
                  + " Found:\n" + _existingTaskItemTable);
        }
        return new ValidationResult(true, null);
      }
    }, "0514c6dda1691bfe96150bb3cd9dcaaf", "6769eb8678f91728cbe60aa8480dc8f6");
    final Configuration _sqliteConfig = Configuration.builder(configuration.context)
        .name(configuration.name)
        .callback(_openCallback)
        .build();
    final SupportSQLiteOpenHelper _helper = configuration.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "task_item_table");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `task_item_table`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(TaskItemDao.class, TaskItemDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  public List<Migration> getAutoMigrations(
      @NonNull Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecsMap) {
    return Arrays.asList();
  }

  @Override
  public TaskItemDao taskItemDao() {
    if (_taskItemDao != null) {
      return _taskItemDao;
    } else {
      synchronized(this) {
        if(_taskItemDao == null) {
          _taskItemDao = new TaskItemDao_Impl(this);
        }
        return _taskItemDao;
      }
    }
  }
}
