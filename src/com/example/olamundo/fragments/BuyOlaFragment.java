package com.example.olamundo.fragments;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.olamundo.R;

@SuppressLint("NewApi")
public class BuyOlaFragment extends Fragment {

	Button buy29;
	Button buy59;
	Button buy89;
	ImageButton backButton;
	ImageButton notNowButton;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.buy_app, container, false);
		return rootView;

	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		findThings();
		// initialVisibilityOfViews();
		myOwnListeners();
		super.onViewCreated(view, savedInstanceState);
	}

	private void findThings() {
		buy29 = (Button) getActivity().findViewById(R.id.buy_29);
		buy59 = (Button) getActivity().findViewById(R.id.buy_59);
		buy89 = (Button) getActivity().findViewById(R.id.buy_89);
		backButton = (ImageButton) getActivity().findViewById(R.id.buy_back);
		notNowButton = (ImageButton) getActivity().findViewById(
				R.id.buy_not_now);
	}

	private void myOwnListeners() {

		buy29.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Toast.makeText(getActivity(), "29", Toast.LENGTH_SHORT).show();
			}
		});

		buy59.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Toast.makeText(getActivity(), "59", Toast.LENGTH_SHORT).show();
			}
		});

		buy89.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Toast.makeText(getActivity(), "89", Toast.LENGTH_SHORT).show();
			}
		});

		backButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

			}
		});

		notNowButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

			}
		});

	}

}