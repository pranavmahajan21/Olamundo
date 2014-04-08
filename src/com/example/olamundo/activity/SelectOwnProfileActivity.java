package com.example.olamundo.activity;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.olamundo.R;
import com.example.olamundo.adapter.SelectOwnProfileAdapter;
import com.example.olamundo.models.LoginDetails;
import com.example.olamundo.models.Members;
import com.example.olamundo.models.Message;
import com.example.olamundo.services.GlobalVariable;
import com.example.olamundo.services.JSONParser;
import com.google.gson.Gson;

public class SelectOwnProfileActivity extends Activity {
	// static int PROFILE_SELECTED = 1;
	static int ADD_PROFILE = 1;
	static int CHAT_PAGE = 10;

	Intent nextIntent;
	SharedPreferences preferences;
	SharedPreferences.Editor editor;
	GlobalVariable globalVariable;

	List<NameValuePair> JSONparams;

	TextView familyProfileTV;
	TextView textView;
	GridView gridView;
	LoginDetails loginDetails;
	List<Members> membersNewReference;
	List<Members> membersDuplicateReference;

	Typeface typeface;
	Typeface typeface2;
	SelectOwnProfileAdapter adapter;

	Drawable drawableForLastBox;
	JSONArray jsonArrayFromServer;
	JSONParser jParser;
	Intent intent;

	private void findThings() {
		gridView = (GridView) findViewById(R.id.gridView);
		familyProfileTV = (TextView) findViewById(R.id.family_profile_textView);
	}

	private void initialVisibilityOfViews() {
		familyProfileTV.setTypeface(typeface);
	}

	private void initializeObjects() {
		globalVariable = (GlobalVariable) getApplicationContext();
		loginDetails = globalVariable.getLoginDetails();
		membersDuplicateReference = loginDetails.getMembers();
		jParser = new JSONParser();
		preferences = getSharedPreferences("hello", MODE_PRIVATE);
		editor = preferences.edit();

		typeface = Typeface.createFromAsset(getAssets(),
				"Fonts/Museo500-Regular.otf");
		typeface2 = Typeface.createFromAsset(getAssets(),
				"Fonts/Museo_Slab_500.otf");
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		System.out.println("SelectOwnProfileActivity");
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.select_own_profile);
		overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
		initializeObjects();
		findThings();
		initialVisibilityOfViews();

		modifyMembers(null);

