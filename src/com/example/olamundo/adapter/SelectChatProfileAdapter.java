package com.example.olamundo.adapter;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.olamundo.R;
import com.example.olamundo.models.Members;

public class SelectChatProfileAdapter extends BaseAdapter {

//	private RelativeLayout rightDrawerRLData;
	
	
	private Context context;
	List<Members> member;
	LayoutInflater inflater;
	boolean decider;

	// Constructor
	public SelectChatProfileAdapter(Context context, List<Members> member,
			boolean decider) {
		this.context = context;
		this.member = member;
		this.decider = decider;
		
//		rightDrawerRLData = 
	}

	public void swapData(List<Members> member)
	{this.member = member;}
	
	static class ViewHolder {
		protected TextView textView;
		protected ImageView profileImageView;
		protected ImageView crossImageButton;
		protected LinearLayout linearLayout1;
		protected ImageView circleBlueIV;
	}

	@SuppressLint("NewApi")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		System.out.println("getView" + position);
		ViewHolder viewHolder;
		if (convertView == null) {
			inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			viewHolder = new ViewHolder();
			convertView = inflater.inflate(
					R.layout.select_chat_profile_element, parent, false);
			viewHolder.textView = (TextView) convertView
					.findViewById(R.id.textView_nickname);
			viewHolder.profileImageView = (ImageView) convertView
					.findViewById(R.id.imageView_profile_pic);
			viewHolder.crossImageButton = (ImageView) convertView
					.findViewById(R.id.orangeCrossIB);
			viewHolder.linearLayout1 = (LinearLayout) convertView
					.findViewById(R.id.chat_person_details_LL);
			viewHolder.circleBlueIV = (ImageView) convertView
					.findViewById(R.id.circle_blue_IV);

			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		if (position == (member.size() - 1)) {
			viewHolder.crossImageButton.setVisibility(View.GONE);
			viewHolder.textView.setTextColor(Color.parseColor("#d3d3d3"));
			viewHolder.textView.setText(member.get(position).getName());
			viewHolder.profileImageView.setImageDrawable(member.get(position)
					.getDrawable());
			viewHolder.linearLayout1.setBackgroundColor(Color.TRANSPARENT);
			viewHolder.circleBlueIV.setVisibility(View.GONE);
		} else {
			viewHolder.crossImageButton.setVisibility(View.VISIBLE);
			viewHolder.textView.setTextColor(Color.parseColor("#263a70"));
			viewHolder.textView.setText(member.get(position).getName());
			// viewHolder.imageView.setImageBitmap(member.get(position).getImageView());
			// viewHolder.imageView = member.get(position).getImageView();
			viewHolder.profileImageView.setImageDrawable(member.get(position)
					.getDrawable());
			viewHolder.linearLayout1.setBackground(context.getResources()
					.getDrawable(R.drawable.custom_background_bluebox));
			viewHolder.circleBlueIV.setVisibility(View.VISIBLE);

			if (decider == false)
				viewHolder.crossImageButton.setVisibility(View.GONE);

		}
		return convertView;
	}

	@Override
	public int getCount() {
		return member.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

}
