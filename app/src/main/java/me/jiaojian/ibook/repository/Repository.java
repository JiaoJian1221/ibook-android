package me.jiaojian.ibook.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.content.Context;

import java.util.List;

import me.jiaojian.ibook.model.Channel;
import me.jiaojian.ibook.repository.remote.RemoteApi;
import me.jiaojian.ibook.repository.remote.RemoteApiFactory;
import me.jiaojian.ibook.repository.remote.RemoteDataSource;

/**
 * Created by jiaojian on 2018/2/5.
 */

public class Repository {

  private final DataSource mNetworkDataSource;
  private final DataSource mCacheDataSource;

  public Repository(Context context) {
    mCacheDataSource   = new RemoteDataSource(RemoteApiFactory.getCacheApi(  context, RemoteApi.class), ResourceSource.CACHE);
    mNetworkDataSource = new RemoteDataSource(RemoteApiFactory.getNetworkApi(context, RemoteApi.class), ResourceSource.REMOTE);
  }

  public LiveData<Resource<List<Channel>>> getChannels() {
    final MediatorLiveData<Resource<List<Channel>>> merger = new MediatorLiveData<>();
    merger.addSource(mCacheDataSource.getChannels(),  merger::postValue);
    merger.addSource(mNetworkDataSource.getChannels(), merger::postValue);
    return merger;
  }

  public LiveData<Resource<List<Channel>>> getChannelsFromCache() {
    return mCacheDataSource.getChannels();
  }

  public LiveData<Resource<List<Channel>>> getChannelsFromNetwork() {
    return mNetworkDataSource.getChannels();
  }

}
