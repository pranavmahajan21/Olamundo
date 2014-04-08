package com.example.olamundo.activity;

import java.io.File;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.olamundo.R;
import com.example.olamundo.services.CreateDialog;
import com.example.olamundo.services.GlobalVariable;
import com.example.olamundo.services.JSONParser;
import com.example.olamundo.services.RoundedImageView;

public class CreateAccountActivity extends Activity {
	GlobalVariable globalVariable;
	static boolean DELETE_BUTTON_HIDER = true;
	int hintFontSize = 14;
	int textFontSize = 20;
	private static final int CAMERA_REQUEST_CODE = 100;
	private static final int SELECT_PICTURE_FROM_GALLERY = 1;
	public static final int MEDIA_TYPE_IMAGE = 1;
	public static final int MEDIA_TYPE_VIDEO = 2;
	// private static final String IMAGE_DIRECTORY_NAME = "Hello Camera";
	private Uri fileUri;

	ImageButton clearFullNameImageButton;
	ImageButton clearNickNameImageButton;
	ImageButton clearEmailImageButton;
	ImageButton clearPasswordImageButton;
	ImageButton circleImageButton;
	ImageView previewUploadedImageIV;
	// RoundedImageView previewUploadedImage2;
	EditText fullnameEditText;
	EditText nicknameEditText;
	EditText emailEditText;
	EditText passwordEditText;

	TextView headerTV;
	TextView headerRequiredTV;
	// header_required
	RelativeLayout fullnameRL;
	RelativeLayout nicknameRL;
	RelativeLayout emailRL;
	RelativeLayout passwordRL;

	Typeface typeface;
	JSONParser jParser;
	JSONObject jsonObjectFromServer;
	Bitmap bitmap;

	public void findThings() {
		clearFullNameImageButton = (ImageButton) findViewById(R.id.clear_fullname_imageButton);
		clearNickNameImageButton = (ImageButton) findViewById(R.id.clear_nickname_imageButton);
		clearEmailImageButton = (ImageButton) findViewById(R.id.clear_email_imageButton);
		clearPasswordImageButton = (ImageButton) findViewById(R.id.clear_password_imageButton);
		circleImageButton = (ImageButton) findViewById(R.id.uploadImageCircle);
		previewUploadedImageIV = (ImageView) findViewById(R.id.preview_uploaded_image);
		// previewUploadedImage2 = new RoundedImageView(this);
		fullnameEditText = (EditText) findViewById(R.id.fullname_edittext);
		nicknameEditText = (EditText) findViewById(R.id.nickname_edittext);
		emailEditText = (EditText) findViewById(R.id.email_edittext);
		passwordEditText = (EditText) findViewById(R.id.password_edittext);
		headerTV = (TextView) findViewById(R.id.header_textView);
		fullnameRL = (RelativeLayout) findViewById(R.id.fullname_RL);
		nicknameRL = (RelativeLayout) findViewById(R.id.nickname_RL);
		emailRL = (RelativeLayout) findViewById(R.id.email_RL);
		passwordRL = (RelativeLayout) findViewById(R.id.password_RL);
		headerRequiredTV = (TextView) findViewById(R.id.header_required_textView);
	}

	public void initialVisibilityOfViews() {
		clearFullNameImageButton.setVisibility(View.INVISIBLE);
		clearNickNameImageButton.setVisibility(View.INVISIBLE);
		clearEmailImageButton.setVisibility(View.INVISIBLE);
		clearPasswordImageButton.setVisibility(View.INVISIBLE);

		fullnameEditText.setTypeface(typeface);
		fullnameEditText.setTextSize(TypedValue.COMPLEX_UNIT_SP, hintFontSize);
		nicknameEditText.setTypeface(typeface);
		nicknameEditText.setTextSize(TypedValue.COMPLEX_UNIT_SP, hintFontSize);
		emailEditText.setTypeface(typeface);
		emailEditText.setTextSize(TypedValue.COMPLEX_UNIT_SP, hintFontSize);
		passwordEditText.setTypeface(typeface);
		passwordEditText.setTextSize(TypedValue.COMPLEX_UNIT_SP, hintFontSize);

		headerTV.setTypeface(typeface);
	}

