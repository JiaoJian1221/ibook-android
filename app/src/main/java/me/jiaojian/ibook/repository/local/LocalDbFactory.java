package me.jiaojian.ibook.repository.local;

import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

/**
 * Created by jiaojian on 2018/1/26.
 */

public class LocalDbFactory {

  private static final String DB_NAME = "DB_IBOOK";

  public static <S extends RoomDatabase> S get(Context context, Class<S> type) {
    return Room.databaseBuilder(context, type, DB_NAME).build();
  }

}
