package com.example.olamundo.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.olamundo.R;
import com.example.olamundo.models.NavDrawerItem;

public class NavDrawerListAdapter extends BaseExpandableListAdapter {

	private Context context;
	private ArrayList<NavDrawerItem> navDrawerItemsHeader;
	HashMap<NavDrawerItem, List<String>> mapChildData;

	public NavDrawerListAdapter(Context context,
			ArrayList<NavDrawerItem> navDrawerItemsHeader,
			HashMap<NavDrawerItem, List<String>> mapChildData) {
		this.context = context;
		this.navDrawerItemsHeader = navDrawerItemsHeader;
		this.mapChildData = mapChildData;
	}

	@Override
	public Object getChild(int groupPosition, int childPosititon) {
//		System.out.println("1");
		// this function returns the childData 1 by 1
		return mapChildData.get(navDrawerItemsHeader.get(groupPosition)).  get(
				childPosititon);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
//		System.out.println("2");
		return childPosition;
	}

	@Override
	public View getChildView(int groupPosition, final int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
//		System.out.println("3");
		final String childText = (String) getChild(groupPosition, childPosition);

		if (convertView == null) {
			LayoutInflater childInflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = childInflater.inflate(R.layout.child, null);
		}

		TextView txtListChild = (TextView) convertView
				.findViewById(R.id.childTextView);

		txtListChild.setText(childText);
		return convertView;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
//		System.out.println("4");
		return mapChildData.get(navDrawerItemsHeader.get(groupPosition))
				.size();
	}

	@Override
	public Object getGroup(int groupPosition) {
//		System.out.println("5");
		return navDrawerItemsHeader.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
//		System.out.println("6");
		return navDrawerItemsHeader.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
//		System.out.println("7");
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
//		System.out.println("8");
		NavDrawerItem navDrawerItem = (NavDrawerItem) getGroup(groupPosition);
		if (convertView == null) {
			LayoutInflater infalInflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = infalInflater.inflate(R.layout.drawer_list_element, null);
		}

		TextView title = (TextView) convertView
				.findViewById(R.id.title);
		title.setTypeface(null, Typeface.BOLD);
		title.setText(navDrawerItem.getTitle());
		
		ImageView icon = (ImageView) convertView
				.findViewById(R.id.icon);
		icon.setImageResource(navDrawerItem.getIcon());
		
		TextView selectedValueTextView = (TextView) convertView.findViewById(R.id.selected_value);
		ImageView rightArrowIV = (ImageView) convertView.findViewById(R.id.right_arrow);
		
		if(navDrawerItem.isSmallTextVisible())
		{
			selectedValueTextView.setVisibility(View.VISIBLE);
			rightArrowIV.setVisibility(View.VISIBLE);
		}

		return convertView;
	}

	@Override
	public boolean hasStableIds() {
//		System.out.println("9");
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
//		System.out.println("10");
		return true;
	}
	// @Override
	// public int getCount() {
	// return navDrawerItems.size();
	// }
	//
	// @Override
	// public Object getItem(int position) {
	// return navDrawerItems.get(position);
	// }
	//
	// @Override
	// public long getItemId(int position) {
	// return position;
	// }
	//
	// @Override
	// public View getView(int position, View convertView, ViewGroup parent) {
	// if (convertView == null) {
	// LayoutInflater mInflater = (LayoutInflater)
	// context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
	// convertView = mInflater.inflate(R.layout.drawer_list_item, null);
	// }
	//
	// ImageView imgIcon = (ImageView) convertView.findViewById(R.id.icon);
	// TextView txtTitle = (TextView) convertView.findViewById(R.id.title);
	// TextView txtCount = (TextView) convertView.findViewById(R.id.counter);
	//
	// imgIcon.setImageResource(navDrawerItems.get(position).getIcon());
	// txtTitle.setText(navDrawerItems.get(position).getTitle());
	//
	// // displaying count
	// // check whether it set visible or not
	// if(navDrawerItems.get(position).getCounterVisibility()){
	// txtCount.setText(navDrawerItems.get(position).getCount());
	// }else{
	// // hide the counter view
	// txtCount.setVisibility(View.GONE);
	// }
	//
	// return convertView;
	// }

}
