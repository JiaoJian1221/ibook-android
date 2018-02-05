package me.jiaojian.ibook.vm;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.support.annotation.NonNull;

import java.util.List;

import me.jiaojian.ibook.model.Channel;
import me.jiaojian.ibook.repository.Repository;
import me.jiaojian.ibook.repository.Resource;

/**
 * Created by jiaojian on 2018/2/5.
 */

public class ChannelViewModel extends AndroidViewModel {

  private final Repository mRepository;

  public ChannelViewModel(@NonNull Application application) {
    super(application);
    mRepository = new Repository(application);
  }

  private final MediatorLiveData<Resource<List<Channel>>> mChannelList = new MediatorLiveData<>();
  public LiveData<Resource<List<Channel>>> getChannels() {
    return mChannelList;
  }

  public void loadFromCache() {
    mChannelList.addSource(mRepository.getChannelsFromCache(), mChannelList::postValue);
  }

  public void loadFromNetwork() {
    mChannelList.addSource(mRepository.getChannelsFromNetwork(), mChannelList::postValue);
  }
}
