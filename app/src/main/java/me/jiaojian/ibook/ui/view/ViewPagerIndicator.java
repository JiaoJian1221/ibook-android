package me.jiaojian.ibook.ui.view;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.annotation.TargetApi;
import android.content.Context;
import android.database.DataSetObserver;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import me.jiaojian.ibook.R;

import static android.widget.LinearLayout.LayoutParams.WRAP_CONTENT;

/**
 * Created by jiaojian on 2018/2/1.
 */

public class ViewPagerIndicator extends LinearLayout implements ViewPager.OnPageChangeListener {
  private static final int DEFAULT_INDICATOR_WIDTH = 8;
  private static final int DEFAULT_INDICATOR_MARGIN = 5;

  //private PagerAdapter mAdapter;
  private ViewPager mViewPager;

  private int mLastPosition = -1;

  private int mIndicatorWidth = dip2px(DEFAULT_INDICATOR_WIDTH);
  private int mIndicatorHeight = dip2px(DEFAULT_INDICATOR_WIDTH);
  private int mIndicatorMargin = dip2px(DEFAULT_INDICATOR_MARGIN);

  private int mAnimatorResId = 0;
  private int mAnimatorReverseResId = 0;

  private int mIndicatorBackgroundResId = R.drawable.white_radius;
  private int mIndicatorUnselectedBackgroundResId = R.drawable.black_radius;

  private Animator mAnimatorOut;
  private Animator mAnimatorIn;
  private Animator mImmediateAnimatorOut;
  private Animator mImmediateAnimatorIn;

  public ViewPagerIndicator(Context context) {
    super(context);
    init(context);
  }

  public ViewPagerIndicator(Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
    init(context);
  }

  public ViewPagerIndicator(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init(context);
  }

  @TargetApi(Build.VERSION_CODES.LOLLIPOP)
  public ViewPagerIndicator(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
    super(context, attrs, defStyleAttr, defStyleRes);
    init(context);
  }

  private void init(Context context) {
    mAnimatorResId = mAnimatorResId == 0
      ? R.animator.scale_with_alpha
      : mAnimatorResId;

    mAnimatorOut = createAnimatorOut(context);
    mImmediateAnimatorOut = createAnimatorOut(context);
    mImmediateAnimatorOut.setDuration(0);

    mAnimatorIn = createAnimatorIn(context);
    mImmediateAnimatorIn = createAnimatorIn(context);
    mImmediateAnimatorIn.setDuration(0);

  }

  private Animator createAnimatorOut(Context context) {
    return AnimatorInflater.loadAnimator(context, mAnimatorResId);
  }

  private Animator createAnimatorIn(Context context) {
    Animator animator;
    if (mAnimatorReverseResId == 0) {
      animator = AnimatorInflater.loadAnimator(context, mAnimatorResId);
      animator.setInterpolator(value -> Math.abs(1.0f - value));
    }
    else {
      animator = AnimatorInflater.loadAnimator(context, mAnimatorReverseResId);
    }
    return animator;
  }

  private void createIndicators() {
    removeAllViews();

    for(int i=0; i<mViewPager.getAdapter().getCount(); i++) {
      View indicator = new View(getContext());
      indicator.setLayoutParams(lp());
      indicator.setBackgroundResource(mViewPager.getCurrentItem() == i
        ? mIndicatorBackgroundResId
        : mIndicatorUnselectedBackgroundResId);
      Animator animator = mViewPager.getCurrentItem() == i
        ? mImmediateAnimatorOut
        : mImmediateAnimatorIn;

      if(animator.isRunning()) {
        animator.end();
        animator.cancel();
      }
      animator.setTarget(indicator);

      addView(indicator);
      animator.start();
    }
  }


  public void setupViewPager(ViewPager viewPager) {
    mViewPager = viewPager;
    mViewPager.removeOnPageChangeListener(this);
    mViewPager.addOnPageChangeListener(this);
    mLastPosition = mViewPager.getCurrentItem();

    mViewPager.getAdapter().registerDataSetObserver(new DataSetObserver() {

      @Override
      public void onChanged() {
        int newCount = mViewPager.getAdapter().getCount();
        int currentCount = getChildCount();

        if (newCount == currentCount) {  // No change
        }
        else if (mLastPosition < newCount) {
          mLastPosition = mViewPager.getCurrentItem();
        }
        else {
          mLastPosition = -1;
        }

        createIndicators();
      }
    });
    createIndicators();
  }

  public int dip2px(float dpValue) {
    final float scale = getResources().getDisplayMetrics().density;
    return (int) (dpValue * scale + 0.5f);
  }

  private LinearLayout.LayoutParams lp() {
    LayoutParams lp = new LayoutParams(mIndicatorWidth, mIndicatorHeight);
    lp.leftMargin = mIndicatorMargin;
    lp.rightMargin = mIndicatorMargin;
    lp.topMargin = mIndicatorMargin;
    lp.bottomMargin = mIndicatorMargin;
    return lp;
  }

  @Override
  public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

  }

  private void in(int position) {
    if(mAnimatorIn.isRunning()) {
      mAnimatorIn.end();
      mAnimatorIn.cancel();
    }

    View view = getChildAt(position);
    view.setBackgroundResource(mIndicatorUnselectedBackgroundResId);
    mAnimatorIn.setTarget(view);
    mAnimatorIn.start();
  }

  private void out(int position) {
    if(mAnimatorOut.isRunning()) {
      mAnimatorOut.end();
      mAnimatorOut.cancel();
    }
    View current = getChildAt(position);
    if(current != null) {
      current.setBackgroundResource(mIndicatorBackgroundResId);
      mAnimatorOut.setTarget(current);
      mAnimatorOut.start();
    }
    mLastPosition = position;
  }

  @Override
  public void onPageSelected(int position) {
    in(mLastPosition);
    out(position);
  }

  @Override
  public void onPageScrollStateChanged(int state) {

  }
}
