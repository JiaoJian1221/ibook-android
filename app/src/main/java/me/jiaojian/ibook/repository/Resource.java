package me.jiaojian.ibook.repository;

/**
 * Created by jiaojian on 2018/1/26.
 */

public class Resource<T> {

  private T mData;
  private Throwable mError;
  private ResourceSource mSource;
  private ResourceStatus mStatus;
  private int mCode;
  private String mMessage;

  public Resource(ResourceSource source) {
    mSource = source;
  }

  public Resource<T> status(ResourceStatus status) {
    mStatus = status;
    return this;
  }

  public Resource<T> error(int code, String message) {
    mStatus = ResourceStatus.ERROR;
    mCode = code;
    mMessage = message;
    return this;
  }

  public Resource<T> failure(Throwable throwable) {
    mStatus = ResourceStatus.ERROR;
    mError  = throwable;
    return this;
  }

  public Resource<T> data(T data) {
    mStatus = null == data ? ResourceStatus.EMPTY : ResourceStatus.CONTENT;
    mData   = data;
    return this;
  }

  public T getData() {
    return mData;
  }

  public Throwable getError() {
    return mError;
  }

  public int getCode() {
    return mCode;
  }

  public String getMessage() {
    return mMessage;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("Source:" + (mSource == ResourceSource.REMOTE ? "Remote, " : "Local, "));
    sb.append("Status:" + (mStatus.name() + ", "));
    if(mStatus == ResourceStatus.ERROR) {
      sb.append("ERROR:" + mError.getMessage());
    }
    else {
      sb.append("Data:" + mData);
    }
    return sb.toString();
  }

}