		adapter = new SelectOwnProfileAdapter(this, membersNewReference,
				typeface2);
		gridView.setAdapter(adapter);
		gridViewListener();
	}

	private void gridViewListener() {
		gridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				System.out.println("position is:  " + position);
				if (position == (membersNewReference.size() - 1)) {
					Toast.makeText(SelectOwnProfileActivity.this,
							" last position", Toast.LENGTH_SHORT).show();
					intent = new Intent(getApplicationContext(),
							AddProfileActvivity.class);
					startActivityForResult(intent, ADD_PROFILE);
				} else {

					intent = new Intent(getApplicationContext(),
							TheRealAppActivity.class);
					intent.putExtra("own_profile_position", position);
					editor.putInt("own_profile_position", position);
					editor.putBoolean("own_profile_selected", true);
					// editor.commit();
					// startActivityForResult(intent, CHAT_PAGE);

					// fetch messages
					JSONparams = new LinkedList<NameValuePair>();
					JSONparams.add(new BasicNameValuePair("email", loginDetails
							.getEmail()));
					JSONparams.add(new BasicNameValuePair("member_id", Integer
							.toString(loginDetails.getMembers().get(position)
									.getId())));
					JSONparams.add(new BasicNameValuePair("count", "15"));

					FetchMsgsAsyncTask asyncTask = new FetchMsgsAsyncTask();
					asyncTask.execute(new String[] { "HW" });

				}
			}
		});

		// gridView.setOnTouchListener(new OnTouchListener() {
		// @Override
		// public boolean onTouch(View v, MotionEvent event) {
		// ((ImageView) v.findViewById(R.id.circleImageView))
		// .setImageDrawable(getResources().getDrawable(
		// R.drawable.circle_orange));
		// ;
		// return false;
		// }
		// });

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		System.out.println("onActivityResult");
		if (resultCode == RESULT_OK)
			if (requestCode == ADD_PROFILE) {
				System.out.println("onActivityResult");
				modifyMembers(null);

				// members.remove(members.size() - 1);
				// members.add(globalVariable.getAddedMember());
				// members.add(new Members("Create a contact",
				// drawableForLastBox));
				adapter.swapData(membersNewReference);
				adapter.notifyDataSetChanged();
			}
		if (resultCode == RESULT_OK)
			if (requestCode == CHAT_PAGE) {
				System.out.println("beaswkpoint");

			}

	}

	private void modifyMembers(Members members) {
		this.membersNewReference = new ArrayList<Members>(
				loginDetails.getMembers());
		System.out.println("init members size  :  "
				+ this.membersNewReference.size());
		if (members != null) {
			System.out.println("member not null");
			this.membersNewReference.add(members);
		}
		drawableForLastBox = getResources().getDrawable(
				R.drawable.create_account);
		this.membersNewReference.add(new Members("Create a contact",
				drawableForLastBox));
		System.out.println("final members size  :  "
				+ this.membersNewReference.size());

	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
	}

	@Override
	protected void onResume() {
		super.onResume();

		modifyMembers(null);
		adapter.notifyDataSetChanged();
	}

	private class FetchMsgsAsyncTask extends AsyncTask<String, Void, JSONArray> {

		@Override
		protected JSONArray doInBackground(String... params) {
			String url = GlobalVariable.URL + GlobalVariable.FETCH_MSGS
					+ ".json";

			jsonArrayFromServer = jParser.getJSONArrayFromUrlAfterHttpGet(url,
					JSONparams);
			// if(jsonArrayFromServer == null)
			// System.out.println("im null");

			return jsonArrayFromServer;
		}

		@Override
		protected void onPostExecute(JSONArray resultJSON) {
			// super.onPostExecute(result);
			System.out.println("postExecute");
			if (resultJSON == null)
				return;

			for (int i = 0; i < resultJSON.length(); i++) {
				try {
					JSONArray personWiseMsgJSONArray = resultJSON
							.getJSONArray(i);
					List<Message> messages = new ArrayList<Message>();
					int temp;
					JSONObject dateWiseMsgJsonObject = null;
					for (int j = 0; j < personWiseMsgJSONArray.length(); j++) {
						// messages = new ArrayList<Message>();
						dateWiseMsgJsonObject = personWiseMsgJSONArray
								.getJSONObject(j);
						JSONArray specificDateMsgsJsonArray = dateWiseMsgJsonObject
								.getJSONArray("messages");
						for (int k = 0; k < specificDateMsgsJsonArray.length(); k++) {
							JSONObject actualMsgObject = specificDateMsgsJsonArray
									.getJSONObject(k);
							messages.add(new Message(Integer
									.parseInt(actualMsgObject
											.getString("message_from")),
									Integer.parseInt(actualMsgObject
											.getString("message_to")),
									actualMsgObject.getInt("message_time"),
									new JSONObject(actualMsgObject
											.getString("message"))));
							
							// System.out.println(actualMsgObject
							// .getString("message")
							// // + "\n\n"
							// // + new JSONObject(actualMsgObject
							// // .getString("message")).toString()
							// );

							}

					}// for (int j = 0)
					temp = dateWiseMsgJsonObject.getInt("contact_id");
					// update loginDetails object with msgs
					for (int z = 0; z < membersDuplicateReference.size(); z++) {
						// System.out.println("z id is   : " +
						// membersDuplicateReference.get(z).getId());
						if (membersDuplicateReference.get(z).getId() == temp)
							// {
							membersDuplicateReference.get(z).setMessages(
									messages);
						// System.out.println("saved"+z);
						// }
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}

			}// for (int i = 0)

			// to check if msgs list object is null or not
			for (int c = 0; c < loginDetails.getMembers().size(); c++) {
				if (globalVariable.getLoginDetails().getMembers().get(c).getMessages() == null) {
					System.out.println("im null");
					System.out.println(loginDetails.getMembers().get(c)
							.getName());
				} else {
					System.out.println("not null");
					System.out.println("msgs size : "
							+ loginDetails.getMembers().get(c)
									.getMessages().get(0)
									.getMsgJsonObject()
									.toString());
				}
			}

			editor.putInt("own_profile_position",
					intent.getIntExtra("own_profile_position", -1));
			editor.putBoolean("own_profile_selected", true);

			// updating login details preferences
			// editor.remove("login_details");
			Gson gson = new Gson();
			String loginDetailsString = gson.toJson(loginDetails);
			editor.putString("login_details", loginDetailsString);

			editor.commit();
			startActivityForResult(intent, CHAT_PAGE);
		}// onPostExecute

	}// FetchMsgsAsyncTask
}
