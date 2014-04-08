package com.example.olamundo.activity;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.olamundo.R;
import com.example.olamundo.models.LoginDetails;
import com.example.olamundo.models.Members;
import com.example.olamundo.services.CreateDialog;
import com.example.olamundo.services.GlobalVariable;
import com.example.olamundo.services.JSONParser;
import com.google.gson.Gson;

@SuppressLint("NewApi")
public class LoginPageActivity extends Activity {

	static int LOGIN = 1;
	GlobalVariable globalVariable;
	LoginDetails loginDetails;

	EditText emailEditText;
	EditText passwordEditText;
	ImageButton clearEmailButtons;
	ImageButton clearPassButton;
	RelativeLayout emailRL;
	RelativeLayout passwordRL;
	TextView textView;
	TextView forgotPassTV;
	TextView newToOlaTV;
	Button createAccountButton;

	Animation fadeIn;
	Animation fadeOut;
	SharedPreferences preferences;
	SharedPreferences.Editor editor;

	Typeface typeface;
	JSONParser jParser;
	JSONObject jsonFromServer;
	UrlEncodedFormEntity encodedFormEntity;

	AlertDialog alertDialog;
	AlertDialog.Builder ab;
	CreateDialog createDialog;

	private void findThings() {
		emailEditText = (EditText) findViewById(R.id.emailEditText);
		passwordEditText = (EditText) findViewById(R.id.passwordEditText);
		emailRL = (RelativeLayout) findViewById(R.id.emailRL);
		passwordRL = (RelativeLayout) findViewById(R.id.passwordRL);
		clearEmailButtons = (ImageButton) findViewById(R.id.clear_email_button);
		clearPassButton = (ImageButton) findViewById(R.id.clear_password_button);
		textView = (TextView) findViewById(R.id.signIn_textView);
		forgotPassTV = (TextView) findViewById(R.id.forgot_pass_textView);
		newToOlaTV = (TextView) findViewById(R.id.new_to_ola_textView);
		createAccountButton = (Button) findViewById(R.id.create_acc_button);
	}

	private void initialVisibilityOfViews() {
		clearEmailButtons.setVisibility(View.INVISIBLE);
		clearPassButton.setVisibility(View.INVISIBLE);
		emailEditText.setTypeface(typeface);
		passwordEditText.setTypeface(typeface);
		emailEditText.setTypeface(typeface);
		textView.setTypeface(typeface);
		forgotPassTV.setTypeface(typeface);
		newToOlaTV.setTypeface(typeface);
		createAccountButton.setTypeface(typeface);
	}

	private void myOwnTextChangeListeners() {
		emailEditText.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// System.out.println("onTextChanged");
				// System.out.println(s);
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// System.out.println("beforeTextChanged");
			}

