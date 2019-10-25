package com.example.sbl_app.query;

import android.arch.persistence.db.SupportSQLiteStatement;
import android.arch.persistence.room.EntityDeletionOrUpdateAdapter;
import android.arch.persistence.room.EntityInsertionAdapter;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.RoomSQLiteQuery;
import android.database.Cursor;
import com.example.sbl_app.model.User;
import java.lang.Override;
import java.lang.String;
import java.util.ArrayList;
import java.util.List;

public class userDAO_Impl implements userDAO {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfUser;

  private final EntityDeletionOrUpdateAdapter __deletionAdapterOfUser;

  private final EntityDeletionOrUpdateAdapter __updateAdapterOfUser;

  public userDAO_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfUser = new EntityInsertionAdapter<User>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `users`(`user_id`,`username`,`password`,`phone_number`) VALUES (nullif(?, 0),?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, User value) {
        stmt.bindLong(1, value.getUser_id());
        if (value.getUsername() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getUsername());
        }
        if (value.getPassword() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getPassword());
        }
        if (value.getPhone_number() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getPhone_number());
        }
      }
    };
    this.__deletionAdapterOfUser = new EntityDeletionOrUpdateAdapter<User>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `users` WHERE `user_id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, User value) {
        stmt.bindLong(1, value.getUser_id());
      }
    };
    this.__updateAdapterOfUser = new EntityDeletionOrUpdateAdapter<User>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `users` SET `user_id` = ?,`username` = ?,`password` = ?,`phone_number` = ? WHERE `user_id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, User value) {
        stmt.bindLong(1, value.getUser_id());
        if (value.getUsername() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getUsername());
        }
        if (value.getPassword() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getPassword());
        }
        if (value.getPhone_number() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getPhone_number());
        }
        stmt.bindLong(5, value.getUser_id());
      }
    };
  }

  @Override
  public void insert(User users) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfUser.insert(users);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(User user) {
    __db.beginTransaction();
    try {
      __deletionAdapterOfUser.handle(user);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(User users) {
    __db.beginTransaction();
    try {
      __updateAdapterOfUser.handle(users);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public List<User> getItems() {
    final String _sql = "SELECT * FROM users";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfUserId = _cursor.getColumnIndexOrThrow("user_id");
      final int _cursorIndexOfUsername = _cursor.getColumnIndexOrThrow("username");
      final int _cursorIndexOfPassword = _cursor.getColumnIndexOrThrow("password");
      final int _cursorIndexOfPhoneNumber = _cursor.getColumnIndexOrThrow("phone_number");
      final List<User> _result = new ArrayList<User>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final User _item;
        _item = new User();
        final int _tmpUser_id;
        _tmpUser_id = _cursor.getInt(_cursorIndexOfUserId);
        _item.setUser_id(_tmpUser_id);
        final String _tmpUsername;
        _tmpUsername = _cursor.getString(_cursorIndexOfUsername);
        _item.setUsername(_tmpUsername);
        final String _tmpPassword;
        _tmpPassword = _cursor.getString(_cursorIndexOfPassword);
        _item.setPassword(_tmpPassword);
        final String _tmpPhone_number;
        _tmpPhone_number = _cursor.getString(_cursorIndexOfPhoneNumber);
        _item.setPhone_number(_tmpPhone_number);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<User> getUserByUserName(String username) {
    final String _sql = "SELECT * FROM users WHERE username LIKE ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (username == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, username);
    }
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfUserId = _cursor.getColumnIndexOrThrow("user_id");
      final int _cursorIndexOfUsername = _cursor.getColumnIndexOrThrow("username");
      final int _cursorIndexOfPassword = _cursor.getColumnIndexOrThrow("password");
      final int _cursorIndexOfPhoneNumber = _cursor.getColumnIndexOrThrow("phone_number");
      final List<User> _result = new ArrayList<User>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final User _item;
        _item = new User();
        final int _tmpUser_id;
        _tmpUser_id = _cursor.getInt(_cursorIndexOfUserId);
        _item.setUser_id(_tmpUser_id);
        final String _tmpUsername;
        _tmpUsername = _cursor.getString(_cursorIndexOfUsername);
        _item.setUsername(_tmpUsername);
        final String _tmpPassword;
        _tmpPassword = _cursor.getString(_cursorIndexOfPassword);
        _item.setPassword(_tmpPassword);
        final String _tmpPhone_number;
        _tmpPhone_number = _cursor.getString(_cursorIndexOfPhoneNumber);
        _item.setPhone_number(_tmpPhone_number);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }
}
