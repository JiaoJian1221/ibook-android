package me.jiaojian.ibook.repository;

import android.arch.lifecycle.LiveData;

import java.util.List;

import me.jiaojian.ibook.model.Channel;

/**
 * Created by jiaojian on 2018/1/26.
 */

public interface DataSource {

  LiveData<Resource<List<Channel>>> getChannels();

  LiveData<Resource<Channel>> getChannel(Long id);

}
