package com.example.olamundo.activity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
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
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.olamundo.R;
import com.example.olamundo.models.CategorySymbols;
import com.example.olamundo.models.Members;
import com.example.olamundo.models.RelatedSymbols;
import com.example.olamundo.models.Symbols;
import com.example.olamundo.services.GlobalVariable;
import com.example.olamundo.services.JSONParser;
import com.example.olamundo.services.RoundedImageView;

public class ChatPageActivity extends Activity {
	RelativeLayout chatPageRL;
//	EditText chatContentEditText;
//	TextView chatPersonTextView;
	ListView chatPageListView;
	ImageView previewImage;
//	ImageView previewImage2;
	ImageView chatbubbleInnerShadowLeftIV;
	ImageView chatbubbleInnerShadowRightIV;
	HorizontalScrollView chatMessageHSV;
	Members ownProfile;
	Members chatPerson;
	Intent intent;
	JSONObject json;
	JSONParser jParser;
	GlobalVariable globalVariable;

	private void findThings() {
		chatPageRL = (RelativeLayout) findViewById(R.id.chat_page);
//		chatContentEditText = (EditText) findViewById(R.id.chat_content_editText);
		chatPageListView = (ListView) findViewById(R.id.chat_page_listView);
		previewImage = (ImageView) findViewById(R.id.preview_image);
//		previewImage2 = (ImageView) findViewById(R.id.preview_image2);
//		chatbubbleInnerShadowLeftIV = (ImageView) findViewById(R.id.chatbubble_inner_shadow_left_image_view);
//		chatbubbleInnerShadowRightIV = (ImageView) findViewById(R.id.chatbubble_inner_shadow_right_image_view);
		chatMessageHSV = (HorizontalScrollView) findViewById(R.id.chat_message_HSV);
//		chatPersonTextView = (TextView) findViewById(R.id.chat_person);
	}

	private void initialVisibilityOfViews() {
//		chatbubbleInnerShadowLeftIV.setVisibility(View.INVISIBLE);
//		chatbubbleInnerShadowRightIV.setVisibility(View.INVISIBLE);
//		chatContentEditText.setFocusable(true);
//		chatPersonTextView.setText(chatPerson.getName());
		previewImage.setImageBitmap(RoundedImageView.getCroppedBitmap(
				chatPerson.getBitmap(), 30));
//		previewImage2.setImageBitmap(RoundedImageView.getCroppedBitmap(
//				ownProfile.getBitmap(), 30));
	}

	public void myOwnTextChangeListeners() {
//		chatContentEditText.addTextChangedListener(new TextWatcher() {
//			@Override
//			public void onTextChanged(CharSequence s, int start, int before,
//					int count) {
//			}
//
//			@Override
//			public void beforeTextChanged(CharSequence s, int start, int count,
//					int after) {
//			}
//
//			@Override
//			public void afterTextChanged(Editable s) {
//				// server get call
//				AsyncExample asyncExample = new AsyncExample();
//				asyncExample.execute(new String[] { "hello world" });
//			}
//		});
	}

	private void initializeVariables() {
		jParser = new JSONParser();
		globalVariable = (GlobalVariable) getApplicationContext();
		intent = getIntent();
		chatPerson = globalVariable.getLoginDetails().getMembers()
				.get(intent.getIntExtra("chat_person_position", 0));
		ownProfile = globalVariable.getLoginDetails().getMembers()
				.get(intent.getIntExtra("own_profile_position", 0));
	}

	List<RelatedSymbols> relatedSymbols;
	List<Symbols> symbols;

	private void createObjects(JSONArray jsonArrayOfCategorySymbols) {
		List<CategorySymbols> categorySymbols = new ArrayList<CategorySymbols>();

		for (int i = 0; i < jsonArrayOfCategorySymbols.length(); i++) {
			CategorySymbols tempCategorySymbols = new CategorySymbols();
			try {
				JSONObject jsonObject = jsonArrayOfCategorySymbols.getJSONObject(i);
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
							for (int iii = 0; iii < tempJsonArrayOfRelatedSymbols.length(); iii++) {
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

	private class AsyncExample extends AsyncTask<String, Void, Void> {
		public void postMethod2() {
			// String url = "http://192.168.102.110:3000/users/sign_in.json";
			String url = GlobalVariable.URL + GlobalVariable.CATEGORY_SYMBOL
					+ ".json";
			System.out.println("url is   : " + url);

			// json
			JSONArray jsonArray = jParser.getJSONArrayFromUrlAfterHttpGet(url);
			System.out.println("length is  :  " + jsonArray.length());
			createObjects(jsonArray);
			// json.get

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
		}

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.chat_page);

		initializeVariables();
		findThings();
		initialVisibilityOfViews();
		myOwnTextChangeListeners();
		chatPageRL.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
						0);
				return false;
			}
		});
	}

}
