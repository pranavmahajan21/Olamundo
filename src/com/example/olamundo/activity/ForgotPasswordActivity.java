package com.example.olamundo.activity;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.olamundo.R;
import com.example.olamundo.services.GlobalVariable;
import com.example.olamundo.services.JSONParser;

public class ForgotPasswordActivity extends Activity {

	ImageButton clearEmailButton;
	EditText emailEditText;
	Intent intent;
	RelativeLayout emailRL;
	JSONParser jParser;
	String emailString;
	JSONObject jsonObjectFromServer;

	public void findThings() {
		intent = getIntent();
		clearEmailButton = (ImageButton) findViewById(R.id.clear_email_button);
		emailEditText = (EditText) findViewById(R.id.emailEditText);
		emailRL = (RelativeLayout) findViewById(R.id.emailRL2);
	}

	public void initialVisibilityOfViews() {
		if (intent.getStringExtra("email").length() > 0)
			emailEditText.setText(intent.getStringExtra("email"));
		else
			clearEmailButton.setVisibility(View.INVISIBLE);
	}

	public void myOwnTextChangeListeners() {
		emailEditText.addTextChangedListener(new TextWatcher() {
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
				if (s.toString().contains(" ")) {
					emailEditText.setText(s.toString().replace(" ", ""));
					emailEditText
							.setSelection(emailEditText.getText().length());
				}
				if (emailEditText.getText().toString().length() > 0)
					clearEmailButton.setVisibility(View.VISIBLE);
				else
					clearEmailButton.setVisibility(View.INVISIBLE);
			}
		});
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.forgot_password);
		overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
//		overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_in);
		findThings();
		initialVisibilityOfViews();
		jParser = new JSONParser();
		myOwnTextChangeListeners();
		((RelativeLayout) findViewById(R.id.forgot_password_ll))
				.setOnTouchListener(new OnTouchListener() {
					@Override
					public boolean onTouch(View v, MotionEvent event) {
						InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
						imm.hideSoftInputFromWindow(getCurrentFocus()
								.getWindowToken(), 0);
						return false;
					}
				});
	}

	public void onClearText(View view) {
		System.out.println("clearing");
		emailEditText.setText("");
		view.setVisibility(View.INVISIBLE);
	}

	public void onCancel(View view) {
		finish();
		overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
	}

	public void onSend(View view) {
		if (!validate())
			return;

		AsyncExample asyncExample = new AsyncExample();
		asyncExample.execute(new String[] { "hello world" });
	}

	private boolean validate() {
		boolean temp = true;
		emailString = emailEditText.getText().toString();
		if (emailString.length() == 0) {
			emailRL.setBackground(getResources().getDrawable(
					R.drawable.custom_background_redbox));
			temp = false;
		}
		return temp;

	}

	private class AsyncExample extends AsyncTask<String, Void, Void> {
		public void postMethod2() {

			String url = GlobalVariable.URL + GlobalVariable.FORGOT_PASSWORD
					+ ".json";

			 JSONObject jsonObjectTopLevel = null;
			 try {
			 JSONObject jsonObjectLevel1 = new JSONObject().put("email",
			 emailString);
			 jsonObjectTopLevel = new JSONObject().put("user",
			 jsonObjectLevel1);
			 } catch (JSONException e) {
			 e.printStackTrace();
			 }
//			JSONObject jsonObjectTopLevel = new JSONObject();
			jsonObjectFromServer = jParser.getJSONFromUrlAfterHttpPost(url,
					jsonObjectTopLevel);

		}

		@Override
		protected Void doInBackground(String... params) {
			try {
				postMethod2();
				System.out.println("doInBackground done");
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// super.onPostExecute(result);
			if (jsonObjectFromServer != null) {
				if (jsonObjectFromServer.has("errors")) {
					Toast.makeText(ForgotPasswordActivity.this, "Email ID not registered", Toast.LENGTH_SHORT).show();
				} else {
//					goto login
					Toast.makeText(ForgotPasswordActivity.this, "Email has been sent to : " + emailString, Toast.LENGTH_SHORT).show();
					finish();
					overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
				}
			} else {

			}
		}

	}
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);

	}
	
}
