package com.example.olamundo.activity;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.olamundo.R;
import com.example.olamundo.models.Members;
import com.example.olamundo.services.GlobalVariable;
import com.example.olamundo.services.JSONParser;

@SuppressLint("NewApi")
public class AddProfileActvivity extends Activity {

	private EditText fullnameET;
	private EditText lastnameET;
	private ImageButton clearFullNameImageButton;
	private ImageButton clearRelationImageButton;
	private RelativeLayout fullnameRL;
	private RelativeLayout lastnameRL;
	JSONObject jsonObjectFromServer;
	JSONParser jParser;
	GlobalVariable globalVariable;
	LinearLayout masterLL;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.add_profile);
		findThings();
		myOwnTextChangeListeners();
		initialVisibikityOfViews();
		jParser = new JSONParser(this);
		globalVariable = (GlobalVariable) getApplicationContext();
		
		masterLL.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(getCurrentFocus()
						.getWindowToken(), 0);
				return false;
			}
		});
	}

	private void initialVisibikityOfViews() {
		clearFullNameImageButton.setVisibility(View.GONE);
		clearRelationImageButton.setVisibility(View.GONE);
		masterLL.setPadding(100, 110, 100, 0);
		
	}

	private void findThings() {
		clearFullNameImageButton = (ImageButton) findViewById(R.id.clear_fullname_imageButton);
		clearRelationImageButton = (ImageButton) findViewById(R.id.clear_relation_imageButton);
		fullnameET = (EditText) findViewById(R.id.fullname_edittext);
		lastnameET = (EditText) findViewById(R.id.relation_edittext);
		fullnameRL = (RelativeLayout) findViewById(R.id.fullname_RL);
		lastnameRL = (RelativeLayout) findViewById(R.id.lastname_RL);
		masterLL = (LinearLayout) findViewById(R.id.add_profile_LL);
	}

	private void myOwnTextChangeListeners() {
		fullnameET.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
				if (fullnameET.getText().toString().length() > 0)
					clearFullNameImageButton.setVisibility(View.VISIBLE);
				else
					clearFullNameImageButton.setVisibility(View.INVISIBLE);
			}
		});

		lastnameET.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
				if (lastnameET.getText().toString().length() > 0)
					clearRelationImageButton.setVisibility(View.VISIBLE);
				else
					clearRelationImageButton.setVisibility(View.INVISIBLE);
			}
		});
	}

	private boolean validate() {
		boolean temp = true;
		fullnameRL.setBackground(getResources().getDrawable(
				R.drawable.layer_list_blue));
		lastnameRL.setBackground(getResources().getDrawable(
				R.drawable.layer_list_blue));
		if (fullnameET.getText().toString().trim().length() == 0) {
			// fullnameEditText.setError("You must enter your fullname.");
			fullnameRL.setBackground(getResources().getDrawable(
					R.drawable.layer_list_red));
			temp = false;
		}
		if (lastnameET.getText().toString().trim().length() == 0) {
			// fullnameEditText.setError("You must enter your fullname.");
			lastnameRL.setBackground(getResources().getDrawable(
					R.drawable.layer_list_red));
			temp = false;
		}

		return temp;
	}

	public void onSave(View view) {
		if (!validate())
			return;
		JSONObject jsonObject = null;
		try {
			jsonObject = new JSONObject()
					.put("firstname", fullnameET.getText().toString())
					.put("lastname", lastnameET.getText().toString())
					.put("email", "pranav@motifworks.com");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		// temp.size();
		AddProfileAsync addProfileAsync = new AddProfileAsync();
		addProfileAsync.execute(jsonObject);

	}

	public void onCancel(View view) {
		finish();
	}

	public void onClearText(View view) {
		System.out.println("onClearText");
		int temp = ((ImageButton) view).getId();

		if (temp == R.id.clear_fullname_imageButton) {
			fullnameET.setText("");
		} else if (temp == R.id.clear_relation_imageButton) {
			lastnameET.setText("");
		}
	}
	
	private class AddProfileAsync extends
			AsyncTask<JSONObject, Void, JSONObject> {

		@Override
		protected JSONObject doInBackground(JSONObject... params) {
			try {
				String url = GlobalVariable.URL
						+ GlobalVariable.ADD_EDIT_PROFILE + ".json";
				jsonObjectFromServer = jParser.getJSONFromUrlAfterHttpPost3(
						url, params[0], null,
						"user[members_attributes][0][avatar]", null);
				System.out.println("doInBackground done");
			} catch (Exception e) {
				e.printStackTrace();
			}
			return jsonObjectFromServer;
		}

		@Override
		protected void onPostExecute(JSONObject jsonObjectFromServer) {
			// super.onPostExecute(result);
			System.out.println("postExecute");

			if (jsonObjectFromServer == null)
				System.out.println("im null");
			else {
				System.out.println("im not null");
				if (jsonObjectFromServer.has(GlobalVariable.ID)) {
					try {
						Members members = new Members(
								jsonObjectFromServer
										.getString(GlobalVariable.FIRST_NAME),
								jsonObjectFromServer
										.getString(GlobalVariable.NAME),
								jsonObjectFromServer
										.getString(GlobalVariable.IMAGE_URL),
								jsonObjectFromServer.getInt(GlobalVariable.ID),
								jsonObjectFromServer
										.getString(GlobalVariable.RELATIONSHIP));
//						globalVariable.setAddedMember(members);
						globalVariable.getLoginDetails().getMembers().add(members);
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
				AddProfileActvivity.this.setResult(RESULT_OK);
				finish();
			}//else
		}//onPostExecute

	}//Async class
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
	}

}
