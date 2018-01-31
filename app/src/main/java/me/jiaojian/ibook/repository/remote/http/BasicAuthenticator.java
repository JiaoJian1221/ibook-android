package me.jiaojian.ibook.repository.remote.http;

import android.support.annotation.Nullable;

import java.io.IOException;

import okhttp3.Authenticator;
import okhttp3.Credentials;
import okhttp3.Request;
import okhttp3.Route;

/**
 * Created by jiaojian on 2018/1/23.
 */
public class BasicAuthenticator implements Authenticator {

  private final String basic;

  public BasicAuthenticator(String username, String password) {
    basic = Credentials.basic(username, password);
  }

  @Nullable
  @Override
  public Request authenticate(Route route, okhttp3.Response response) throws IOException {
    return response.request().newBuilder().header("Authorization", basic).build();
  }
}
