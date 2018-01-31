package me.jiaojian.ibook.repository.remote;

import android.content.Context;

import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.OkHttpClient;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import me.jiaojian.ibook.repository.remote.http.BasicAuthenticator;
import me.jiaojian.ibook.repository.remote.http.CacheInterceptor;
import me.jiaojian.ibook.repository.remote.http.CacheResponseInterceptor;

/**
 * Created by jiaojian on 2018/1/26.
 */

public class RemoteApiFactory {

  public static <S> S getApi(Context context, Class<S> type, CacheControl cacheControl) {
    RemoteConfig config = new RemoteConfig(context);

    Retrofit retrofit = new Retrofit.Builder()
      .client(
        new OkHttpClient.Builder()
          .connectTimeout(config.getConnectTimeout(), TimeUnit.SECONDS)
          .readTimeout(config.getReadTimeout(), TimeUnit.SECONDS)
          .cache(new Cache(context.getCacheDir(), config.getMaxCacheSize()))
          .authenticator(new BasicAuthenticator(config.getUsername(), config.getPassword()))
          .addNetworkInterceptor(new CacheResponseInterceptor(config.getMaxCacheAge()))
          .addInterceptor(new CacheInterceptor(cacheControl))
          .build()
      )
      .addConverterFactory(GsonConverterFactory.create())
      //.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
      .baseUrl(config.getHost())
      .build();

    return retrofit.create(type);
  }

  public static <S> S getNetworkApi(Context context, Class<S> type) {
    return getApi(context, type, CacheControl.FORCE_NETWORK);
  }

  public static <S> S getCacheApi(Context context, Class<S> type) {
    return getApi(context, type, CacheControl.FORCE_CACHE);
  }}
