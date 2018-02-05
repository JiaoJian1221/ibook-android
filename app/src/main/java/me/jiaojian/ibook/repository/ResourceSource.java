package me.jiaojian.ibook.repository;

import android.support.annotation.NonNull;

/**
 * Created by jiaojian on 2018/1/31.
 */
public enum ResourceSource {
  LOCAL, REMOTE, CACHE;

  @NonNull
  public <T> Resource<T> newResource() {
    switch (this) {
      case CACHE:
        return new Resource<>(this);
      case REMOTE:
        return new Resource<>(this);
      case LOCAL:
        return new Resource<>(this);
      default:
        return null;
    }
  }

}
