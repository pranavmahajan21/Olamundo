package com.example.olamundo.activity;

import com.example.olamundo.R;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class LogoutPageActivity extends Activity {
	ImageView imageView;
	SharedPreferences preferences;
	SharedPreferences.Editor editor;
	Intent intent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		System.out.println("onCreate LogoutAct");
		setContentView(R.layout.logout_page);
		preferences = getSharedPreferences("hello", MODE_PRIVATE);
		editor = preferences.edit();
		intent = getIntent();
		((TextView) findViewById(R.id.profile_name)).setText(preferences
				.getString("profile_name", "Something's Wrong"));
	}

	public void onLogout(View view)
	{
		editor.clear();
		editor.commit();
//		setResult(RESULT_OK, intent);
		finish();
	}

	public void onChangeProfile(View view) {
		editor.remove("profile_name");
		editor.commit();
		System.out.println(preferences
				.getString("profile_name", "expected result"));
		finish();
		System.out.println("finished");
	}

	@Override
	protected void onResume() {
		super.onResume();
	System.out.println("onResume LogoutAct");
	}

	
}
