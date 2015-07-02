package com.mobidev.preferencesexample;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import com.viewpagerindicator.IconPagerAdapter;

public class IntroFragmentAdapter extends FragmentPagerAdapter implements
		IconPagerAdapter {
	protected static final String[] CONTENT_TITLE = new String[] { "Welcome!",
			"Post", "Search", "Enjoy convenience, anytime", };
	protected static final String[] CONTENT = new String[] {
			"Swipe to learn about Maskani",
			"Post details of vacant houses, apartments or plots",
			"Search available house, apartments, or plots",
			"Create account to get notifications.", };

	private int mCount = CONTENT.length;

	public IntroFragmentAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int position) {
		switch (position) {
		case 0:
			return FirstFragment.newInstance(CONTENT_TITLE[position],
					CONTENT[position]);
		case 1:
			return SecondFragment.newInstance(CONTENT_TITLE[position],
					CONTENT[position]);
		case 2:
			return ThirdFragment.newInstance(CONTENT_TITLE[position],
					CONTENT[position]);
		case 3:
			return FourthFragment.newInstance(CONTENT_TITLE[position],
					CONTENT[position]);
		default:
			return FirstFragment.newInstance(CONTENT_TITLE[position],
					CONTENT[position]);
		}

	}

	@Override
	public int getCount() {
		return mCount;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return IntroFragmentAdapter.CONTENT[position % CONTENT.length];
	}

	@Override
	public int getIconResId(int index) {
		// return ICONS[index % ICONS.length];
		return 0;
	}

	public void setCount(int count) {
		if (count > 0 && count <= 10) {
			mCount = count;
			notifyDataSetChanged();
		}
	}
}