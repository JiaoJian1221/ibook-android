package me.jiaojian.ibook.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * Created by jiaojian on 2018/1/26.
 */

@Entity
public class Channel implements Serializable {

  @PrimaryKey
  private Long id;

  private String name;

  @Ignore
  private List<Catalog> catalogs = Collections.emptyList();

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<Catalog> getCatalogs() {
    return catalogs;
  }

  public void setCatalogs(List<Catalog> catalogs) {
    this.catalogs = catalogs;
  }
}
