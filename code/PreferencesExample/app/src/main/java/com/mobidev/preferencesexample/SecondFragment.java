package com.mobidev.preferencesexample;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class SecondFragment extends Fragment {

	private static final String KEY_CONTENT_TITLE = "SECONDFragment:title";
	private static final String KEY_CONTENT = "SECONDFragment:content";

	private String mTitle = "???";
	private String mContent = "???";

	public static SecondFragment newInstance(String title, String content) {
		SecondFragment fragment = new SecondFragment();

		fragment.mTitle = title;
		fragment.mContent = content;

		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if ((savedInstanceState != null)
				&& savedInstanceState.containsKey(KEY_CONTENT_TITLE)
				&& savedInstanceState.containsKey(KEY_CONTENT)) {
			mTitle = savedInstanceState.getString(KEY_CONTENT_TITLE);
			mContent = savedInstanceState.getString(KEY_CONTENT);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.vp_fragment_two, container, false);

		TextView txtTitle = (TextView) v
				.findViewById(R.id.vp_fragment_two_title);
		txtTitle.setText(mTitle);
		TextView txtContent = (TextView) v
				.findViewById(R.id.vp_fragment_two_desc);
		txtContent.setText(mContent);

		return v;
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putString(KEY_CONTENT_TITLE, mTitle);
		outState.putString(KEY_CONTENT, mContent);
	}

}
