package me.jiaojian.ibook.repository.local;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import me.jiaojian.ibook.model.Catalog;
import me.jiaojian.ibook.model.Channel;

/**
 * Created by jiaojian on 2018/1/26.
 */

@Dao
public interface CatalogDao {

  @Insert(onConflict = OnConflictStrategy.REPLACE)// cache need update
  Long add(Catalog catalog);

  @Insert(onConflict = OnConflictStrategy.REPLACE)// cache need update
  List<Long> addAll(List<Catalog> catalogs);

  @Query("select * from catalog where id = :id")
  LiveData<Catalog> findOne(Long id);

  @Query("select * from catalog")
  LiveData<List<Channel>> findAll();
}
