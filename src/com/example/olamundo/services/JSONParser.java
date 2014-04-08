package com.example.olamundo.services;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

public class JSONParser {

	static InputStream is = null;
	static JSONObject jsonObj = null;
	// static String jsonString = "";
	Context context;

	public JSONParser(Context context) {
		this.context = context;
	}

	public JSONParser() {

	}

	public JSONObject getJSONFromUrlAfterHttpPost(String url,
			JSONObject jsonObject) {
		System.out.println("url is   :  " + url);
		System.out.println("json im sending   :  " + jsonObject.toString());

		try {
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(url);
			if (jsonObject != null) {
				StringEntity stringEntity = new StringEntity(
						jsonObject.toString());
				stringEntity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE,
						"application/json"));
				// stringEntity.setContentEncoding("UTF-8");
				// stringEntity.setContentType("application/json");
				httpPost.setEntity(stringEntity);
			}
			HttpResponse httpResponse = httpClient.execute(httpPost);
			HttpEntity httpEntity = httpResponse.getEntity();
			is = httpEntity.getContent();

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return getJSONObjectFromInputStream(is);

	}

	public JSONObject getJSONFromUrlAfterHttpPost2(String url,
			JSONObject jsonObject, File file, String fileKey, Uri fileUri) {
		// String jsonString = "";
		System.out.println("json im sending   :  " + jsonObject.toString());

		try {
			// InputStreamBody(context.getContentResolver().openInputStream(fileUri),"hello");
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(url);

			MultipartEntity multipartEntity = new MultipartEntity(
					HttpMultipartMode.BROWSER_COMPATIBLE);

			try {
				multipartEntity.addPart("user[email]", new StringBody(
						jsonObject.getString("email")));
				multipartEntity.addPart("user[password]", new StringBody(
						jsonObject.getString("password")));
				multipartEntity.addPart(
						"user[password_confirmation]",
						new StringBody(jsonObject
								.getString("password_confirmation")));

				JSONObject jsonObject2 = jsonObject.getJSONObject(
						"members_attributes").getJSONObject("0");
				multipartEntity.addPart("user[members_attributes][0][name]",
						new StringBody(jsonObject2.getString("name")));
				multipartEntity.addPart(
						"user[members_attributes][0][first_name]",
						new StringBody(jsonObject2.getString("first_name")));
				multipartEntity.addPart(
						"user[members_attributes][0][relationship]",
						new StringBody(jsonObject2.getString("relationship")));
				System.out.println("checking name  "
						+ jsonObject2.getString("name"));
			} catch (JSONException e) {
				e.printStackTrace();
			}
			// ContentBody contentBody = new FileBody(file, "image/jpeg");
			// multipartEntity.addPart(fileKey, contentBody);

			// FileBody fileBody = new FileBody(file, "image/jpeg");
			// multipartEntity.addPart(fileKey, fileBody);

			// multipartEntity.addPart(fileKey, new InputStreamBody(context
			// .getContentResolver().openInputStream(fileUri), file.getName()));

			// getClass().getClassLoader().getResourceAsStream(fileUri.toString());
			// multipartEntity.addPart(fileKey, new InputStreamBody(is,
			// file.getName()));

			// System.out.println("check22");
			httpPost.setEntity(multipartEntity);

			HttpResponse httpResponse = httpClient.execute(httpPost);
			HttpEntity httpEntity = httpResponse.getEntity();
			is = httpEntity.getContent();
			System.out.println("check11");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return getJSONObjectFromInputStream(is);
	}

	public JSONObject getJSONFromUrlAfterHttpPost3(String url,
			JSONObject jsonObject, File file, String fileKey, Uri fileUri) {
		// String jsonString = "";
		System.out.println("json im sending   :  " + jsonObject.toString());

		try {
			// InputStreamBody(context.getContentResolver().openInputStream(fileUri),"hello");
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(url);

			MultipartEntity multipartEntity = new MultipartEntity(
					HttpMultipartMode.BROWSER_COMPATIBLE);
			Iterator<?> jsonObjectKeys = jsonObject.keys();
			while( jsonObjectKeys.hasNext() ){
	            
	            String temp = (String)jsonObjectKeys.next();
	            try {
					multipartEntity.addPart(temp, new StringBody(
							jsonObject.getString(temp)));
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
//			try {
//				multipartEntity.addPart("user[email]", new StringBody(
//						jsonObject.getString("email")));
//				multipartEntity.addPart("user[password]", new StringBody(
//						jsonObject.getString("password")));
//				multipartEntity.addPart(
//						"user[password_confirmation]",
//						new StringBody(jsonObject
//								.getString("password_confirmation")));
//
//				JSONObject jsonObject2 = jsonObject.getJSONObject(
//						"members_attributes").getJSONObject("0");
//				multipartEntity.addPart("user[members_attributes][0][name]",
//						new StringBody(jsonObject2.getString("name")));
//				multipartEntity.addPart(
//						"user[members_attributes][0][first_name]",
//						new StringBody(jsonObject2.getString("first_name")));
//				multipartEntity.addPart(
//						"user[members_attributes][0][relationship]",
//						new StringBody(jsonObject2.getString("relationship")));
//				System.out.println("checking name  "
//						+ jsonObject2.getString("name"));
//			} catch (JSONException e) {
//				e.printStackTrace();
//			}
			// ContentBody contentBody = new FileBody(file, "image/jpeg");
			// multipartEntity.addPart(fileKey, contentBody);

			// FileBody fileBody = new FileBody(file, "image/jpeg");
			// multipartEntity.addPart(fileKey, fileBody);

			// multipartEntity.addPart(fileKey, new InputStreamBody(context
			// .getContentResolver().openInputStream(fileUri), file.getName()));

			// getClass().getClassLoader().getResourceAsStream(fileUri.toString());
			// multipartEntity.addPart(fileKey, new InputStreamBody(is,
			// file.getName()));

			// System.out.println("check22");
			httpPost.setEntity(multipartEntity);

			HttpResponse httpResponse = httpClient.execute(httpPost);
			HttpEntity httpEntity = httpResponse.getEntity();
			is = httpEntity.getContent();
			System.out.println("check11");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return getJSONObjectFromInputStream(is);
	}

	public JSONArray getJSONArrayFromUrlAfterHttpGet(String url) {
		
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(url);
		HttpResponse httpResponse;
		try {
			httpResponse = httpClient.execute(httpGet);
			HttpEntity httpEntity = httpResponse.getEntity();
			is = httpEntity.getContent();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return getJSONArrayFromInputStream(is);
	}

	public JSONObject getJSONObjectFromUrlAfterHttpGet(String url,
			List<NameValuePair> params) {
		DefaultHttpClient httpClient = new DefaultHttpClient();

		// if (jsonObject != null) {
		//
		// Iterator<?> keys = jsonObject.keys();
		//
		// while (keys.hasNext()) {
		// System.out.println("whiling");
		// String key = (String) keys.next();
		// try {
		// httpGet.addHeader(key, jsonObject.getString(key));
		// } catch (JSONException e) {
		// e.printStackTrace();
		// }
		// }
		// }

		if (!url.endsWith("?"))
			url += "?";

		if (params != null) {
			String paramString = URLEncodedUtils.format(params, "utf-8");
			url += paramString;
		}
		System.out.println("final uri :  " + url);
		HttpGet httpGet = new HttpGet(url);

		HttpResponse httpResponse;
		try {
			httpResponse = httpClient.execute(httpGet);
			HttpEntity httpEntity = httpResponse.getEntity();
			is = httpEntity.getContent();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return getJSONObjectFromInputStream(is);
	}
	
	public JSONArray getJSONArrayFromUrlAfterHttpGet(String url,
			List<NameValuePair> params) {
		DefaultHttpClient httpClient = new DefaultHttpClient();

		if (!url.endsWith("?"))
			url += "?";

		if (params != null) {
			String paramString = URLEncodedUtils.format(params, "utf-8");
			url += paramString;
		}
		System.out.println("final uri :  " + url);
		HttpGet httpGet = new HttpGet(url);

		HttpResponse httpResponse;
		try {
			httpResponse = httpClient.execute(httpGet);
			HttpEntity httpEntity = httpResponse.getEntity();
			is = httpEntity.getContent();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return getJSONArrayFromInputStream(is);
	}

	private JSONArray getJSONArrayFromInputStream(InputStream is) {
		String jsonString = "";
		JSONArray jsonArray = null;
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "iso-8859-1"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();
			jsonString = sb.toString();
			System.out.println("json string im getting  :  " + jsonString);
		} catch (Exception e) {
			Log.e("Buffer Error", "Error converting result " + e.toString());
		}

		// try parse the string to a JSON object
		try {
			// System.out.println("JSON is   :  " + jsonString);
			// jsonObj = new JSONObject(jsonString);
			jsonArray = new JSONArray(jsonString);
		} catch (JSONException e) {
			Log.e("JSON Parser", "Error parsing data " + e.toString());
		}

		// return JSON String
		return jsonArray;
	}

	private JSONObject getJSONObjectFromInputStream(InputStream is) {
		String jsonString = "";
		// System.out.println("input stream is :  " + is);
		JSONObject jsonObj = null;
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "iso-8859-1"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();
			jsonString = sb.toString();
			System.out.println("json string im getting  :  " + jsonString);
		} catch (Exception e) {
			Log.e("Buffer Error", "Error converting result " + e.toString());
		}

		// try parse the string to a JSON object
		try {
			jsonObj = new JSONObject(jsonString);
		} catch (JSONException e) {
			Log.e("JSON Parser", "Error parsing data " + e.toString());
		}

		return jsonObj;
	}
}