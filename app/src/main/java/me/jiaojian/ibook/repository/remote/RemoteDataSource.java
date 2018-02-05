package me.jiaojian.ibook.repository.remote;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import java.util.List;


import me.jiaojian.ibook.model.Channel;
import me.jiaojian.ibook.repository.Resource;
import me.jiaojian.ibook.repository.DataSource;
import me.jiaojian.ibook.repository.ResourceSource;
import me.jiaojian.ibook.repository.ResourceStatus;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by jiaojian on 2018/1/26.
 */

public class RemoteDataSource implements DataSource {

  private final RemoteApi mRemoteApi;
  private final ResourceSource mSource;

  public RemoteDataSource(RemoteApi api, ResourceSource source) {
    mRemoteApi = api;
    mSource = source;
  }

  private  <T> LiveData<Resource<T>> toLiveData(Call<T> call) {
    final MutableLiveData<Resource<T>> result = new MutableLiveData<>();
    final Resource<T> resource = mSource.<T>newResource().status(ResourceStatus.LOADING);
    result.postValue(resource);

    call.enqueue(new Callback<T>() {

      @Override
      public void onResponse(@NonNull Call<T> call, @NonNull Response<T> response) {
        if(response.isSuccessful()) {
          result.postValue(resource.data(response.body()));
        }
        else {
          result.postValue(resource.error(response.code(), response.message()));
        }
      }

      @Override
      public void onFailure(@NonNull Call<T> call, @NonNull Throwable t) {
        result.postValue(resource.failure(t));
      }

    });
    return result;
  }

  @Override
  public LiveData<Resource<List<Channel>>> getChannels() {
    return toLiveData(mRemoteApi.getChannels());
  }

  @Override
  public LiveData<Resource<Channel>> getChannel(Long id) {
    return toLiveData(mRemoteApi.getChannel(id));
  }

}
