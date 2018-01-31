package me.jiaojian.ibook.repository.remote.http;

import java.io.IOException;

import okhttp3.Interceptor;

/**
 * Created by jiaojian on 2018/1/23.
 */
public class CacheResponseInterceptor implements Interceptor {

  private final long age;

  public CacheResponseInterceptor(long maxAge) {
    age = maxAge;
  }

  @Override
  public okhttp3.Response intercept(Chain chain) throws IOException {
    return chain.proceed(chain.request()).newBuilder()
      .removeHeader("Pragma").removeHeader("Cache-Control")
      .header("Cache-Control", "max-age=" + age)
      .build();
  }
}
