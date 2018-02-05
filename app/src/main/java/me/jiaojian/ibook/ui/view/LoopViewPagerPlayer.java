package me.jiaojian.ibook.ui.view;

import android.database.DataSetObserver;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;

/**
 * Created by jiaojian on 2018/2/2.
 */
public class LoopViewPagerPlayer extends Handler implements ViewPager.OnPageChangeListener {

  private static final int NEXT = 1;
  private static final int STOP = 2;
  private static final int UPDATE = 3;
  private static final int JUMP = 4;

  private long mDelay = 6000;

  private int mCurrentItem = 0, mStep = 1, mCount;
  private final ViewPager mViewPager;
  //private final PagerAdapter mAdapter;

  public LoopViewPagerPlayer(ViewPager view) {
    mViewPager = view;

    setupViewPager();
  }


  private void setupViewPager() {
    mViewPager.addOnPageChangeListener(this);
    mViewPager.getAdapter().registerDataSetObserver(new DataSetObserver() {

      @Override
      public void onChanged() {
        mCount = mViewPager.getAdapter().getCount();
      }

    });
    mCount = mViewPager.getAdapter().getCount();
    mCurrentItem = mViewPager.getCurrentItem();
  }

  @Override
  public void handleMessage(Message msg) {
    super.handleMessage(msg);

    removeCallbacksAndMessages(null);

    switch (msg.what) {
      case NEXT:
        mCurrentItem += mStep;
        mViewPager.setCurrentItem(mCurrentItem, true);
        break;
      case STOP:
        //pause play, do not send message
        break;
      case UPDATE:
        //user change page index, need rest page index
        mCurrentItem = msg.arg1;
        //mStep = mCurrentItem == 0 ? 1 : (mCurrentItem + 1) >= mCount ? -1 : mStep;
        break;
      case JUMP:
        //user change page index, need rest page index
        mCurrentItem = msg.arg1;
        mViewPager.setCurrentItem(mCurrentItem, true);
        break;
      default:
        break;
    }
  }

  public void play(long delay) {
    mDelay = delay;
    sendEmptyMessageDelayed(NEXT, mDelay);
  }

  public void play() {
    sendEmptyMessageDelayed(NEXT, mDelay);
  }

  public void stop() {
    sendEmptyMessage(STOP);
  }

  public void destroy() {
    mViewPager.removeOnPageChangeListener(this);
  }

  private void next() {
    sendEmptyMessageDelayed(NEXT, mDelay);
  }

  private void update(int position) {
    sendMessage(Message.obtain(this, UPDATE, position, 0));
  }

  @Override
  public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

  }

  @Override
  public void onPageSelected(int position) {
    update(position);
  }

  @Override
  public void onPageScrollStateChanged(int state) {
    switch (state) {
      case ViewPager.SCROLL_STATE_DRAGGING:
        stop();
        break;
      case ViewPager.SCROLL_STATE_IDLE:
        next();
        break;
      default:
        break;
    }
  }
}
