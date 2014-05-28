package com.example.olamundo.adapter;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.olamundo.R;
import com.example.olamundo.imagesfromurl.ImageLoader;
import com.example.olamundo.models.Members;
import com.example.olamundo.services.GlobalVariable;
import com.example.olamundo.services.RoundedCornersSmartImageView;
import com.example.olamundo.services.RoundedImageView;

public class SelectOwnProfileAdapter extends BaseAdapter {
	private Context context;
	List<Members> member;
	LayoutInflater inflater;
	Typeface typeface;
	ImageLoader imageLoader;
	int defaultImage = R.drawable.logoimage_rs;
	ImageView imageView;
	GlobalVariable globalVariable;
//	GradientDrawable gd;

	// Constructor
	public SelectOwnProfileAdapter(Context context, List<Members> member,
			Typeface typeface) {
		this.context = context;
		this.member = member;
		this.typeface = typeface;
		imageLoader = new ImageLoader(context);
		imageView = new ImageView(context);
		globalVariable = new GlobalVariable();
		System.out.println("members length :" + member.size());
		
//		gd = new GradientDrawable();
//		gd.setColor(Color.TRANSPARENT);
//		gd.setCornerRadius(75f);
//		gd.setStroke(1,Color.BLACK);
	}

	public void swapData(List<Members> member) {
		this.member = member;
	}

	static class ViewHolder {
		protected TextView textView;
//		 protected RoundImageView smartIV;
		protected RoundedCornersSmartImageView smartIV;
		protected ImageView circleImageView;

	}

	ViewHolder viewHolder;
	@SuppressLint("NewApi")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		System.out.println("members length :" + member.size());
		if (convertView == null) {
			viewHolder = new ViewHolder();
			inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.select_own_profile_element,
					parent, false);

			viewHolder.textView = (TextView) convertView
					.findViewById(R.id.profileName);
			viewHolder.textView.setTypeface(typeface);
			viewHolder.smartIV = (RoundedCornersSmartImageView) convertView
					.findViewById(R.id.profilePicture);
			viewHolder.circleImageView = (ImageView) convertView
					.findViewById(R.id.circleImageView);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		if (position == (member.size() - 1)) {
			System.out.println("position : " + position);
			viewHolder.textView.setText(member.get(position).getName());
			viewHolder.textView.setTextColor(Color.parseColor("#d3d3d3"));
			viewHolder.smartIV.setImageDrawable(member.get(position)
					.getDrawable());
			viewHolder.circleImageView.setVisibility(View.INVISIBLE);
		}
		else {
			viewHolder.textView.setText(member.get(position).getName());
			viewHolder.textView.setTextColor(Color.parseColor("#405581"));

			System.out.println("image url is   : "
					+ member.get(position).getImageURL());
			viewHolder.smartIV.setImageUrl(member.get(position).getImageURL());
			viewHolder.smartIV.setRadius(50);

			viewHolder.circleImageView.setVisibility(View.VISIBLE);
		}
		System.out.println(position);

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

	private class FetchImageAsync extends
			AsyncTask<String, Void, Void> {

		@Override
		protected Void doInBackground(String... params) {
			viewHolder.smartIV.setImageUrl(params[0]);
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// super.onPostExecute(result);
			System.out.println("postExecute");
			viewHolder.smartIV.setImageBitmap(RoundedImageView
					.getCroppedBitmap(globalVariable
							.getBitmapFromDrawable(viewHolder.smartIV
									.getDrawable()), 100));
		}// onPostExecute

	}// Async class
}