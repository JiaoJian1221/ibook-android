package me.jiaojian.ibook.repository.local;

import android.arch.lifecycle.LiveData;
import android.arch.paging.DataSource;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import me.jiaojian.ibook.model.Channel;

/**
 * Created by jiaojian on 2018/1/26.
 */

@Dao
public interface ChannelDao {

  @Insert(onConflict = OnConflictStrategy.REPLACE)// cache need update
  Long add(Channel channel);

  @Insert(onConflict = OnConflictStrategy.REPLACE)// cache need update
  List<Long> addAll(List<Channel> channels);

  @Query("select * from channel where id = :id")
  LiveData<Channel> findOne(Long id);

  @Query("select * from channel")
  LiveData<List<Channel>> findAll();

  @Query("SELECT * FROM channel ORDER BY name ASC")
  DataSource.Factory<Integer, Channel> findChannelsOrderByName();
}