	public void myOwnTextChangeListeners() {
		fullnameEditText.addTextChangedListener(new TextWatcher() {
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
				if (fullnameEditText.getText().toString().length() > 0) {
					clearFullNameImageButton.setVisibility(View.VISIBLE);
					fullnameEditText.setTextSize(TypedValue.COMPLEX_UNIT_SP,
							textFontSize);
				} else {
					clearFullNameImageButton.setVisibility(View.INVISIBLE);
					fullnameEditText.setTextSize(TypedValue.COMPLEX_UNIT_SP,
							hintFontSize);
				}
			}
		});
		nicknameEditText.addTextChangedListener(new TextWatcher() {
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
				if (nicknameEditText.getText().toString().length() > 0) {
					clearNickNameImageButton.setVisibility(View.VISIBLE);
					nicknameEditText.setTextSize(TypedValue.COMPLEX_UNIT_SP,
							textFontSize);
				} else {
					clearNickNameImageButton.setVisibility(View.INVISIBLE);
					nicknameEditText.setTextSize(TypedValue.COMPLEX_UNIT_SP,
							hintFontSize);
				}
			}
		});
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
				if (emailEditText.getText().toString().length() > 0) {
					clearEmailImageButton.setVisibility(View.VISIBLE);
					emailEditText.setTextSize(TypedValue.COMPLEX_UNIT_SP,
							textFontSize);
				} else {
					clearEmailImageButton.setVisibility(View.INVISIBLE);
					emailEditText.setTextSize(TypedValue.COMPLEX_UNIT_SP,
							hintFontSize);
				}
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
				if (passwordEditText.getText().toString().length() > 0) {
					clearPasswordImageButton.setVisibility(View.VISIBLE);
					passwordEditText.setTextSize(TypedValue.COMPLEX_UNIT_SP,
							textFontSize);
				} else {
					clearPasswordImageButton.setVisibility(View.INVISIBLE);
					passwordEditText.setTextSize(TypedValue.COMPLEX_UNIT_SP,
							hintFontSize);
				}
			}
		});

	}

	public void onClearText(View view) {
		System.out.println("onClearText");
		int temp = ((ImageButton) view).getId();

		if (temp == R.id.clear_fullname_imageButton)
			fullnameEditText.setText("");
		else if (temp == R.id.clear_nickname_imageButton)
			nicknameEditText.setText("");
		else if (temp == R.id.clear_email_imageButton)
			emailEditText.setText("");
		else if (temp == R.id.clear_password_imageButton)
			passwordEditText.setText("");
	}

	@SuppressLint("NewApi")
	public boolean validate() {
		boolean temp = true;
		fullnameRL.setBackground(getResources().getDrawable(
				R.drawable.layer_list_blue));
		nicknameRL.setBackground(getResources().getDrawable(
				R.drawable.layer_list_blue));
		emailRL.setBackground(getResources().getDrawable(
				R.drawable.layer_list_blue));
		passwordRL.setBackground(getResources().getDrawable(
				R.drawable.layer_list_blue));
		circleImageButton.setImageDrawable(getResources().getDrawable(
				R.drawable.circle_blue));
		if (fullnameEditText.getText().toString().trim().length() == 0) {
			// fullnameEditText.setError("You must enter your fullname.");
			fullnameRL.setBackground(getResources().getDrawable(
					R.drawable.custom_background_redbox));

			temp = false;
		}
		if (nicknameEditText.getText().toString().trim().length() == 0) {
			// nicknameEditText.setError("You must enter your nickname.");
			nicknameRL.setBackground(getResources().getDrawable(
					R.drawable.custom_background_redbox));
			temp = false;
		}
		if (emailEditText.getText().toString().trim().length() == 0) {
			// emailEditText.setError("You must enter your Email ID.");
			emailRL.setBackground(getResources().getDrawable(
					R.drawable.custom_background_redbox));
			temp = false;
		}
		if (passwordEditText.getText().toString().length() == 0) {
			// passwordEditText.setError("You must enter a password.");
			passwordRL.setBackground(getResources().getDrawable(
					R.drawable.custom_background_redbox));
			temp = false;
		}
		if (DELETE_BUTTON_HIDER == true) {
			circleImageButton.setImageDrawable(getResources().getDrawable(
					R.drawable.circle_red));
			temp = false;
		}
		return temp;
	}

	// public byte[] getBytesFromBitmap(Bitmap bitmap) {
	// ByteArrayOutputStream stream = new ByteArrayOutputStream();
	// bitmap.compress(CompressFormat.JPEG, 70, stream);
	// return stream.toByteArray();
	// }

	private class CreateAccountAsync extends AsyncTask<String, Void, Void> {
		public void postMethod2() {
			String url = GlobalVariable.URL + GlobalVariable.REGISTER_USER
					+ ".json";
			JSONObject jsonObjectTopLevel = null;
			try {
				JSONObject jsonObjectLevel1 = new JSONObject()
						.put("name", nicknameEditText.getText().toString())
						.put("first_name",
								fullnameEditText.getText().toString())
						.put("relationship", "Admin");

				JSONObject jsonObjectLevel2 = new JSONObject().put("0",
						jsonObjectLevel1);
				jsonObjectTopLevel = new JSONObject()
						.put("email", emailEditText.getText().toString())
						.put("password", passwordEditText.getText().toString())
						.put("password_confirmation",
								passwordEditText.getText().toString())
						.put("members_attributes", jsonObjectLevel2);

			} catch (JSONException e) {
				e.printStackTrace();
			}
			// File file = new File(fileUri.getPath());
			System.out.println("File uri is :   " + fileUri + "   "
					+ fileUri.getPath());
			jsonObjectFromServer = jParser.getJSONFromUrlAfterHttpPost2(url,
					jsonObjectTopLevel, new File(fileUri.getPath()),
					"user[members_attributes][0][avatar]", fileUri);

			if (jsonObjectFromServer == null)
				System.out.println("im null");
			else
				System.out.println("im not null");

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
			if (jsonObjectFromServer.has("email")) {
//				System.out.println("email already registered");
				ab = createDialog.createAlertDialog("Oops",
						"Email already registered. Choose another and try to login", false);
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
				Intent intent = new Intent(CreateAccountActivity.this,
						ActivateAccountActivity.class);
				intent.putExtra("email", emailEditText.getText().toString());
				startActivity(intent);
			}
		}

	}

	AlertDialog alertDialog;
	AlertDialog.Builder ab;

	public void onNext(View view) {
		
		
		// System.out.println("next");
		if (!validate())
			return;

		CreateAccountAsync createAccountAsync = new CreateAccountAsync();
		createAccountAsync.execute(new String[] { "hello world" });

	}

	public void onBack(View view) {
		System.out.println("onBack");
		finish();
		overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
	}

	public void onUploadImage(View view) {
		System.out.println("onUploadImage");
		openContextMenu(view);

	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View view,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, view, menuInfo);
		if (view.getId() == R.id.uploadImageCircle) {
			getMenuInflater().inflate(R.menu.context_menu_select_image, menu);
		}
		menu.setHeaderTitle("Picture Options");

		MenuItem item1 = menu.getItem(0);
		if (DELETE_BUTTON_HIDER == true)
			item1.setVisible(false);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.item1:
			Toast.makeText(this, "delete picture", Toast.LENGTH_SHORT).show();
			previewUploadedImageIV.setImageDrawable(getResources().getDrawable(
					R.drawable.camera));
			headerRequiredTV.setVisibility(View.VISIBLE);
			DELETE_BUTTON_HIDER = true;
			return true;
		case R.id.item2:
			System.out.println("Gallery Picture");
			Intent intentGallery = new Intent();
			intentGallery.setType("image/*");
			intentGallery.setAction(Intent.ACTION_GET_CONTENT);
			startActivityForResult(
					Intent.createChooser(intentGallery, "Select Picture"),
					SELECT_PICTURE_FROM_GALLERY);

			return true;
		case R.id.item3:
			Toast.makeText(this, "take picture", Toast.LENGTH_SHORT).show();
			fileUri = globalVariable.getOutputMediaFileUri(MEDIA_TYPE_IMAGE);

			Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
			startActivityForResult(intentCamera, CAMERA_REQUEST_CODE);
			return true;
		case R.id.item4:
			Toast.makeText(this, "choose avatar", Toast.LENGTH_SHORT).show();
			return true;
		default:
			return false;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {
		// if the result is capturing Image
		if (requestCode == CAMERA_REQUEST_CODE) {
			if (resultCode == RESULT_OK) {
				// successfully captured the image

				bitmap = globalVariable.previewCapturedImageInImageView(
						fileUri, previewUploadedImageIV);
				DELETE_BUTTON_HIDER = false;
				headerRequiredTV.setVisibility(View.GONE);
				circleImageButton.setImageDrawable(getResources().getDrawable(
						R.drawable.circle_blue));
			} else if (resultCode == RESULT_CANCELED) {
				// user cancelled Image capture
				Toast.makeText(getApplicationContext(),
						"User cancelled image capture", Toast.LENGTH_SHORT)
						.show();
			} else {
				// failed to capture image
				Toast.makeText(getApplicationContext(),
						"Sorry! Failed to capture image", Toast.LENGTH_SHORT)
						.show();
			}
		}
		if (requestCode == SELECT_PICTURE_FROM_GALLERY) {
			if (resultCode == RESULT_OK) {
				bitmap = globalVariable.getImageFromGallery(intent);
				fileUri = intent.getData();
				if (bitmap == null)
					System.out.println("bitmap is null");

				// globalVariable.previewCapturedImageInImageView(fileUri,
				// previewUploadedImage);
				// or
				previewUploadedImageIV.setImageBitmap(RoundedImageView
						.getCroppedBitmap(bitmap, 140));

				DELETE_BUTTON_HIDER = false;
				headerRequiredTV.setVisibility(View.GONE);
				circleImageButton.setImageDrawable(getResources().getDrawable(
						R.drawable.circle_blue));
			}
		}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);

		// save file url in bundle as it will be null on screen orientation
		// changes
		outState.putParcelable("file_uri", fileUri);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		// get the file url
		fileUri = savedInstanceState.getParcelable("file_uri");
	}

	CreateDialog createDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.create_account);
		overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
		typeface = Typeface.createFromAsset(getAssets(),
				"Fonts/Museo_Slab_500.otf");
		jParser = new JSONParser(this);
		createDialog = new CreateDialog(this);
		findThings();
		globalVariable = (GlobalVariable) getApplicationContext();
		initialVisibilityOfViews();
		myOwnTextChangeListeners();
		staticNonsense();

		((LinearLayout) findViewById(R.id.create_account_ll))
				.setOnTouchListener(new OnTouchListener() {
					@Override
					public boolean onTouch(View v, MotionEvent event) {
						InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
						imm.hideSoftInputFromWindow(getCurrentFocus()
								.getWindowToken(), 0);
						return false;
					}
				});

		registerForContextMenu(circleImageButton);
	}

	private void staticNonsense() {
		fullnameEditText.setText("Sharath");
		nicknameEditText.setText("Mr. Sharuu");
		emailEditText.setText("pranavmahajan21@gmail.com");
		passwordEditText.setText("aaaaaaaa");
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
	}

	
	// private void previewCapturedImage() {
	// try {
	// // hide video preview
	//
	// BitmapFactory.Options options = new BitmapFactory.Options();
	//
	// // downsizing image as it throws OutOfMemory Exception for larger
	// // images
	// options.inSampleSize = 8;
	//
	// final Bitmap bitmap = BitmapFactory.decodeFile(fileUri.getPath(),
	// options);
	// previewUploadedImage.setImageBitmap(RoundedImageView
	// .getCroppedBitmap(bitmap, 160));
	//
	// } catch (NullPointerException e) {
	// Toast.makeText(this, "null pointer", Toast.LENGTH_SHORT).show();
	// e.printStackTrace();
	// }
	// }

	// public Uri getOutputMediaFileUri(int type) {
	// return Uri.fromFile(getOutputMediaFile(type));
	// }

	// /*
	// * returning image / video
	// */
	// private static File getOutputMediaFile(int type) {
	//
	// // External sdcard location
	// File mediaStorageDir = new File(
	// Environment
	// .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
	// IMAGE_DIRECTORY_NAME);
	//
	// // Create the storage directory if it does not exist
	// if (!mediaStorageDir.exists()) {
	// if (!mediaStorageDir.mkdirs()) {
	// Log.d(IMAGE_DIRECTORY_NAME, "Oops! Failed create "
	// + IMAGE_DIRECTORY_NAME + " directory");
	// return null;
	// }
	// }
	//
	// // Create a media file name
	// String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
	// Locale.getDefault()).format(new Date());
	// File mediaFile;
	// if (type == MEDIA_TYPE_IMAGE) {
	// mediaFile = new File(mediaStorageDir.getPath() + File.separator
	// + "IMG_" + timeStamp + ".jpg");
	// } else if (type == MEDIA_TYPE_VIDEO) {
	// mediaFile = new File(mediaStorageDir.getPath() + File.separator
	// + "VID_" + timeStamp + ".mp4");
	// } else {
	// return null;
	// }
	//
	// return mediaFile;
	// }

}
