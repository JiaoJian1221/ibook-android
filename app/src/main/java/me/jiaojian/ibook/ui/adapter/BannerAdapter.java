package me.jiaojian.ibook.ui.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jiaojian on 2018/2/5.
 */
public class BannerAdapter extends PagerAdapter {

  private List<String> mImageList = new ArrayList<String>() {
    {
      add("http://img11.360buyimg.com/cms/jfs/t18517/89/350033132/173932/7f409db2/5a6e7681Nd73c282f.jpg");
      add("http://img11.360buyimg.com/cms/jfs/t13387/55/1887675142/181560/81ccfe54/5a5482a7Ne8b6d787.jpg");
      add("http://img14.360buyimg.com/cms/jfs/t15649/98/1922418784/143730/968c1da4/5a69c23cN525bb687.jpg");
      add("http://img10.360buyimg.com/cms/jfs/t18379/158/182363105/201090/d5f7ab56/5a61bffaN42089f36.jpg");
      add("http://img14.360buyimg.com/cms/jfs/t18676/87/349342224/102260/7ae47732/5a6eb925N603b8595.jpg");
      add("http://img13.360buyimg.com/cms/jfs/t14068/141/2191347415/175452/a7b9268f/5a61c0e1N6dd44b5d.gif");
      add("http://img11.360buyimg.com/cms/jfs/t17656/311/309771474/138232/c3df758b/5a6ae1d7N56fbe926.jpg");
    }
  };

  @Override
  public int getCount() {
    return mImageList.size();
  }

  @Override
  public boolean isViewFromObject(View view, Object object) {
    return view == object;
  }

  @Override
  public Object instantiateItem(ViewGroup container, int position) {
    ImageView view = new ImageView(container.getContext());
    container.addView(view);

    Glide.with(container.getContext()).load(mImageList.get(position)).centerCrop().into(view);

    return view;
  }

  @Override
  public void destroyItem(ViewGroup container, int position, Object object) {
    container.removeView((View) object);
  }
}
