package com.example.sbl_app.query;

import android.arch.persistence.db.SupportSQLiteStatement;
import android.arch.persistence.room.EntityDeletionOrUpdateAdapter;
import android.arch.persistence.room.EntityInsertionAdapter;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.RoomSQLiteQuery;
import android.database.Cursor;
import com.example.sbl_app.model.UserTrack;
import java.lang.Override;
import java.lang.String;
import java.util.ArrayList;
import java.util.List;

public class userTrackDAO_Impl implements userTrackDAO {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfUserTrack;

  private final EntityDeletionOrUpdateAdapter __deletionAdapterOfUserTrack;

  private final EntityDeletionOrUpdateAdapter __updateAdapterOfUserTrack;

  public userTrackDAO_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfUserTrack = new EntityInsertionAdapter<UserTrack>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `user_track`(`track_id`,`user_id`,`latitude`,`longitude`,`where_is`,`date_in`) VALUES (nullif(?, 0),?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, UserTrack value) {
        stmt.bindLong(1, value.getTrack_id());
        stmt.bindLong(2, value.getUser_id());
        stmt.bindDouble(3, value.getLatitude());
        stmt.bindDouble(4, value.getLongitude());
        if (value.getWhere_is() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getWhere_is());
        }
        if (value.getDate_in() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getDate_in());
        }
      }
    };
    this.__deletionAdapterOfUserTrack = new EntityDeletionOrUpdateAdapter<UserTrack>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `user_track` WHERE `track_id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, UserTrack value) {
        stmt.bindLong(1, value.getTrack_id());
      }
    };
    this.__updateAdapterOfUserTrack = new EntityDeletionOrUpdateAdapter<UserTrack>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `user_track` SET `track_id` = ?,`user_id` = ?,`latitude` = ?,`longitude` = ?,`where_is` = ?,`date_in` = ? WHERE `track_id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, UserTrack value) {
        stmt.bindLong(1, value.getTrack_id());
        stmt.bindLong(2, value.getUser_id());
        stmt.bindDouble(3, value.getLatitude());
        stmt.bindDouble(4, value.getLongitude());
        if (value.getWhere_is() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getWhere_is());
        }
        if (value.getDate_in() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getDate_in());
        }
        stmt.bindLong(7, value.getTrack_id());
      }
    };
  }

  @Override
  public void insert(UserTrack users) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfUserTrack.insert(users);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(UserTrack user) {
    __db.beginTransaction();
    try {
      __deletionAdapterOfUserTrack.handle(user);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(UserTrack users) {
    __db.beginTransaction();
    try {
      __updateAdapterOfUserTrack.handle(users);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public List<UserTrack> getItems(int id) {
    final String _sql = "SELECT * FROM user_track WHERE user_id LIKE ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfTrackId = _cursor.getColumnIndexOrThrow("track_id");
      final int _cursorIndexOfUserId = _cursor.getColumnIndexOrThrow("user_id");
      final int _cursorIndexOfLatitude = _cursor.getColumnIndexOrThrow("latitude");
      final int _cursorIndexOfLongitude = _cursor.getColumnIndexOrThrow("longitude");
      final int _cursorIndexOfWhereIs = _cursor.getColumnIndexOrThrow("where_is");
      final int _cursorIndexOfDateIn = _cursor.getColumnIndexOrThrow("date_in");
      final List<UserTrack> _result = new ArrayList<UserTrack>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final UserTrack _item;
        _item = new UserTrack();
        final int _tmpTrack_id;
        _tmpTrack_id = _cursor.getInt(_cursorIndexOfTrackId);
        _item.setTrack_id(_tmpTrack_id);
        final int _tmpUser_id;
        _tmpUser_id = _cursor.getInt(_cursorIndexOfUserId);
        _item.setUser_id(_tmpUser_id);
        final double _tmpLatitude;
        _tmpLatitude = _cursor.getDouble(_cursorIndexOfLatitude);
        _item.setLatitude(_tmpLatitude);
        final double _tmpLongitude;
        _tmpLongitude = _cursor.getDouble(_cursorIndexOfLongitude);
        _item.setLongitude(_tmpLongitude);
        final String _tmpWhere_is;
        _tmpWhere_is = _cursor.getString(_cursorIndexOfWhereIs);
        _item.setWhere_is(_tmpWhere_is);
        final String _tmpDate_in;
        _tmpDate_in = _cursor.getString(_cursorIndexOfDateIn);
        _item.setDate_in(_tmpDate_in);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }
}
