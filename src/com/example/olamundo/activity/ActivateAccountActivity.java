package com.example.olamundo.activity;

import java.util.LinkedList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.olamundo.R;
import com.example.olamundo.services.CreateDialog;
import com.example.olamundo.services.GlobalVariable;
import com.example.olamundo.services.JSONParser;

public class ActivateAccountActivity extends Activity {
	TextView headerTV;
	TextView infoTV;
	EditText activationCodeET;
	String activationCodeString;
	Intent intent;
	Typeface typeface;
	Typeface typeface2;
	Typeface typeface3;
	JSONParser jParser;
	JSONObject jsonObject;
	JSONObject jsonObjectFromServer;
	List<NameValuePair> params;
	AlertDialog alertDialog;
	AlertDialog.Builder ab;
	CreateDialog createDialog;
	String email;

	public void findThings() {
		intent = getIntent();
		infoTV = (TextView) findViewById(R.id.infoTextView);
		headerTV = (TextView) findViewById(R.id.header_textView);
		activationCodeET = (EditText) findViewById(R.id.activation_code_editText);
	}

	public void initialVisibilityOfViews() {
		email = intent.getStringExtra("email");
		infoTV.setText(GlobalVariable.ACTIVATION_MSG_HEADER + email
				+ GlobalVariable.ACTIVATION_MSG_FOOTER);
		headerTV.setTypeface(typeface);

		infoTV.setTypeface(typeface2);
		activationCodeET.setTypeface(typeface2);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activate_account);
		overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
		jParser = new JSONParser();
		typeface = Typeface.createFromAsset(getAssets(),
				"Fonts/Museo700-Regular.otf");
		typeface2 = Typeface.createFromAsset(getAssets(),
				"Fonts/Museo500-Regular.otf");
		createDialog = new CreateDialog(this);
		findThings();
		initialVisibilityOfViews();

		((LinearLayout) findViewById(R.id.activate_account_ll))
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

	@SuppressLint("NewApi")
	private boolean validate() {
		boolean temp = true;
		activationCodeET.setBackground(getResources().getDrawable(
				R.drawable.layer_list_blue));
		activationCodeString = activationCodeET.getText().toString();
		int length = activationCodeString.length();
		if (length == 0) {
			activationCodeET.setError("You must enter verification code.");
			activationCodeET.setBackground(getResources().getDrawable(
					R.drawable.layer_list_red));
			temp = false;

		} else if (length < 4) {
			activationCodeET.setError("Verification Code must be of 4 digits.");
			activationCodeET.setBackground(getResources().getDrawable(
					R.drawable.layer_list_red));
			temp = false;
		}
		return temp;
	}

	public void onBack(View view) {
		finish();
		overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
	}

	public void onNext(View view) {
		if (!validate())
			return;

		try {

			jsonObject = new JSONObject().put("verification_code",
					activationCodeString);

			params = new LinkedList<NameValuePair>();
			params.add(new BasicNameValuePair("verification_code",
					activationCodeString));

			ActivateAccountAsyncTask activateAccountAsyncTask = new ActivateAccountAsyncTask();
			activateAccountAsyncTask.execute(new String[] { "hello world" });

		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	private class ActivateAccountAsyncTask extends
			AsyncTask<String, Void, Void> {
		public void postMethod2() {
			String url = GlobalVariable.URL + GlobalVariable.ACTIVATE_ACCOUNT
					+ ".json";
			System.out.println("url activation is   : " + url);

			jsonObjectFromServer = jParser.getJSONObjectFromUrlAfterHttpGet(
					url, params);
			System.out
					.println("length is  :  " + jsonObjectFromServer.length());

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
			System.out.println("postExecute");
			if (jsonObjectFromServer.has("errors")) {
				System.out.println("Incorrect code");
				LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				View layout = inflater.inflate(R.layout.dialog_layout,
						(ViewGroup) findViewById(R.id.dialog_layout_LL));

				ab = createDialog
						.createAlertDialog(
								"Wrong Code",
								"You have entered a wrong activation code. Please verify the code and try again",
								false);
				ab.setView(layout);
				ab.setPositiveButton("OK",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								dialog.dismiss();
								// finish();
							}
						});
				// ab.set
				alertDialog = ab.create();
				alertDialog.show();
			} else {
				Toast.makeText(ActivateAccountActivity.this,
						"Email : " + email + " registered successfully",
						Toast.LENGTH_LONG).show();
				startActivity(new Intent(ActivateAccountActivity.this,
						BuyOlaActivity.class));
			}
		}

	}

	private class ReSendActivationCodeAsyncTask extends
			AsyncTask<String, Void, Void> {
		public void postMethod2() {
			String url = GlobalVariable.URL
					+ GlobalVariable.RESEND_VERIFICATION_CODE + ".json";
			System.out.println("url activation is   : " + url);

			params = new LinkedList<NameValuePair>();
			params.add(new BasicNameValuePair("email", intent
					.getStringExtra("email")));
			jsonObjectFromServer = jParser.getJSONObjectFromUrlAfterHttpGet(
					url, params);
			System.out
					.println("length is  :  " + jsonObjectFromServer.length());

		}

		@Override
		protected Void doInBackground(String... params) {
			// try {
			postMethod2();
			// System.out.println("doInBackground done");
			// } catch (Exception e) {
			// e.printStackTrace();
			// }
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			// super.onPostExecute(result);
			System.out.println("postExecute");
			if (jsonObjectFromServer.has("errors")) {
				System.out.println("Problem resending code");
			} else {
				Toast.makeText(ActivateAccountActivity.this,
						"Email has been sent to : " + email, Toast.LENGTH_SHORT)
						.show();
			}
		}

	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View view,
			ContextMenuInfo menuInfo) {
		System.out.println("context");
		super.onCreateContextMenu(menu, view, menuInfo);
		// if (view.getId() == R.id.uploadImageCircle) {
		getMenuInflater().inflate(R.menu.context_menu_forgot_pass, menu);
		// }
		menu.setHeaderTitle("Code Options");

	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.item1:
			Toast.makeText(this, "delete picture", Toast.LENGTH_SHORT).show();
			return true;
		case R.id.item2:
			System.out.println("Gallery Picture");

			return true;

		default:
			return false;
		}
	}

	public void onReEnterEmail(View view) {
		Toast.makeText(this, "Re-Enter Email", Toast.LENGTH_SHORT).show();
		alertDialog.dismiss();
		finish();
		overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
	}

	public void onReSendCode(View view) {
		// Toast.makeText(this, "Re Send Code", Toast.LENGTH_SHORT).show();
		alertDialog.dismiss();
		ReSendActivationCodeAsyncTask reSendCodeAsyncTask = new ReSendActivationCodeAsyncTask();
		reSendCodeAsyncTask.execute(new String[] { "hello world" });

	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
	}

}
