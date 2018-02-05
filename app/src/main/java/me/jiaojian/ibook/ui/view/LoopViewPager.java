package me.jiaojian.ibook.ui.view;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jiaojian on 2018/1/29.
 */

public class LoopViewPager extends ViewPager {
  private LoopPagerAdapterWrapper mAdapter;
  private boolean mBoundaryCaching = true;
  private boolean mBoundaryLooping = true;

  private List<OnPageChangeListener> mOnPageChangeListeners;

  public LoopViewPager(Context context) {
    super(context);
    init(context);
  }

  public LoopViewPager(Context context, AttributeSet attrs) {
    super(context, attrs);
    init(context);
  }

  private void init(Context context) {
    if (onPageChangeListener != null) {
      super.removeOnPageChangeListener(onPageChangeListener);
    }
    super.addOnPageChangeListener(onPageChangeListener);
  }

  /********************************************************************************************/

  @Override
  public void setAdapter(PagerAdapter adapter) {
    mAdapter = new LoopPagerAdapterWrapper(adapter);
    mAdapter.setBoundaryCaching(mBoundaryCaching);
    mAdapter.setBoundaryLooping(mBoundaryLooping);
    super.setAdapter(mAdapter);

    setCurrentItem(0, false);
  }

  @Override
  public PagerAdapter getAdapter() {
    return mAdapter != null ? mAdapter.getRealAdapter() : mAdapter;
  }

  @Override
  public int getCurrentItem() {
    return mAdapter != null ? mAdapter.toRealPosition(super.getCurrentItem()) : 0;
  }

  public void setCurrentItem(int item, boolean smoothScroll) {
    int realItem = mAdapter.toInnerPosition(item);
    super.setCurrentItem(realItem, smoothScroll);
  }

  @Override
  public void setCurrentItem(int item) {
    if (getCurrentItem() != item) {
      setCurrentItem(item, true);
    }
  }

  @Override
  public void setOnPageChangeListener(OnPageChangeListener listener) {
    addOnPageChangeListener(listener);
  }

  @Override
  public void addOnPageChangeListener(OnPageChangeListener listener) {
    if (mOnPageChangeListeners == null) {
      mOnPageChangeListeners = new ArrayList<>();
    }
    mOnPageChangeListeners.add(listener);
  }

  @Override
  public void removeOnPageChangeListener(OnPageChangeListener listener) {
    if (mOnPageChangeListeners != null) {
      mOnPageChangeListeners.remove(listener);
    }
  }

  @Override
  public void clearOnPageChangeListeners() {
    if (mOnPageChangeListeners != null) {
      mOnPageChangeListeners.clear();
    }
  }

  private OnPageChangeListener onPageChangeListener = new OnPageChangeListener() {
    private float mPreviousOffset = -1;
    private float mPreviousPosition = -1;

    @Override
    public void onPageSelected(int position) {

      int realPosition = mAdapter.toRealPosition(position);
      if (mPreviousPosition != realPosition) {
        mPreviousPosition = realPosition;

        if (mOnPageChangeListeners != null) {
          for (int i = 0; i < mOnPageChangeListeners.size(); i++) {
            OnPageChangeListener listener = mOnPageChangeListeners.get(i);
            if (listener != null) {
              listener.onPageSelected(realPosition);
            }
          }
        }
      }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
      int realPosition = position;
      if (mAdapter != null) {
        realPosition = mAdapter.toRealPosition(position);

        if (positionOffset == 0 && mPreviousOffset == 0 && (position == 0 || position == mAdapter.getCount() - 1)) {
          setCurrentItem(realPosition, false);
        }
      }

      mPreviousOffset = positionOffset;

      if (mOnPageChangeListeners != null) {
        for (int i = 0; i < mOnPageChangeListeners.size(); i++) {
          OnPageChangeListener listener = mOnPageChangeListeners.get(i);
          if (listener != null) {
            if (realPosition != mAdapter.getRealCount() - 1) {
              listener.onPageScrolled(realPosition, positionOffset, positionOffsetPixels);
            }
            else {
              if (positionOffset > .5) {
                listener.onPageScrolled(0, 0, 0);
              }
              else {
                listener.onPageScrolled(realPosition, 0, 0);
              }
            }
          }
        }
      }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
      if (mAdapter != null) {
        int position = LoopViewPager.super.getCurrentItem();
        int realPosition = mAdapter.toRealPosition(position);
        if (state == ViewPager.SCROLL_STATE_IDLE && (position == 0 || position == mAdapter.getCount() - 1)) {
          setCurrentItem(realPosition, false);
        }
      }

      if (mOnPageChangeListeners != null) {
        for (int i = 0; i < mOnPageChangeListeners.size(); i++) {
          OnPageChangeListener listener = mOnPageChangeListeners.get(i);
          if (listener != null) {
            listener.onPageScrollStateChanged(state);
          }
        }
      }
    }
  };
}
