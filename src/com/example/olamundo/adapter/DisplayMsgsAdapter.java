package com.example.olamundo.adapter;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.olamundo.R;
import com.example.olamundo.adapter.SelectOwnProfileAdapter.ViewHolder;
import com.example.olamundo.models.Message;
import com.loopj.android.image.SmartImageView;

public class DisplayMsgsAdapter extends BaseAdapter {
	private Context context;
	List<Message> messages;
	LayoutInflater inflater;
	View word;

	public DisplayMsgsAdapter(Context context, List<Message> messages) {
		super();
		this.context = context;
		this.messages = messages;
	}

	static class ViewHolder {
		protected LinearLayout LLInsideHSV;
	}

	ViewHolder viewHolder;

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			viewHolder = new ViewHolder();
			inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.chat_page_element, parent,
					false);

			viewHolder.LLInsideHSV = (LinearLayout) convertView
					.findViewById(R.id.LL_inside_HSV);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		JSONObject jsonObject = messages.get(position).getMsgJsonObject();
//		System.out.println("has msgs  :  "  + jsonObject.has("message"));
//		System.out.println("huh  " + jsonObject.toString());
		if(jsonObject.has("message"))
		{
		try {
			int i = 1;
			JSONObject theFinalMsgJsonObject = jsonObject.getJSONObject("message");
			while (true) {
//				System.out.println("inside while " + Integer.toString(i) + "" + Integer.toString(i).length());
				if (theFinalMsgJsonObject.has(Integer.toString(i))) {
					System.out.println("inside iff");
					JSONObject oneSpecificWord = theFinalMsgJsonObject
							.getJSONObject(Integer.toString(i));
					word = inflater.inflate(R.layout.word_msg, null, false);
					// put condition to check if image url is not null
					((SmartImageView) word.findViewById(R.id.symbolPicture))
							.setImageUrl(oneSpecificWord.getString("imageURL"));
					((TextView) word.findViewById(R.id.msg_TV))
							.setText(oneSpecificWord.getString("name"));
					viewHolder.LLInsideHSV.addView(word);
					i++;
				} else
					break;
			}// while
			System.out.println("total while  : " + i);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		}
		return convertView;
	}

	@Override
	public int getCount() {
		return messages.size();
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
