package com.example.olamundo.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.olamundo.R;
import com.example.olamundo.adapter.SelectChatProfileAdapter;
import com.example.olamundo.models.Members;
import com.example.olamundo.services.GlobalVariable;

public class SelectChatProfileActivity extends Activity {
	Intent intent;
	ImageView imageViewProfilePic;
	GlobalVariable globalVariable;
	List<Members> members;
	Members selectedOwnMember;
	ListView listView;

	private void findThings() {
		imageViewProfilePic = (ImageView) findViewById(R.id.imageView_profile_pic);
		listView = (ListView) findViewById(R.id.listView_sel_chat_pro);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		intent = getIntent();
		globalVariable = (GlobalVariable) getApplicationContext();
		setContentView(R.layout.select_chat_profile);
		findThings();
		members = new ArrayList<Members>(globalVariable.getLoginDetails()
				.getMembers());
		final int selectedProfilePosition = intent.getIntExtra(
				"own_profile_position", 0);
		selectedOwnMember = members.remove(selectedProfilePosition);

		imageViewProfilePic.setImageDrawable(selectedOwnMember.getDrawable());
//		listView.setAdapter(new SelectChatProfileAdapter(this, members));

		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				System.out.println("position is :  " + position);
				int temp;
				if (position < selectedProfilePosition)
					temp = position;
				else
					temp = position + 1;
//				Intent intent = new Intent(getApplicationContext(),
//						ChatPageActivity.class);
				Intent intent = new Intent(getApplicationContext(),
						TheRealAppActivity.class);
				intent.putExtra("own_profile_position", selectedProfilePosition);
				intent.putExtra("chat_person_position", temp);
				
				startActivity(intent);
			}
		});
	}

	public void onReselectChatProfile(View view) {
		finish();
		startActivity(intent);
	}
}
