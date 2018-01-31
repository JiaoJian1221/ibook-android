package me.jiaojian.ibook.repository.remote;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

import me.jiaojian.ibook.model.Channel;

/**
 * Created by jiaojian on 2018/1/26.
 */

public interface RemoteApi {

  @GET("/channels")
  Call<List<Channel>> getChannels();

  @GET("/channels/{id}")
  Call<Channel> getChannel(@Path("id") Long id);


}
