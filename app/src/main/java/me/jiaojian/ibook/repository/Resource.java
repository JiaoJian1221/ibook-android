package me.jiaojian.ibook.repository;

/**
 * Created by jiaojian on 2018/1/26.
 */

public class Resource<T> {

  private T data;
  private Throwable error;
  private ResourceSource source;
  private ResourceStatus status;

  private Resource() {
  }

  public static <R> Resource<R> remote() {
    return new Resource<R>().source(ResourceSource.REMOTE);
  }

  public static <R> Resource<R> local() {
    return new Resource<R>().source(ResourceSource.LOCAL);
  }

  private Resource<T> source(ResourceSource source) {
    this.source = source;
    return this;
  }

  public Resource<T> loading() {
    this.status = ResourceStatus.LOADING;
    return this;
  }

  public Resource<T> error(Throwable throwable) {
    this.status = ResourceStatus.ERROR;
    this.error = throwable;
    return this;
  }

  public Resource<T> data(T data) {
    this.status = null == data ? ResourceStatus.EMPTY : ResourceStatus.CONTENT;
    this.data = data;
    return this;
  }

  public T getData() {
    return data;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("Source:" + (this.source == ResourceSource.REMOTE ? "Remote, " : "Local, "));
    sb.append("Status:" + (this.status.name() + ", "));
    if(this.status == ResourceStatus.ERROR) {
      sb.append("ERROR:" + this.error.getMessage());
    }
    else {
      sb.append("Data:" + this.data);
    }
    return sb.toString();
  }

  public boolean hasData() {
    return this.status == ResourceStatus.CONTENT;
  }

  public boolean isLoading() {
    return this.status == ResourceStatus.LOADING;
  }

  public boolean hasError() {
    return this.status == ResourceStatus.ERROR;
  }

  public Throwable getError() {
    return error;
  }

}
