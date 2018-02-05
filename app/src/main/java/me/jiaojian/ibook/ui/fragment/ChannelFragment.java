package me.jiaojian.ibook.ui.fragment;


import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.jiaojian.ibook.R;
import me.jiaojian.ibook.ui.adapter.ChannelListAdapter;
import me.jiaojian.ibook.vm.ChannelViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChannelFragment extends Fragment {
  private static final String TAG = ChannelFragment.class.getSimpleName();

  private View mContentView;

  public ChannelFragment() {
    // Required empty public constructor
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    mContentView = inflater.inflate(R.layout.fragment_channel, container, false);
    binding();
    return mContentView;
  }

  private void binding() {
    ChannelListAdapter adapter = new ChannelListAdapter();

    RecyclerView recyclerView = mContentView.findViewById(R.id.recycler_view);
    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    recyclerView.setAdapter(adapter);

    ChannelViewModel vm = ViewModelProviders.of(this).get(ChannelViewModel.class);
    vm.getChannels().observe(this, resource -> {
        Log.d(TAG, resource.toString());
        adapter.setChannelList(resource.getData());
    });
    mContentView.findViewById(R.id.button_load_cache).setOnClickListener(view -> {
      vm.loadFromCache();
    });
    mContentView.findViewById(R.id.button_load_network).setOnClickListener(view -> {
      vm.loadFromNetwork();
    });
  }


}
