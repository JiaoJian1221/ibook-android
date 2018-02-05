package me.jiaojian.ibook.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.jiaojian.ibook.R;
import me.jiaojian.ibook.ui.adapter.BannerAdapter;
import me.jiaojian.ibook.ui.view.LoopViewPager;
import me.jiaojian.ibook.ui.view.LoopViewPagerPlayer;
import me.jiaojian.ibook.ui.view.ViewPagerIndicator;
import me.jiaojian.ibook.ui.view.ViewPagerPlayer;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {


  public HomeFragment() {
    // Required empty public constructor
  }

  private View mContentView;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    mContentView= inflater.inflate(R.layout.fragment_home, container, false);
    setupViewPager();
    setupLoopViewPager();
    return mContentView;
  }

  private <S> S findViewById(int id) {
    return (S)mContentView.findViewById(id);
  }

  private void setupLoopViewPager() {
    LoopViewPager loopViewPager = findViewById(R.id.loop_view_pager);
    ViewPagerIndicator loopViewPagerIndicator = findViewById(R.id.loop_view_Pager_indicator);

    loopViewPager.setAdapter(new BannerAdapter());
    loopViewPagerIndicator.setupViewPager(loopViewPager);

    LoopViewPagerPlayer loopPlayer = new LoopViewPagerPlayer(loopViewPager);
    loopPlayer.play(2000L);

    loopViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {

      @Override
      public void onPageSelected(int position) {
        Log.d("LOOP BANNER", "CURRENT POSITION:" + position);
      }

    });
  }

  private void setupViewPager() {
    ViewPager viewPager = findViewById(R.id.view_pager);
    ViewPagerIndicator viewPagerIndicator = findViewById(R.id.view_Pager_indicator);

    viewPager.setAdapter(new BannerAdapter());
    viewPagerIndicator.setupViewPager(viewPager);

    ViewPagerPlayer player = new ViewPagerPlayer(viewPager);
    player.play(2000L);

    viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {

      @Override
      public void onPageSelected(int position) {
        Log.d("BANNER", "CURRENT POSITION:" + position);
      }

    });
  }

}
