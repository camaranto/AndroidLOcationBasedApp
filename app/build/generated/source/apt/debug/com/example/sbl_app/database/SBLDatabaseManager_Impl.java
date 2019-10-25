package com.example.sbl_app.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.db.SupportSQLiteOpenHelper.Callback;
import android.arch.persistence.db.SupportSQLiteOpenHelper.Configuration;
import android.arch.persistence.room.DatabaseConfiguration;
import android.arch.persistence.room.InvalidationTracker;
import android.arch.persistence.room.RoomOpenHelper;
import android.arch.persistence.room.RoomOpenHelper.Delegate;
import android.arch.persistence.room.util.TableInfo;
import android.arch.persistence.room.util.TableInfo.Column;
import android.arch.persistence.room.util.TableInfo.ForeignKey;
import android.arch.persistence.room.util.TableInfo.Index;
import com.example.sbl_app.query.userDAO;
import com.example.sbl_app.query.userDAO_Impl;
import com.example.sbl_app.query.userTrackDAO;
import com.example.sbl_app.query.userTrackDAO_Impl;
import java.lang.IllegalStateException;
import java.lang.Override;
import java.lang.String;
import java.util.HashMap;
import java.util.HashSet;

public class SBLDatabaseManager_Impl extends SBLDatabaseManager {
  private volatile userDAO _userDAO;

  private volatile userTrackDAO _userTrackDAO;

  protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(6) {
      public void createAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("CREATE TABLE IF NOT EXISTS `users` (`user_id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `username` TEXT, `password` TEXT, `phone_number` TEXT)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `user_track` (`track_id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `user_id` INTEGER NOT NULL, `latitude` REAL NOT NULL, `longitude` REAL NOT NULL, `where_is` TEXT, `date_in` TEXT)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, \"f4925f4f3af2dc114fd2f8d43cdf9c42\")");
      }

      public void dropAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("DROP TABLE IF EXISTS `users`");
        _db.execSQL("DROP TABLE IF EXISTS `user_track`");
      }

      protected void onCreate(SupportSQLiteDatabase _db) {
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onCreate(_db);
          }
        }
      }

      public void onOpen(SupportSQLiteDatabase _db) {
        mDatabase = _db;
        internalInitInvalidationTracker(_db);
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onOpen(_db);
          }
        }
      }

      protected void validateMigration(SupportSQLiteDatabase _db) {
        final HashMap<String, TableInfo.Column> _columnsUsers = new HashMap<String, TableInfo.Column>(4);
        _columnsUsers.put("user_id", new TableInfo.Column("user_id", "INTEGER", true, 1));
        _columnsUsers.put("username", new TableInfo.Column("username", "TEXT", false, 0));
        _columnsUsers.put("password", new TableInfo.Column("password", "TEXT", false, 0));
        _columnsUsers.put("phone_number", new TableInfo.Column("phone_number", "TEXT", false, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysUsers = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesUsers = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoUsers = new TableInfo("users", _columnsUsers, _foreignKeysUsers, _indicesUsers);
        final TableInfo _existingUsers = TableInfo.read(_db, "users");
        if (! _infoUsers.equals(_existingUsers)) {
          throw new IllegalStateException("Migration didn't properly handle users(com.example.sbl_app.model.User).\n"
                  + " Expected:\n" + _infoUsers + "\n"
                  + " Found:\n" + _existingUsers);
        }
        final HashMap<String, TableInfo.Column> _columnsUserTrack = new HashMap<String, TableInfo.Column>(6);
        _columnsUserTrack.put("track_id", new TableInfo.Column("track_id", "INTEGER", true, 1));
        _columnsUserTrack.put("user_id", new TableInfo.Column("user_id", "INTEGER", true, 0));
        _columnsUserTrack.put("latitude", new TableInfo.Column("latitude", "REAL", true, 0));
        _columnsUserTrack.put("longitude", new TableInfo.Column("longitude", "REAL", true, 0));
        _columnsUserTrack.put("where_is", new TableInfo.Column("where_is", "TEXT", false, 0));
        _columnsUserTrack.put("date_in", new TableInfo.Column("date_in", "TEXT", false, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysUserTrack = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesUserTrack = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoUserTrack = new TableInfo("user_track", _columnsUserTrack, _foreignKeysUserTrack, _indicesUserTrack);
        final TableInfo _existingUserTrack = TableInfo.read(_db, "user_track");
        if (! _infoUserTrack.equals(_existingUserTrack)) {
          throw new IllegalStateException("Migration didn't properly handle user_track(com.example.sbl_app.model.UserTrack).\n"
                  + " Expected:\n" + _infoUserTrack + "\n"
                  + " Found:\n" + _existingUserTrack);
        }
      }
    }, "f4925f4f3af2dc114fd2f8d43cdf9c42");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(configuration.context)
        .name(configuration.name)
        .callback(_openCallback)
        .build();
    final SupportSQLiteOpenHelper _helper = configuration.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  protected InvalidationTracker createInvalidationTracker() {
    return new InvalidationTracker(this, "users","user_track");
  }

  @Override
  public userDAO userDAO() {
    if (_userDAO != null) {
      return _userDAO;
    } else {
      synchronized(this) {
        if(_userDAO == null) {
          _userDAO = new userDAO_Impl(this);
        }
        return _userDAO;
      }
    }
  }

  @Override
  public userTrackDAO userTrackDAO() {
    if (_userTrackDAO != null) {
      return _userTrackDAO;
    } else {
      synchronized(this) {
        if(_userTrackDAO == null) {
          _userTrackDAO = new userTrackDAO_Impl(this);
        }
        return _userTrackDAO;
      }
    }
  }
}
