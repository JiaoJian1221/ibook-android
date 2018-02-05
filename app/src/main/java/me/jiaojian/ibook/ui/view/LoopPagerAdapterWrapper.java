package me.jiaojian.ibook.ui.view;

import android.database.DataSetObserver;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by jiaojian on 2018/2/2.
 */
public class LoopPagerAdapterWrapper extends PagerAdapter {
  private final PagerAdapter mAdapter;

  public LoopPagerAdapterWrapper(@NonNull PagerAdapter pagerAdapter) {
    mAdapter = pagerAdapter;
  }

  /********************************************************************************************/
  private SparseArray<ToDestroy> mToDestroy = new SparseArray<>();

  private static final boolean DEFAULT_BOUNDARY_CASHING = true;
  private static final boolean DEFAULT_BOUNDARY_LOOPING = true;

  private boolean mBoundaryCaching = DEFAULT_BOUNDARY_CASHING;
  private boolean mBoundaryLooping = DEFAULT_BOUNDARY_LOOPING;

  public void setBoundaryCaching(boolean flag) {
    mBoundaryCaching = flag;
  }

  public void setBoundaryLooping(boolean flag) {
    mBoundaryLooping = flag;
  }

  public int getRealCount() {
    return mAdapter.getCount();
  }

  public PagerAdapter getRealAdapter() {
    return mAdapter;
  }

  public int toInnerPosition(int realPosition) {
    int position = (realPosition + 1);
    return mBoundaryLooping ? position : realPosition;
  }

  public int toRealPosition(int position) {
    int realPosition = position;
    int realCount = getRealCount();
    if (realCount == 0) return 0;
    if (mBoundaryLooping) {
      realPosition = (position - 1) % realCount;
      if (realPosition < 0) {
        realPosition += realCount;
      }
    }
    return realPosition;
  }

  private int getRealFirstPosition() {
    return mBoundaryLooping ? 1 : 0;
  }

  private int getRealLastPosition() {
    return getRealFirstPosition() + getRealCount() - 1;
  }


  private boolean isFragmentAdapter() {
    return mAdapter instanceof FragmentPagerAdapter || mAdapter instanceof FragmentStatePagerAdapter;
  }


  /********************************************************************************************/

  @Override
  public int getCount() {
    int count = getRealCount();
    return mBoundaryLooping ? count + 2 : count;
  }

  @Override
  public Object instantiateItem(ViewGroup container, int position) {
    int realPosition = isFragmentAdapter() ? position : toRealPosition(position);

    if (mBoundaryCaching) {
      ToDestroy toDestroy = mToDestroy.get(position);
      if (toDestroy != null) {
        mToDestroy.remove(position);
        return toDestroy.object;
      }
    }
    return mAdapter.instantiateItem(container, realPosition);
  }

  @Override
  public void destroyItem(ViewGroup container, int position, Object object) {
    int realFirst = getRealFirstPosition();
    int realLast = getRealLastPosition();
    int realPosition = isFragmentAdapter() ? position : toRealPosition(position);

    if (mBoundaryCaching && (position == realFirst || position == realLast)) {
      mToDestroy.put(position, new ToDestroy(container, realPosition, object));
    }
    else {
      mAdapter.destroyItem(container, realPosition, object);
    }
  }

  @Override
  public void notifyDataSetChanged() {
    mToDestroy = new SparseArray<>();
    super.notifyDataSetChanged();
  }

  /********************************************************************************************/

  @Override
  public void startUpdate(ViewGroup container) {
    mAdapter.startUpdate(container);
  }

  @Override
  public void setPrimaryItem(ViewGroup container, int position, Object object) {
    mAdapter.setPrimaryItem(container, position, object);
  }

  @Override
  public void finishUpdate(ViewGroup container) {
    mAdapter.finishUpdate(container);
  }

  @Override
  @Deprecated
  public void startUpdate(View container) {
    mAdapter.startUpdate(container);
  }

  @Override
  @Deprecated
  public Object instantiateItem(View container, int position) {
    return mAdapter.instantiateItem(container, position);
  }

  @Override
  @Deprecated
  public void destroyItem(View container, int position, Object object) {
    mAdapter.destroyItem(container, position, object);
  }

  @Override
  @Deprecated
  public void setPrimaryItem(View container, int position, Object object) {
    mAdapter.setPrimaryItem(container, position, object);
  }

  @Override
  @Deprecated
  public void finishUpdate(View container) {
    mAdapter.finishUpdate(container);
  }

  @Override
  public Parcelable saveState() {
    return mAdapter.saveState();
  }

  @Override
  public void restoreState(Parcelable state, ClassLoader loader) {
    mAdapter.restoreState(state, loader);
  }

  @Override
  public int getItemPosition(Object object) {
    return mAdapter.getItemPosition(object);
  }

  @Override
  public void registerDataSetObserver(DataSetObserver observer) {
    mAdapter.registerDataSetObserver(observer);
  }

  @Override
  public void unregisterDataSetObserver(DataSetObserver observer) {
    mAdapter.unregisterDataSetObserver(observer);
  }

  @Override
  public CharSequence getPageTitle(int position) {
    return mAdapter.getPageTitle(position);
  }

  @Override
  public float getPageWidth(int position) {
    return mAdapter.getPageWidth(position);
  }

  @Override
  public boolean isViewFromObject(View view, Object object) {
    return mAdapter.isViewFromObject(view, object);
  }

  static class ToDestroy {
    ViewGroup container;
    int position;
    Object object;

    public ToDestroy(ViewGroup container, int position, Object object) {
      this.container = container;
      this.position = position;
      this.object = object;
    }
  }
}
