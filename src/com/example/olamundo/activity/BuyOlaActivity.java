package com.example.olamundo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.example.olamundo.R;

public class BuyOlaActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.buy_app);
	
	}

	 public void onBuyApp(View view)
	 {
		 Toast.makeText(this, "buy app", Toast.LENGTH_SHORT).show();
	 }
}
