package me.jiaojian.ibook.repository.remote;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import java.util.List;


import me.jiaojian.ibook.model.Channel;
import me.jiaojian.ibook.repository.Resource;
import me.jiaojian.ibook.repository.DataSource;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by jiaojian on 2018/1/26.
 */

public class RemoteDataSource implements DataSource {

  protected final RemoteApi api;

  public RemoteDataSource(RemoteApi api) {
    this.api = api;
  }

  protected  <T> LiveData<Resource<T>> toLiveData(Call<T> call) {
    final MutableLiveData<Resource<T>> data = new MutableLiveData<>();

    data.postValue(Resource.<T>remote().loading());

    call.enqueue(new Callback<T>() {

      @Override
      public void onResponse(Call<T> call, Response<T> response) {
        if(response.isSuccessful()) {
          data.postValue(Resource.<T>remote().data(response.body()));
        }
        else {
          data.postValue(Resource.<T>remote().error(new RuntimeException("ERROR response, code:" + response.code() + ", message:" + response.message())));
        }
      }

      @Override
      public void onFailure(Call<T> call, Throwable t) {
        data.postValue(Resource.<T>remote().error(t));
      }

    });
    return data;
  }

  @Override
  public LiveData<Resource<List<Channel>>> getChannels() {
    return toLiveData(api.getChannels());
  }

  @Override
  public LiveData<Resource<Channel>> getChannel(Long id) {
    return toLiveData(api.getChannel(id));
  }

}
