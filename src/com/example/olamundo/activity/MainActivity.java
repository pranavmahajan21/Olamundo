package com.example.olamundo.activity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

import com.example.olamundo.models.CategorySymbols;
import com.example.olamundo.models.LoginDetails;
import com.example.olamundo.models.RelatedSymbols;
import com.example.olamundo.models.Symbols;
import com.example.olamundo.services.GlobalVariable;
import com.example.olamundo.services.JSONParser;
import com.google.gson.Gson;

public class MainActivity extends Activity {
	static int LOGIN = 1;
	static int PROFILE_SELECTED = 1;
	Intent realAppIntent;
	SharedPreferences preferences;
	SharedPreferences.Editor editor;
	JSONParser jParser;
	List<RelatedSymbols> relatedSymbols;
	List<Symbols> symbols;
	List<CategorySymbols> categorySymbols;
	GlobalVariable globalVariable;
	Gson gson;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		System.out.println("onCreate MainAct");
		jParser = new JSONParser();
		gson = new Gson();
		globalVariable = (GlobalVariable) getApplicationContext();

		preferences = getSharedPreferences("hello", MODE_PRIVATE);
		editor = preferences.edit();

		// if( !(preferences.getBoolean("isAppDataPresent", false)) )
		// {
		// // read data from file
		// }
		// else
		// getAppData();

		// remove next line
		// editor.putBoolean("isLogin", false);
		
		realAppIntent = new Intent(this, LogoutPageActivity.class);
		check();
		redirect();
	}

	public void redirect() {
		if (preferences.getBoolean("isLogin", false)
				&& preferences.contains("login_details")) {

			globalVariable.setLoginDetails(gson.fromJson(
					preferences.getString("login_details", null),
					LoginDetails.class));

			if (preferences.getBoolean("own_profile_selected", false)) {
				startActivityForResult((new Intent(this,
						TheRealAppActivity.class)), LOGIN);
			} else
				startActivityForResult((new Intent(this,
						SelectOwnProfileActivity.class)), LOGIN);
		} else {
			startActivity(new Intent(this, LoginPageActivity.class));
		}

		// startActivity(new Intent(this, LoginPageActivity.class));
	}

	public void check() {
		System.out.println("name :"
				+ preferences.getString("profile_name", null) + "\nislogin  : "
				+ preferences.getBoolean("isLogin", false));
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		System.out.println("onActivityResult MainActi");
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	protected void onResume() {
		System.out.println("onResume MainAct");
		super.onResume();
		check();
		redirect();
	}

	private void getAppData() {
		KeyboardAsyncTask keyboardAsyncTask = new KeyboardAsyncTask();
		keyboardAsyncTask.execute(new String[] { "hello world" });
	}

	private class KeyboardAsyncTask extends AsyncTask<String, Void, Void> {
		public void postMethod2() {
			// String url = "http://192.168.102.110:3000/users/sign_in.json";
			String url = GlobalVariable.URL + GlobalVariable.CATEGORY_SYMBOL
					+ ".json";
			System.out.println("url is   : " + url);

			JSONArray jsonArray = jParser.getJSONArrayFromUrlAfterHttpGet(url);
			System.out.println("length is  :  " + jsonArray.length());
			createObjects(jsonArray);

			// write data to file
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
			editor.putBoolean("isAppDataPresent", true);
			editor.commit();
		}

	}

	private void createObjects(JSONArray jsonArrayOfCategorySymbols) {
		categorySymbols = new ArrayList<CategorySymbols>();

		for (int i = 0; i < jsonArrayOfCategorySymbols.length(); i++) {
			CategorySymbols tempCategorySymbols = new CategorySymbols();
			try {
				JSONObject jsonObject = jsonArrayOfCategorySymbols
						.getJSONObject(i);
				tempCategorySymbols.setBackgroungColor(jsonObject
						.getString(GlobalVariable.BACKGROUND_COLOR));
				tempCategorySymbols.setCategoryImageFileName(jsonObject
						.getString(GlobalVariable.CATEGORY_IMAGE_FILE_NAME));
				tempCategorySymbols.setCategoryLevel(jsonObject
						.getInt(GlobalVariable.CATEGORY_LEVEL));
				tempCategorySymbols.setCategoryName(jsonObject
						.getString(GlobalVariable.CATEGORY_NAME));
				tempCategorySymbols.setHebrew(jsonObject
						.getString(GlobalVariable.HEBREW));
				tempCategorySymbols.setSequence(jsonObject
						.getInt(GlobalVariable.SEQUENCE));
				JSONArray tempJsonArrayOfSymbols = jsonObject
						.getJSONArray(GlobalVariable.SYMBOLS_WITH_SEQUENCE);
				{
					symbols = new ArrayList<Symbols>();
					for (int ii = 0; ii < tempJsonArrayOfSymbols.length(); ii++) {
						JSONObject symbolsJsonObject = tempJsonArrayOfSymbols
								.getJSONObject(ii);
						Symbols tempSymbol = new Symbols();

						tempSymbol.setId(symbolsJsonObject
								.getInt(GlobalVariable.ID));
						tempSymbol.setSymbolType((symbolsJsonObject
								.getString(GlobalVariable.SYMBOL_TYPE)));
						tempSymbol.setSymbolLevel((symbolsJsonObject
								.getInt(GlobalVariable.SYMBOL_LEVEL)));
						tempSymbol.setWord((symbolsJsonObject
								.getString(GlobalVariable.WORD)));
						JSONArray tempJsonArrayOfRelatedSymbols = symbolsJsonObject
								.getJSONArray(GlobalVariable.RELATED_SYMBOLS_WITH_SEQUENCE);
						{
							relatedSymbols = new ArrayList<RelatedSymbols>();
							for (int iii = 0; iii < tempJsonArrayOfRelatedSymbols
									.length(); iii++) {
								JSONObject relatedSymbolsJsonObject = tempJsonArrayOfRelatedSymbols
										.getJSONObject(iii);
								RelatedSymbols tempRelatedSymbols = new RelatedSymbols();
								tempRelatedSymbols
										.setHebrew(relatedSymbolsJsonObject
												.getString(GlobalVariable.HEBREW));
								tempRelatedSymbols
										.setId(relatedSymbolsJsonObject
												.getInt(GlobalVariable.ID));
								tempRelatedSymbols
										.setImageUrl(relatedSymbolsJsonObject
												.getString(GlobalVariable.IMAGE_URL));
								tempRelatedSymbols
										.setSequence(relatedSymbolsJsonObject
												.getInt(GlobalVariable.SEQUENCE));
								tempRelatedSymbols
										.setSymbolImageFileName(relatedSymbolsJsonObject
												.getString(GlobalVariable.SYMBOL_IMAGE_FILE_NAME));
								tempRelatedSymbols
										.setSymbolText(relatedSymbolsJsonObject
												.getString(GlobalVariable.SYMBOL_TEXT));
								relatedSymbols.add(tempRelatedSymbols);
							}
						}
						tempSymbol.setRelatedSymbols(relatedSymbols);
						symbols.add(tempSymbol);
					}
				}
				tempCategorySymbols.setSymbols(symbols);
				categorySymbols.add(tempCategorySymbols);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		globalVariable.setCategorySymbols(categorySymbols);
	}

}
