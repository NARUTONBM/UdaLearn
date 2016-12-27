package com.naruto.tourguide.adapter;
/*
 * Created with Android Studio.
 * User: narutonbm@gmail.com
 * Date: 2016-12-24
 * Time: 16:18
 * Desc: UdaLearn
 */

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.naruto.tourguide.R;
import com.naruto.tourguide.fragment.BeiJingFragment;
import com.naruto.tourguide.fragment.DongJingFragment;
import com.naruto.tourguide.fragment.NanJingFragment;
import com.naruto.tourguide.fragment.ShangHaiFragment;

public class CategoryAdapter extends FragmentPagerAdapter {

	private Context mContext;

	public CategoryAdapter(Context context, FragmentManager fm) {
		super(fm);
		mContext = context;
	}

	@Override
	public Fragment getItem(int position) {
		Fragment fragment = null;
		switch (position) {
		case 0:
			fragment = new DongJingFragment();
			break;

		case 1:
			fragment = new NanJingFragment();
			break;

		case 2:
			fragment = new ShangHaiFragment();
			break;

		case 3:
			fragment = new BeiJingFragment();
			break;

		default:
			break;
		}

		return fragment;
	}

	@Override
	public int getCount() {
		return 4;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		String pageTitle = null;
		switch (position) {
		case 0:
			pageTitle = mContext.getString(R.string.category_dongjing);
			break;

		case 1:
			pageTitle = mContext.getString(R.string.category_nanjing);
			break;

		case 2:
			pageTitle = mContext.getString(R.string.category_shanghai);
			break;

		case 3:
			pageTitle = mContext.getString(R.string.category_beijing);
			break;

		default:
			break;
		}

		return pageTitle;
	}
}
