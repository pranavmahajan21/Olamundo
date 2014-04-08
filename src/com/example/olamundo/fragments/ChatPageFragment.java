package com.example.olamundo.fragments;

import java.util.List;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.GestureDetector.OnGestureListener;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.olamundo.R;
import com.example.olamundo.activity.TheRealAppActivity;
import com.example.olamundo.adapter.DisplayMsgsAdapter;
import com.example.olamundo.models.Members;
import com.example.olamundo.services.GlobalVariable;

@SuppressLint("NewApi")
public class ChatPageFragment extends Fragment {

	RelativeLayout chatPageRL;
	// TextView freeMsgsTV;
	// ImageButton buyIB;
	ListView chatPageListView;

	ImageButton sendIB;
	HorizontalScrollView chatMessageHSV;
	LinearLayout LLInsideHSV;

	View child;
	Typeface typeface;
	LayoutInflater inflater;
	Context context;

	GlobalVariable globalVariable;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.chat_page, container, false);
		return rootView;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		findThings();
		context = getActivity();
		typeface = Typeface.createFromAsset(getActivity().getAssets(),
				"Fonts/Museo700-Regular.otf");

		inflater = (LayoutInflater) getActivity().getSystemService(
				Context.LAYOUT_INFLATER_SERVICE);
		child = inflater.inflate(R.layout.word_element, null, false);
		LLInsideHSV.addView(child);
		globalVariable = (GlobalVariable) context.getApplicationContext();

		initialVisibilityOfViews();

		// buyIB.setOnClickListener(new OnClickListener() {
		// @Override
		// public void onClick(View arg0) {
		// Toast.makeText(getActivity(), "buy", Toast.LENGTH_SHORT).show();
		// }
		// });

		final GestureDetector detector = new GestureDetector(context,
				new OnGestureListener() {

					@Override
					public boolean onSingleTapUp(MotionEvent e) {
						Toast.makeText(getActivity(), "HSV", Toast.LENGTH_SHORT)
								.show();
						return false;
					}

					@Override
					public void onShowPress(MotionEvent e) {
					}

					@Override
					public boolean onScroll(MotionEvent e1, MotionEvent e2,
							float distanceX, float distanceY) {
						return false;
					}

					@Override
					public void onLongPress(MotionEvent e) {
					}

					@Override
					public boolean onFling(MotionEvent e1, MotionEvent e2,
							float velocityX, float velocityY) {
						return false;
					}

					@Override
					public boolean onDown(MotionEvent e) {
						return false;
					}
				});

		chatMessageHSV.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// child = inflater.inflate(R.layout.word_element, null, false);
				// LLinsideHSV.addView(child);
				detector.onTouchEvent(event);
				return false;
			}
		});

		LLInsideHSV.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Toast.makeText(getActivity(), "LL", Toast.LENGTH_SHORT).show();
				// LLInsideHSV.addView(child);
				// LLInsideHSV.addView(child);

			}
		});

		sendIB.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Toast.makeText(getActivity(), "send", Toast.LENGTH_SHORT)
						.show();
				LLInsideHSV.addView(child);
				// LLInsideHSV.addView(child);

			}
		});
		super.onViewCreated(view, savedInstanceState);
	}

	private void findThings() {
		chatPageRL = (RelativeLayout) getActivity()
				.findViewById(R.id.chat_page);
		chatPageListView = (ListView) getActivity().findViewById(
				R.id.chat_page_listView);
		sendIB = (ImageButton) getActivity().findViewById(
				R.id.send_image_button);
		chatMessageHSV = (HorizontalScrollView) getActivity().findViewById(
				R.id.chat_message_HSV);
		// freeMsgsTV = (TextView) getActivity().findViewById(
		// R.id.free_msgs_textView);
		// buyIB = (ImageButton)
		// getActivity().findViewById(R.id.buy_imageButton);
		LLInsideHSV = (LinearLayout) getActivity().findViewById(
				R.id.LL_inside_HSV);
		System.out.println("end");
	}

	private void initialVisibilityOfViews() {
		// freeMsgsTV.setText(GlobalVariable.FREE_MSGS);
		// freeMsgsTV.setTypeface(typeface);
	}

	public void displayChatMsgs(int selectedPosition) {
		System.out.println("inside Chat Frag");
		Toast.makeText(getActivity(), "display msgs", Toast.LENGTH_SHORT)
				.show();

		List<Members> members = globalVariable.getLoginDetails().getMembers();
		DisplayMsgsAdapter adapter = new DisplayMsgsAdapter(context, members.get(selectedPosition).getMessages());

		chatPageListView.setAdapter(adapter);
	}

}
