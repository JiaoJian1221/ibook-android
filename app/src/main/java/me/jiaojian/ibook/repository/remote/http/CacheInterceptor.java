package me.jiaojian.ibook.repository.remote.http;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Interceptor;

/**
 * Created by jiaojian on 2018/1/23.
 */
public class CacheInterceptor implements Interceptor {

  private final CacheControl cacheControl;

  public CacheInterceptor(CacheControl cacheControl) {
    this.cacheControl = cacheControl;
  }

  @Override
  public okhttp3.Response intercept(Chain chain) throws IOException {
    return chain.proceed(chain.request().newBuilder().cacheControl(cacheControl).build());
  }
}
