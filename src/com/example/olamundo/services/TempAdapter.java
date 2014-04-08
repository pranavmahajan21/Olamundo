package com.example.olamundo.services;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.olamundo.R;
import com.loopj.android.image.SmartImageView;

public class TempAdapter extends BaseAdapter implements Filterable {
	private Context context;
	List<Temp> tempList;
	List<Temp> origList;
	LayoutInflater inflater;
	private TempFilter filter;

	// Constructor
	public TempAdapter(Context context, List<Temp> tempList) {
		this.context = context;
		this.tempList = tempList;
		origList = this.tempList;
		filter = new TempFilter();
	}

	static class ViewHolder {
		protected TextView textView;
		// protected TextView textView2;
		protected SmartImageView smartImageView;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;

		if (convertView == null) {
			inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			viewHolder = new ViewHolder();
			convertView = inflater.inflate(R.layout.auto_text_list_element, parent, false);
			viewHolder.textView = (TextView) convertView
					.findViewById(R.id.textView);
			viewHolder.smartImageView = (SmartImageView) convertView
					.findViewById(R.id.profilePicture);
			// viewHolder.textView2 = (TextView) convertView
			// .findViewById(R.id.textView2);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		viewHolder.textView.setText(tempList.get(position).getName());
		// viewHolder.textView2.setText(tempList.get(position).getUrl());
		viewHolder.smartImageView.setImageUrl(tempList.get(position).getUrl());
		return convertView;

	}

	@Override
	public int getCount() {
		if (tempList != null)
			return tempList.size();
		else
			return 0;
	}

	@Override
	public Object getItem(int arg0) {
		return tempList.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// return tempList.get(arg0).getID();
		return 0;
	}

	@Override
	public Filter getFilter() {
		return filter;
	}

	private class TempFilter extends Filter {
		@Override
		protected FilterResults performFiltering(CharSequence charSequence) {
			System.out.println("performFiltering");
			FilterResults oReturn = new FilterResults();
			ArrayList<Temp> results = new ArrayList<Temp>();
			if (origList == null)
				origList = tempList;

			if (charSequence != null) {
				if (origList != null && origList.size() > 0) {
					for (Temp g : origList) {
						if (g.getName().contains(charSequence))
							results.add(g);
					}
				}
				oReturn.values = results;
			}
			return oReturn;
		}

		@Override
		protected void publishResults(CharSequence constraint,
				FilterResults results) {
			System.out.println("publishResults");
			tempList = (ArrayList<Temp>) results.values;
			notifyDataSetChanged();
		}
	}
	
	
}