			@Override
			public void afterTextChanged(Editable s) {
				// System.out.println("afterTextChanged" + s);
				if (s.toString().contains(" ")) {
					emailEditText.setText(s.toString().replace(" ", ""));
					emailEditText
							.setSelection(emailEditText.getText().length());
				}
				if (emailEditText.getText().toString().length() > 0)
					clearEmailButtons.setVisibility(View.VISIBLE);
				else
					clearEmailButtons.setVisibility(View.INVISIBLE);
			}
		});

		passwordEditText.addTextChangedListener(new TextWatcher() {
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
				if (passwordEditText.getText().toString().length() > 0)
					clearPassButton.setVisibility(View.VISIBLE);
				else
					clearPassButton.setVisibility(View.INVISIBLE);
			}
		});
	}

	private void initializeObjects() {
		typeface = Typeface.createFromAsset(getAssets(),
				"Fonts/Museo500-Regular.otf");
		globalVariable = (GlobalVariable) getApplicationContext();
		preferences = getSharedPreferences("hello", MODE_PRIVATE);
		editor = preferences.edit();
		createDialog = new CreateDialog(this);
		jParser = new JSONParser();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		System.out.println("onCreate LPA");
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.login_page);
		overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
		initializeObjects();
		System.out.println("null  : " + preferences == null);

		((RelativeLayout) findViewById(R.id.login_page))
				.setOnTouchListener(new OnTouchListener() {
					@Override
					public boolean onTouch(View v, MotionEvent event) {
						InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
						imm.hideSoftInputFromWindow(getCurrentFocus()
								.getWindowToken(), 0);
						return false;
					}
				});

		findThings();
		initialVisibilityOfViews();
		myOwnTextChangeListeners();
		staticNonsense();
		fadeIn = AnimationUtils.loadAnimation(getApplicationContext(),
				R.anim.fade_in);
		fadeOut = AnimationUtils.loadAnimation(getApplicationContext(),
				R.anim.fade_out);

	}

	private void staticNonsense() {
//		emailEditText.setText("pranav@motifworks.com");
//		passwordEditText.setText("olamundo");
		emailEditText.setText("sankar@motifworks.com");
		passwordEditText.setText("sankar");
	}

	public void onForgotPassword(View view) {
		Intent intent = new Intent(this, ForgotPasswordActivity.class);
		intent.putExtra("email", emailEditText.getText().toString().trim());
		startActivity(intent);
	}

	public void onCreateAccount(View view) {
		startActivity(new Intent(this, CreateAccountActivity.class));
	}

	public void onClearText(View view) {
		System.out.println("clearing");
		int temp = ((ImageButton) view).getId();
		if (temp == R.id.clear_email_button)
			emailEditText.setText("");

		if (temp == R.id.clear_password_button)
			passwordEditText.setText("");
		view.setVisibility(View.INVISIBLE);
	}

	private class LoginAsyncTask extends AsyncTask<String, Void, Void> {
		public void postMethod2() {
			String url = GlobalVariable.URL + GlobalVariable.SIGN_IN + ".json";
			System.out.println("url is   : " + url);
			JSONObject jsonObject2 = null;
			try {

				JSONObject jsonObject = new JSONObject()
						.put("email", emailEditText.getText().toString())
						.put("password", passwordEditText.getText().toString())
						.put("client_login", "clientlogin");
				jsonObject2 = new JSONObject().put("user", jsonObject);

			} catch (JSONException e) {
				e.printStackTrace();
			}

			jsonFromServer = jParser.getJSONFromUrlAfterHttpPost(url,
					jsonObject2);
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

			if (jsonFromServer.has("errors")) {
				ab = createDialog.createAlertDialog("Login Unsuccessful",
						"You have entered wrong emailID or password", false);
				ab.setPositiveButton("OK",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								dialog.dismiss();
								// finish();
							}
						});
				alertDialog = ab.create();
				alertDialog.show();
			} else {
				createObjects(jsonFromServer);
				// preferences = getSharedPreferences("hello", MODE_PRIVATE);
				// SharedPreferences.Editor editor = preferences.edit();
				editor.putBoolean("isLogin", true);

				Gson gson = new Gson();
				String loginDetailsString = gson.toJson(loginDetails);
				editor.putString("login_details", loginDetailsString);

				editor.commit();
				System.out.println(preferences.getString("profile_name",
						"expectedString2"));
				startActivityForResult((new Intent(LoginPageActivity.this,
						SelectOwnProfileActivity.class)), LOGIN);
			}
		}

	}

	private boolean validate() {
		boolean temp = true;
		emailRL.setBackground(getResources().getDrawable(
				R.drawable.layer_list_blue));
		passwordRL.setBackground(getResources().getDrawable(
				R.drawable.layer_list_blue));

		if (emailEditText.length() == 0) {
			emailEditText.setError("You must enter email ID.");
			emailRL.setBackground(getResources().getDrawable(
					R.drawable.layer_list_red));
			temp = false;

		}
		if (passwordEditText.length() == 0) {
			passwordEditText.setError("You must enter a password.");
			passwordRL.setBackground(getResources().getDrawable(
					R.drawable.layer_list_red));
			temp = false;
		} else if (passwordEditText.length() < 6) {
			passwordEditText
					.setError("Password must be of 6 characters atleast.");
			passwordRL.setBackground(getResources().getDrawable(
					R.drawable.layer_list_red));
			temp = false;
		}
		return temp;
	}

	public void onLogin(View view) {
		if (!validate())
			return;
		LoginAsyncTask loginAsync = new LoginAsyncTask();
		loginAsync.execute(new String[] { "hello world" });
		// asyncExample.

		// preferences = getSharedPreferences("hello", MODE_PRIVATE);
		// SharedPreferences.Editor editor = preferences.edit();
		// editor.putBoolean("isLogin", true);
		// editor.commit();
		// System.out.println(preferences.getString("profile_name",
		// "expectedString2"));
		// startActivityForResult((new Intent(this,
		// ProfileSelectActivity.class)),
		// LOGIN);

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		System.out.println("onActivityResult LoginActi");
		// if (requestCode == LOGIN) {
		// if (resultCode == RESULT_OK) {
		// }
		// }
		finish();
		super.onActivityResult(requestCode, resultCode, data);
	}

	public void createObjects(JSONObject jsonObject) {
		loginDetails = new LoginDetails();
		List<Members> members = new ArrayList<Members>();
//		ImageLoader imageLoader = new ImageLoader(this);
//		ImageView imageView = new ImageView(this);
//		int defaultImage = R.drawable.logoimage_rs;
		try {
			JSONArray tempJSONArray = jsonObject.getJSONArray("members");
			loginDetails.setEmail(jsonObject.getString(GlobalVariable.EMAIL));
			loginDetails.setSuccess(Boolean.parseBoolean(jsonObject
					.getString(GlobalVariable.SUCCESS)));
			loginDetails.setMsgCount(Integer.parseInt(jsonObject
					.getString(GlobalVariable.MSG_COUNT)));
//			loginDetails.setFreeMsgCount(Integer.parseInt(jsonObject
//					.getString(GlobalVariable.FREE_MSG_COUNT)));
			for (int i = 0; i < tempJSONArray.length(); i++) {
				Members tempMember = new Members();
				JSONObject tempJsonObject = tempJSONArray.getJSONObject(i);
				tempMember.setFirstName(tempJsonObject
						.getString(GlobalVariable.FIRST_NAME));
				tempMember.setName(tempJsonObject
						.getString(GlobalVariable.NAME));

				tempMember.setImageURL(tempJsonObject
						.getString(GlobalVariable.IMAGE_URL));
//				imageLoader.displayImage(tempMember.getImageURL(),
//						defaultImage, imageView);
//				System.out.println("tag is :  " + imageView.getTag());
				// tempMember.setDrawable(imageView.getDrawable());
				// tempMember.setImageView(imageView);
				// tempMember.setBitmap(globalVariable
				// .getBitmapFromDrawable(tempMember.getDrawable()));
//				tempMember.setBitmap(imageView.getDrawingCache());
				// System.out.println("byte count: "
				// + tempMember.getBitmap().getAllocationByteCount());

				tempMember.setId(Integer.parseInt(tempJsonObject
						.getString(GlobalVariable.ID)));
				tempMember.setRelationship(tempJsonObject
						.getString(GlobalVariable.RELATIONSHIP));

				members.add(tempMember);

			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		loginDetails.setMembers(members);
		globalVariable.setLoginDetails(loginDetails);
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		System.out.println("onRestart LPA");
	}

	@Override
	protected void onResume() {
		super.onResume();
//		overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
		System.out.println("onResume LPA");
	}

	@Override
	protected void onStop() {
		super.onStop();
		System.out.println("onStop LPA");
	}

}
// http://www.youtube.com/watch?v=ZIRdTnQrDIg