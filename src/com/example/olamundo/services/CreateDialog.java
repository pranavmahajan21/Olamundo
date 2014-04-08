package com.example.olamundo.services;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;

public class CreateDialog {
	Context context;
	ProgressDialog progressDialog;

	public CreateDialog(Context context) {
		this.context = context;
	}

	public AlertDialog.Builder createAlertDialog(String title, String message,
			boolean cancel) {
		System.out.println("creating dialog");
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				context);
		if (title != null)
			alertDialogBuilder.setTitle(title);
		if (message != null)
			alertDialogBuilder.setMessage(message);

		alertDialogBuilder.setCancelable(cancel);

		return alertDialogBuilder;
	}

	public ProgressDialog createProgressDialog(String title, String message,
			boolean indeterminateState, Drawable drawable) {
		System.out.println("creating progress dialog");
		progressDialog = new ProgressDialog(context);
		if (title != null)
			progressDialog.setTitle(title);
		if (message != null)
			progressDialog.setMessage(message);

		progressDialog.setIndeterminate(indeterminateState);
		if (drawable != null)
			progressDialog.setIndeterminateDrawable(drawable);
		progressDialog.setCancelable(false);
		return progressDialog;
	}

}
