package me.jiaojian.ibook.repository.local;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

import me.jiaojian.ibook.model.Catalog;
import me.jiaojian.ibook.model.Channel;
import me.jiaojian.ibook.repository.Resource;
import me.jiaojian.ibook.repository.DataSource;

/**
 * Created by jiaojian on 2018/1/26.
 */

public class LocalDataSource implements DataSource {

  private final ChannelDao channelDao;
  private final CatalogDao catalogDao;

  public LocalDataSource(Context context) {
    channelDao = LocalDbFactory.get(context, LocalDb.class).getChannelDao();
    catalogDao = LocalDbFactory.get(context, LocalDb.class).getCatalogDao();
  }

  private <T> LiveData<Resource<T>> toLiveData(final LiveData<T> liveData) {
    final MediatorLiveData<Resource<T>> merger = new MediatorLiveData<>();
    merger.postValue(Resource.<T>local().loading());
    merger.addSource(liveData, data -> merger.postValue(Resource.<T>local().data(data)));
    return merger;
  }

  @Override
  public LiveData<Resource<Channel>> getChannel(final Long id) {
    return toLiveData(channelDao.findOne(id));
  }

  @Override
  public LiveData<Resource<List<Channel>>> getChannels() {
    return toLiveData(channelDao.findAll());
  }

  public LiveData<Long> addChannel(final Channel channel) {
    final MutableLiveData<Long> data = new MutableLiveData<>();
    new AsyncTask<Void, Void, Long>() {

      @Override
      protected Long doInBackground(Void... voids) {
        Long cid = channelDao.add(channel);
        for(Catalog c : channel.getCatalogs()) {
          c.setChannelId(cid);
        }
        catalogDao.addAll(channel.getCatalogs());
        return cid;
      }

      @Override
      protected void onPostExecute(Long id) {
        data.postValue(id);
      }

    }.execute();
    return data;
  }

  public void addChannels(List<Channel> channels) {
    final MutableLiveData<List<Long>> data = new MutableLiveData<>();
    new AsyncTask<Void, Void, List<Long>>() {
      @Override
      protected List<Long> doInBackground(Void... voids) {
         return channelDao.addAll(channels);
      }

      @Override
      protected void onPostExecute(List<Long> ids) {
        data.postValue(ids);
      }
    }.execute();
  }
}
