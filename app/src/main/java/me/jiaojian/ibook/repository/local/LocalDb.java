package me.jiaojian.ibook.repository.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import me.jiaojian.ibook.model.Book;
import me.jiaojian.ibook.model.Catalog;
import me.jiaojian.ibook.model.Channel;


/**
 * Created by jiaojian on 2018/1/26.
 */

@Database(entities = { Channel.class, Catalog.class, Book.class }, version = 1, exportSchema = false)
public abstract class LocalDb extends RoomDatabase {

  public abstract ChannelDao getChannelDao();

  public abstract CatalogDao getCatalogDao();
}
