package me.jiaojian.ibook.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import me.jiaojian.ibook.R;
import me.jiaojian.ibook.model.Channel;

/**
 * Created by jiaojian on 2018/2/5.
 */

public class ChannelListAdapter extends RecyclerView.Adapter<ChannelListAdapter.ChannelViewHolder> {

  private List<Channel> mChannelList = Collections.emptyList();

  @Override
  public ChannelViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_layout_channel, parent, false);
    return new ChannelViewHolder(view);
  }

  @Override
  public void onBindViewHolder(ChannelViewHolder holder, int position) {
    holder.bind(mChannelList.get(position));
  }

  @Override
  public int getItemCount() {
    return mChannelList.size();
  }

  public void setChannelList(List<Channel> channelList) {
    if(channelList != null && channelList.size() > 0) {
      mChannelList = channelList;
      notifyDataSetChanged();
    }
  }

  static class ChannelViewHolder extends RecyclerView.ViewHolder {

    private final View mItemView;

    public ChannelViewHolder(View itemView) {
      super(itemView);
      mItemView = itemView;
    }

    private <S> S findViewById(int id) {
      return (S) mItemView.findViewById(id);
    }

    public void bind(Channel channel) {
      this.<TextView>findViewById(R.id.text_view_channel_name).setText(channel.getName());
    }
  }

}
