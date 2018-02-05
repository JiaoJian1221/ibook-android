package me.jiaojian.ibook.repository.remote;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by jiaojian on 2018/1/25.
 */

public class RemoteConfig {

  private static final String USERNAME = "username";
  private static final String PASSWORD = "password";

  private static final String HTTP_HOST = "http_host";
  private static final String HTTP_CONNECT_TIMEOUT = "http_connect_timeout";
  private static final String HTTP_READ_TIMEOUT = "http_read_timeout";
  private static final String HTTP_CACHE_MAX_SIZE = "http_cache_max_size";
  private static final String HTTP_CACHE_MAX_AGE = "http_cache_max_age";

  /************************************************************************************/

  private static final String HTTP_HOST_DEFAULT_VALUE = "http://10.0.2.2:8080";

  private static final long HTTP_READ_TIMEOUT_DEFAULT_VALUE = 10L;
  private static final long HTTP_CONNECT_TIMEOUT_DEFAULT_VALUE = 3L;
  private static final long HTTP_CACHE_MAX_AGE_DEFAULT_VALUE = 30 * 24 * 60 * 60;
  private static final long HTTP_CACHE_MAX_SIZE_DEFAULT_VALUE = 300 * 1024 * 1024;

  private static final String USERNAME_DEFAULT_VALUE = "user";
  private static final String PASSWORD_DEFAULT_VALUE = "password";


  /************************************************************************************/

  private final SharedPreferences mSharedPreferences;

  public RemoteConfig(Context context) {
    mSharedPreferences = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
  }

  private String getString(String name, String defValue) {
    return mSharedPreferences.getString(name, defValue);
  }

  private Long getLong(String name, Long defValue) {
    return Long.valueOf(mSharedPreferences.getString(name, defValue.toString()));
  }

  public String getHost() {
    return getString(HTTP_HOST, HTTP_HOST_DEFAULT_VALUE);
  }

  public Long getConnectTimeout() {
    return getLong(HTTP_CONNECT_TIMEOUT, HTTP_CONNECT_TIMEOUT_DEFAULT_VALUE);
  }

  public Long getReadTimeout() {
    return getLong(HTTP_READ_TIMEOUT, HTTP_READ_TIMEOUT_DEFAULT_VALUE);
  }

  public Long getMaxCacheSize() {
    return getLong(HTTP_CACHE_MAX_SIZE, HTTP_CACHE_MAX_SIZE_DEFAULT_VALUE);
  }

  public Long getMaxCacheAge() {
    return getLong(HTTP_CACHE_MAX_AGE, HTTP_CACHE_MAX_AGE_DEFAULT_VALUE);
  }

  /************************************************************************************/

  public String getUsername() {
    return getString(USERNAME, USERNAME_DEFAULT_VALUE);
  }

  public String getPassword() {
    return getString(PASSWORD, PASSWORD_DEFAULT_VALUE);
  }

}
