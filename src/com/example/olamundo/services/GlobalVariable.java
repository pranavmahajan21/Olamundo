package com.example.olamundo.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.olamundo.models.CategorySymbols;
import com.example.olamundo.models.LoginDetails;
import com.example.olamundo.models.Members;

public class GlobalVariable extends Application {

//	public static final String URL = "http://192.168.102.124:3000";
	public static final String URL = "http://ola-mundo.cloudapp.net";
	
	public static final String SIGN_IN = "/users/sign_in";
	public static final String REGISTER_USER = "/users";
	public static final String CATEGORY_BANK = "/category_bank_list";
	public static final String CATEGORY_SYMBOL = "/categories_symbols";
	public static final String ACTIVATE_ACCOUNT = "/verify_account";
	public static final String FORGOT_PASSWORD = "/users/password";
	public static final String RESEND_VERIFICATION_CODE = "/resend_verification_code";
	public static final String ADD_EDIT_PROFILE = "/update_user_members";
	public static final String DELETE_PROFILE = "/delete_member_from_ipad";
	public static final String FETCH_MSGS = "/requesting_messages";
	// All model classes
	public static final String IMAGE_URL = "image_url";
	public static final String ID = "id";

	// LoginDetails & Members Model Class
	public static final String RELATIONSHIP = "relationship";
	public static final String FIRST_NAME = "first_name";
	public static final String NAME = "name";
	public static final String EMAIL = "email";
	public static final String SUCCESS = "success";
	public static final String PASSWORD = "password";
	public static final String MSG_COUNT = "msg_count";
	public static final String FREE_MSG_COUNT = "free_msg_count";

	// CategorySymbol Model Class
	public static final String BACKGROUND_COLOR = "background_color";
	public static final String CATEGORY_IMAGE_FILE_NAME = "category_image_file_name";
	public static final String CATEGORY_LEVEL = "category_level";
	public static final String CATEGORY_NAME = "category_name";
	public static final String HEBREW = "hebrew";
	public static final String SEQUENCE = "sequence";
	public static final String SYMBOLS_WITH_SEQUENCE = "symbols_with_sequence";

	// Symbols Model Class
	public static final String SYMBOL_IMAGE_FILE_NAME = "symbol_image_file_name";
	public static final String SYMBOL_TYPE = "symbol_type";
	public static final String SYMBOL_LEVEL = "symbol_level";
	public static final String RELATED_SYMBOLS_WITH_SEQUENCE = "related_symbols_with_sequence";
	public static final String WORD = "word";

	// RelatedSymbol Model Class
	public static final String SYMBOL_TEXT = "symbol_text";

	
	private static final String IMAGE_DIRECTORY_NAME = "Hello Camera";
	public static final int MEDIA_TYPE_IMAGE = 1;
	public static final int MEDIA_TYPE_VIDEO = 2;
	public static final String ACTIVATION_MSG_HEADER = "We have just sent an email to : ";
	public static final String ACTIVATION_MSG_FOOTER = " .\nPlease copy the 4 digit activation\ncode included in the email into the\nfield above and hit 'next'.";

	public static final String FREE_MSGS = "100 messages\nleft for this free account";
	// Typeface can't be declared here
	// Typeface typeface = Typeface.createFromAsset(getAssets(),
	// "Fonts/Rex Bold.otf");

	public static final String AVATAR_UPDATED_AT = "avatar_updated_at";

	public static final String LAST_NAME = "LASTNAME";


	LoginDetails loginDetails;
	List<CategorySymbols> categorySymbols;
	Members addedMember;

	public Bitmap previewCapturedImageInImageView(Uri fileUri,
			ImageView imageView) {
		try {
			// hide video preview

			BitmapFactory.Options options = new BitmapFactory.Options();

			// downsizing image as it throws OutOfMemory Exception for larger
			// images
			options.inSampleSize = 8;

			final Bitmap bitmap = BitmapFactory.decodeFile(fileUri.getPath(),
					options);
			imageView.setImageBitmap(RoundedImageView.getCroppedBitmap(bitmap,
					140));
			return bitmap;

		} catch (NullPointerException e) {
			Toast.makeText(this, "null pointer", Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		}
		return null;
	}

	public Uri getOutputMediaFileUri(int type) {
		return Uri.fromFile(getOutputMediaFile(type));
	}

	/*
	 * returning image / video
	 */
	private static File getOutputMediaFile(int type) {

		// External sdcard location
		File mediaStorageDir = new File(
				Environment
						.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
				IMAGE_DIRECTORY_NAME);

		// Create the storage directory if it does not exist
		if (!mediaStorageDir.exists()) {
			if (!mediaStorageDir.mkdirs()) {
				Log.d(IMAGE_DIRECTORY_NAME, "Oops! Failed create "
						+ IMAGE_DIRECTORY_NAME + " directory");
				return null;
			}
		}

		// Create a media file name
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
				Locale.getDefault()).format(new Date());
		File mediaFile;
		if (type == MEDIA_TYPE_IMAGE) {
			mediaFile = new File(mediaStorageDir.getPath() + File.separator
					+ "IMG_" + timeStamp + ".jpg");
		} else if (type == MEDIA_TYPE_VIDEO) {
			mediaFile = new File(mediaStorageDir.getPath() + File.separator
					+ "VID_" + timeStamp + ".mp4");
		} else {
			return null;
		}

		return mediaFile;
	}

	public Bitmap getImageFromGallery(Intent intent) {
		if (intent == null)
			System.out.println("is null");
		Uri selectedImage = intent.getData();
		if (selectedImage == null)
			System.out.println("uri is null");

		// String[] filePathColumn = {MediaStore.Images.Media.DATA};

		// Cursor cursor = getContentResolver().query(
		// selectedImage, filePathColumn, null, null, null);
		// if(cursor == null)
		// System.out.println("cursor is null");
		//
		// cursor.moveToFirst();
		//
		// int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
		// String filePath = cursor.getString(columnIndex);
		// cursor.close();
		//
		// System.out.println("check");
		// Bitmap yourSelectedImage = BitmapFactory.decodeFile(filePath);

		Bitmap yourSelectedBitmap = null;
		try {
			yourSelectedBitmap = MediaStore.Images.Media.getBitmap(
					this.getContentResolver(), selectedImage);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return yourSelectedBitmap;
	}

	@SuppressLint("NewApi")
	public Bitmap getBitmapFromDrawable(Drawable drawable) {
		if (drawable instanceof BitmapDrawable) {
			return ((BitmapDrawable) drawable).getBitmap();
		}

		Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
				drawable.getIntrinsicHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);
		drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
		drawable.draw(canvas);
		return bitmap;
	}

	public LoginDetails getLoginDetails() {
		return loginDetails;
	}

	public void setLoginDetails(LoginDetails loginDetails) {
		this.loginDetails = loginDetails;
	}

	public List<CategorySymbols> getCategorySymbols() {
		return categorySymbols;
	}

	public void setCategorySymbols(List<CategorySymbols> categorySymbols) {
		this.categorySymbols = categorySymbols;
	}

	public Members getAddedMember() {
		return addedMember;
	}

	public void setAddedMember(Members addedMember) {
		this.addedMember = addedMember;
	}

	
}
