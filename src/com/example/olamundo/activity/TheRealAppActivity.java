package com.example.olamundo.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.olamundo.R;
import com.example.olamundo.adapter.NavDrawerListAdapter;
import com.example.olamundo.adapter.SelectChatProfileAdapter;
import com.example.olamundo.fragments.BuyOlaFragment;
import com.example.olamundo.fragments.ChatPageFragment;
import com.example.olamundo.models.LoginDetails;
import com.example.olamundo.models.Members;
import com.example.olamundo.models.NavDrawerItem;
import com.example.olamundo.services.CreateDialog;
import com.example.olamundo.services.GlobalVariable;
import com.example.olamundo.services.JSONParser;
import com.loopj.android.image.SmartImageView;

@SuppressLint("NewApi")
public class TheRealAppActivity extends Activity {

	boolean editOrChatMode = false;
	boolean disableRightDrawer;
	boolean addingOrEdittingProfileDecider;

	LayoutInflater inflater;
	JSONObject jsonObjectFromServer;
	JSONParser jParser;
	private TextView rightDrawerTV;

	// add/edit profile widgets
	private EditText fullnameET;
	private EditText lastnameET;
	private ImageButton clearFullNameImageButton;
	private ImageButton clearRelationImageButton;
	private RelativeLayout fullnameRL;
	private RelativeLayout lastnameRL;

	private ImageButton editCumSaveIB;
	HashMap<NavDrawerItem, List<String>> mapChildData;

	private DrawerLayout mDrawerLayout;

	private ExpandableListView leftExpandableList;
	private ListView rightListView;
	private NavDrawerListAdapter leftAdapter;
	private SelectChatProfileAdapter rightAdapter;

	private RelativeLayout leftDrawerRLData;
	private RelativeLayout whosTalkingToWhoRL;
	private RelativeLayout rightDrawerRL;
	private RelativeLayout rightDrawerRLData;
	private ImageButton rightDrawerButton;
	private SmartImageView previewImage;
	private SmartImageView previewImage2;
	// slide menu items
	private String[] navMenuTitles;
	private TypedArray navMenuIcons;

	private ArrayList<NavDrawerItem> navDrawerItems;
	List<NameValuePair> param;

	Intent intent;
	GlobalVariable globalVariable;
	View child;
	LoginDetails loginDetails;
	List<Members> members;
	Members ownProfileMember;

	SharedPreferences preferences;
	SharedPreferences.Editor editor;

	CreateDialog createDialog;
	AlertDialog alertDialog;
	AlertDialog.Builder ab;

	int ownProfilePosition;
	int positionChatPerson;
	int positionDeletePerson;

	Drawable drawableForLastBox;

	private void initializeThings() {
		intent = getIntent();
		globalVariable = (GlobalVariable) getApplicationContext();

		preferences = getSharedPreferences("hello", MODE_PRIVATE);
		editor = preferences.edit();

		createDialog = new CreateDialog(this);
		jParser = new JSONParser(this);
		loginDetails = globalVariable.getLoginDetails();

		// ownProfilePosition = intent.getIntExtra("own_profile_position", 0);
		if (preferences.getBoolean("own_profile_selected", false))
			ownProfilePosition = preferences.getInt("own_profile_position", 0);
		modifyMembers(null);

		// to check if msgs are null
		// for (int i = 0; i < members.size(); i++) {
		// if (members.get(i).getMessages() == null)
		// System.out.println("im null"+members.get(i).getId());
		// else
		// System.out.println("not null"+members.get(i).getId());
		// }

		inflater = (LayoutInflater) TheRealAppActivity.this
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

	}

	private void findThings() {
		leftExpandableList = (ExpandableListView) findViewById(R.id.left_expandableListView);
		rightListView = (ListView) findViewById(R.id.right_listView);
		leftDrawerRLData = (RelativeLayout) findViewById(R.id.tempRL);

		rightDrawerRL = (RelativeLayout) findViewById(R.id.rightRL);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		editCumSaveIB = (ImageButton) findViewById(R.id.edit_cum_done_IV);
		rightDrawerRLData = (RelativeLayout) findViewById(R.id.rightRL2);
		whosTalkingToWhoRL = (RelativeLayout) findViewById(R.id.whos_talking_to_who_RL);
		rightDrawerButton = (ImageButton) findViewById(R.id.right_drawer_button);
		rightDrawerTV = (TextView) findViewById(R.id.right_drawer_header);
		previewImage = (SmartImageView) findViewById(R.id.preview_image);
		previewImage2 = (SmartImageView) findViewById(R.id.preview_image2);

	}

	private void initialVisibilityForViews() {
		previewImage.setImageUrl(ownProfileMember.getImageURL());
		// previewImage2.setImageURL(ownProfileMember.getImageURL());
	}

	private void findThingsForCreateProfile() {
		clearFullNameImageButton = (ImageButton) findViewById(R.id.clear_fullname_imageButton);
		clearRelationImageButton = (ImageButton) findViewById(R.id.clear_relation_imageButton);
		fullnameET = (EditText) findViewById(R.id.fullname_edittext);
		lastnameET = (EditText) findViewById(R.id.relation_edittext);
		fullnameRL = (RelativeLayout) findViewById(R.id.fullname_RL);
		lastnameRL = (RelativeLayout) findViewById(R.id.lastname_RL);
	}

	private void initialVisibilityOfRightDrawer() {
		rightDrawerTV.setText("Choose a contact to \ncommunicate with");
	}

	private void initialVisibilityForCreateProfile(String name, String relation) {
		clearFullNameImageButton.setVisibility(View.INVISIBLE);
		clearRelationImageButton.setVisibility(View.INVISIBLE);
		fullnameET.setText(name);
		lastnameET.setText(relation);
	}

	private void myOwnTextChangeListenersForCreateProfile() {
		fullnameET.addTextChangedListener(new TextWatcher() {
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
				if (fullnameET.getText().toString().length() > 0)
					clearFullNameImageButton.setVisibility(View.VISIBLE);
				else
					clearFullNameImageButton.setVisibility(View.INVISIBLE);
			}
		});

		lastnameET.addTextChangedListener(new TextWatcher() {
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
				if (lastnameET.getText().toString().length() > 0)
					clearRelationImageButton.setVisibility(View.VISIBLE);
				else
					clearRelationImageButton.setVisibility(View.INVISIBLE);
			}
		});
	}

	private void modifyMembers(Members members) {
		this.members = new ArrayList<Members>(loginDetails.getMembers());
		System.out.println("init members" + this.members.size());
		if (members != null) {
			System.out.println("member not null");
			this.members.add(members);
		}
		drawableForLastBox = getResources().getDrawable(
				R.drawable.create_account);
		this.members.add(new Members("Create a contact", drawableForLastBox));
		System.out.println("final members" + this.members.size());
		ownProfileMember = this.members.remove(ownProfilePosition);
		Toast.makeText(this, "Welcome" + ownProfileMember.getName(),
				Toast.LENGTH_SHORT).show();
	}

	private void drawerConfiguration() {
		mDrawerLayout.setScrimColor(getResources().getColor(
				android.R.color.transparent));

		mDrawerLayout.setDrawerListener(new DrawerLayout.DrawerListener() {

			@Override
			public void onDrawerStateChanged(int arg0) {
				System.out.println("first & last");
			}

			@Override
			public void onDrawerSlide(View view, float arg1) {
				System.out.println("second");
			}

			@Override
			public void onDrawerOpened(View view) {
				System.out.println("third open");
				if (view == rightDrawerRL) {
					mDrawerLayout.setDrawerLockMode(
							DrawerLayout.LOCK_MODE_LOCKED_CLOSED,
							leftDrawerRLData);
				} else if (view == leftDrawerRLData) {
					mDrawerLayout
							.setDrawerLockMode(
									DrawerLayout.LOCK_MODE_LOCKED_CLOSED,
									rightDrawerRL);
				}
			}

			@Override
			public void onDrawerClosed(View view) {
				System.out.println("third close");
				if (view == rightDrawerRL) {
					mDrawerLayout.setDrawerLockMode(
							DrawerLayout.LOCK_MODE_UNLOCKED, leftDrawerRLData);
				} else if (view == leftDrawerRLData) {
					mDrawerLayout.setDrawerLockMode(
							DrawerLayout.LOCK_MODE_UNLOCKED, rightDrawerRL);
				}
				if (disableRightDrawer == true)
					mDrawerLayout
							.setDrawerLockMode(
									DrawerLayout.LOCK_MODE_LOCKED_CLOSED,
									rightDrawerRL);
			}
		});
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.the_real_app_page);
		overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
		((DrawerLayout) findViewById(R.id.drawer_layout))
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
		initializeThings();
		initialVisibilityForViews();
		editor.putBoolean("isOwnProfileSelected", true);
		// Gson gson = new Gson();
		// String selectedOwnMemberString = gson.toJson(selectedOwnMember);
		// editor.putString("selectedOwnMember", loginDetailsString);

		editor.commit();

		initialVisibilityOfRightDrawer();
		prepareLeftDrawerItems();
		prepareLeftDrawerSubItems();
		// setting the nav drawer list adapter
		leftExpandableList.setGroupIndicator(null);
		leftAdapter = new NavDrawerListAdapter(getApplicationContext(),
				navDrawerItems, mapChildData);
		leftExpandableList.setAdapter(leftAdapter);
		rightAdapter = new SelectChatProfileAdapter(this, members,
				editOrChatMode);

		rightListView.setAdapter(rightAdapter);

		expandableListListeners();
		drawerConfiguration();

		if (savedInstanceState == null) {
			// on first time display view for first nav item
			displayView(0, -1);
		}
	}

	private void changeViewOfActionBar(int visibility) {
		whosTalkingToWhoRL.setVisibility(visibility);
		rightDrawerButton.setVisibility(visibility);
	}
	
	Fragment fragment;
	private void displayView(int groupPosition, int childPosition) {
		// update the main content by replacing fragments

		fragment = null;
		switch (groupPosition) {
		case 0:
			fragment = new ChatPageFragment();
			disableRightDrawer = false;
			changeViewOfActionBar(View.VISIBLE);
			setFragementTag("CHAT_PAGE");
			break;
		case 1:
			// switch own profile
			editor.remove("own_profile_position");
			editor.remove("own_profile_selected");
			editor.commit();
			finish();
			System.out.println("break3");
			return;
			// break;
		case 2:
			// contact location
			fragment = new ChatPageFragment();
			break;
		case 3:
			fragment = new BuyOlaFragment();
			disableRightDrawer = true;
			changeViewOfActionBar(View.INVISIBLE);
			break;
		case 4:
			switch (childPosition) {
			case 0:
				fragment = new ChatPageFragment();
				break;
			case 1:
				fragment = new ChatPageFragment();
				break;
			default:
				break;
			}
			break;

		default:
			break;
		}

		if (fragment != null) {

			FragmentManager fragmentManager = getFragmentManager();
			fragmentManager.beginTransaction()
					.replace(R.id.frame_to_be_replaced, fragment).commit();

			leftExpandableList.setItemChecked(groupPosition, true);
			leftExpandableList.setSelection(groupPosition);
			mDrawerLayout.closeDrawer(leftDrawerRLData);
		} else {
			// error in creating fragment
			Log.e("MainActivity", "Error in creating fragment");
		}
	}

	private void setFragementTag(String tag)
	{
		if (fragment != null) {

			FragmentManager fragmentManager = getFragmentManager();
			fragmentManager.beginTransaction()
					.replace(R.id.frame_to_be_replaced, fragment, tag).commit();

			
		} else {
			// error in creating fragment
			Log.e("MainActivity", "Error in creating fragment");
		}
	}
	
	public void onOpenLeftDrawer(View view) {
		mDrawerLayout.openDrawer(leftDrawerRLData);
	}

	public void onOpenRightDrawer(View view) {
		mDrawerLayout.openDrawer(rightDrawerRL);
	}

	private void prepareLeftDrawerSubItems() {
		// listDataHeader = new ArrayList<String>();
		mapChildData = new HashMap<NavDrawerItem, List<String>>();

		// Adding child data
		List<String> chatPage = new ArrayList<String>();
		List<String> switchProfile = new ArrayList<String>();
		List<String> contactLocation = new ArrayList<String>();
		List<String> buy = new ArrayList<String>();
		List<String> help = new ArrayList<String>();
		help.add("page1");
		help.add("page2");

		mapChildData.put(navDrawerItems.get(0), chatPage); // Header, Child data
		mapChildData.put(navDrawerItems.get(1), switchProfile);
		mapChildData.put(navDrawerItems.get(2), contactLocation);
		mapChildData.put(navDrawerItems.get(3), buy);
		mapChildData.put(navDrawerItems.get(4), help);

	}

	private void expandableListListeners() {
		// View groupView;
		// group expand
		leftExpandableList
				.setOnGroupExpandListener(new OnGroupExpandListener() {
					@Override
					public void onGroupExpand(int groupPosition) {
						System.out.println("onGroupExpand");
						View groupView = ((View) leftExpandableList
								.getChildAt(groupPosition));
						ImageView arrowIV = (ImageView) groupView
								.findViewById(R.id.right_arrow);
						arrowIV.setImageDrawable(getResources().getDrawable(
								R.drawable.arrow_bottom));
					}
				});

		// group collapse
		leftExpandableList
				.setOnGroupCollapseListener(new OnGroupCollapseListener() {
					@Override
					public void onGroupCollapse(int groupPosition) {
						System.out.println("onGroupCollapse");
						View groupView = ((View) leftExpandableList
								.getChildAt(groupPosition));
						ImageView arrowIV = (ImageView) groupView
								.findViewById(R.id.right_arrow);
						arrowIV.setImageDrawable(getResources().getDrawable(
								R.drawable.arrow_right));
					}
				});
		// group click
		leftExpandableList.setOnGroupClickListener(new OnGroupClickListener() {
			@Override
			public boolean onGroupClick(ExpandableListView parent, View v,
					int groupPosition, long id) {
				System.out.println("onGroupClick");

				if (mapChildData.get(navDrawerItems.get(groupPosition)).size() == 0)
					displayView(groupPosition, -1);
				return false;
			}
		});

		// child click
		leftExpandableList.setOnChildClickListener(new OnChildClickListener() {
			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				System.out.println("onChildClick");
				displayView(groupPosition, childPosition);
				return false;
			}
		});
	}

	private void prepareLeftDrawerItems() {
		navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);
		navMenuIcons = getResources()
				.obtainTypedArray(R.array.nav_drawer_icons);

		navDrawerItems = new ArrayList<NavDrawerItem>();
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[0], navMenuIcons
				.getResourceId(0, -1)));
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], navMenuIcons
				.getResourceId(1, -1)));
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], navMenuIcons
				.getResourceId(2, -1)));
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[3], navMenuIcons
				.getResourceId(3, -1)));
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[4], navMenuIcons
				.getResourceId(4, -1), true, "hello"));

		// Recycle the typed array
		navMenuIcons.recycle();

	}

	public void onLogOut(View view) {
		Toast.makeText(this, "logout", Toast.LENGTH_SHORT).show();
		editor.putBoolean("isLogin", false);
		editor.putBoolean("own_profile_selected", false);
		editor.commit();
		Intent intent = new Intent(this, MainActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
				| Intent.FLAG_ACTIVITY_CLEAR_TASK);
		startActivity(intent);
	}

	public void onEditCumDoneButton(View view) {
		System.out.println("onEditCumDoneButton");
		editOrChatMode = !editOrChatMode;
		rightAdapter = new SelectChatProfileAdapter(this, members,
				editOrChatMode);
		rightListView.setAdapter(rightAdapter);

		if (editOrChatMode) {
			editCumSaveIB.setImageDrawable(getResources().getDrawable(
					R.drawable.tick));
			rightDrawerTV.setText("Choose a contact\nto edit");
		} else {
			editCumSaveIB.setImageDrawable(getResources().getDrawable(
					R.drawable.edit));
			rightDrawerTV.setText("Choose a contact to \ncommunicate with");
		}

	}

	public void onDeleteContact(View view) {
		// System.out.println("deleting" + position);
		positionDeletePerson = rightListView.getPositionForView(view);
		Toast.makeText(this, "deleting" + positionDeletePerson,
				Toast.LENGTH_SHORT).show();
		ab = createDialog
				.createAlertDialog("Alert", "Are you sure you want to delete"
						+ positionDeletePerson, false);
		ab.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				dialog.dismiss();
				// finish();
				param = new LinkedList<NameValuePair>();
				param.add(new BasicNameValuePair("email", loginDetails
						.getEmail()));
				param.add(new BasicNameValuePair("member_id", Integer
						.toString(members.get(positionDeletePerson).getId())));

				DeleteProfileAsync deleteProfileAsync = new DeleteProfileAsync();
				deleteProfileAsync.execute("hello");
			}
		});
		ab.setNegativeButton("No", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
				dialog.dismiss();
				// finish();
				return;
			}
		});
		alertDialog = ab.create();
		alertDialog.show();

	}

	private class DeleteProfileAsync extends
			AsyncTask<String, Void, JSONObject> {

		@Override
		protected JSONObject doInBackground(String... params) {
			try {
				// postMethod2();
				String url = GlobalVariable.URL + GlobalVariable.DELETE_PROFILE
						+ ".json";
				jsonObjectFromServer = jParser
						.getJSONObjectFromUrlAfterHttpGet(url, param);
				System.out.println("doInBackground done");
			} catch (Exception e) {
				e.printStackTrace();
			}
			return jsonObjectFromServer;
		}

		@Override
		protected void onPostExecute(JSONObject jsonObjectFromServer) {
			System.out.println("postExecute");

			if (jsonObjectFromServer == null)
				System.out.println("im null");
			else {
				System.out.println("im not null");
				if (jsonObjectFromServer.has("status"))
					Toast.makeText(TheRealAppActivity.this, "Deleted",
							Toast.LENGTH_SHORT).show();
				if (positionDeletePerson >= ownProfilePosition)
					positionDeletePerson++;
				else {
					ownProfilePosition--;
					editor.putInt("own_profile_position", ownProfilePosition);
					editor.commit();
				}
				globalVariable.getLoginDetails().getMembers()
						.remove(positionDeletePerson);
				modifyMembers(null);
				rightAdapter.swapData(members);
				rightAdapter.notifyDataSetChanged();
			}
		}
	}

	public void onProfileDetailsClick(View view) {
		positionChatPerson = rightListView.getPositionForView(view);
		Members clickedMember = members.get(positionChatPerson);

		if (positionChatPerson == (members.size() - 1)) {
			Toast.makeText(TheRealAppActivity.this, "last", Toast.LENGTH_SHORT)
					.show();
			addingOrEdittingProfileDecider = true;
			loadAddEditView();
			initialVisibilityForCreateProfile("", "");

			System.out.println("onProfile" + positionChatPerson);
			return;
		} else {
			addingOrEdittingProfileDecider = false;
			if (editOrChatMode == true) {
				loadAddEditView();
				initialVisibilityForCreateProfile(clickedMember.getFirstName(),
						clickedMember.getName());
			} else {
				System.out.println("else");
				// Toast.makeText(this, "go back to chat  " +
				// positionChatPerson,
				// Toast.LENGTH_SHORT / 2).show();
				previewImage2.setImageUrl(clickedMember.getImageURL());
				mDrawerLayout.closeDrawer(rightDrawerRL);
				FragmentManager fragmentManager = getFragmentManager();
				ChatPageFragment fragment = (ChatPageFragment) fragmentManager
						.findFragmentByTag("CHAT_PAGE");
				if (fragment == null)
					System.out.println("frag null");
				else
				{
					if (positionChatPerson >= ownProfilePosition)
						fragment.displayChatMsgs(positionChatPerson+1);
					else
						fragment.displayChatMsgs(positionChatPerson);
					}
//					System.out.println("visibility  " + fragment.isVisible());
			}
		}
	}

	private void loadAddEditView() {
		child = inflater.inflate(R.layout.add_profile, null);
		rightDrawerRLData.setVisibility(View.GONE);
		rightDrawerRL.addView(child);
		findThingsForCreateProfile();
		myOwnTextChangeListenersForCreateProfile();

	}

	public void onClearText(View view) {
		System.out.println("onClearText");
		int temp = ((ImageButton) view).getId();

		if (temp == R.id.clear_fullname_imageButton) {
			fullnameET.setText("");
		} else if (temp == R.id.clear_relation_imageButton) {
			lastnameET.setText("");
		}
	}

	private boolean validate() {
		boolean temp = true;
		fullnameRL.setBackground(getResources().getDrawable(
				R.drawable.layer_list_blue));
		lastnameRL.setBackground(getResources().getDrawable(
				R.drawable.layer_list_blue));
		if (fullnameET.getText().toString().trim().length() == 0) {
			// fullnameEditText.setError("You must enter your fullname.");
			fullnameRL.setBackground(getResources().getDrawable(
					R.drawable.layer_list_red));

			temp = false;
		}
		if (lastnameET.getText().toString().trim().length() == 0) {
			// fullnameEditText.setError("You must enter your fullname.");
			lastnameRL.setBackground(getResources().getDrawable(
					R.drawable.layer_list_red));
			temp = false;
		}

		return temp;
	}

	public void onCancel(View view) {
		System.out.println("onCancel");
		rightDrawerRLData.setVisibility(View.VISIBLE);
		rightDrawerRL.removeView(child);
	}

	public void onSave(View view) {
		// Toast.makeText(this, "nothing", Toast.LENGTH_SHORT).show();
		if (!validate())
			return;
		JSONObject jsonObject = null;
		try {
			jsonObject = new JSONObject()
					.put("firstname", fullnameET.getText().toString())
					.put("lastname", lastnameET.getText().toString())
					.put("email", "pranav@motifworks.com");
			// jsonObject = new JSONObject()
			// .put("firstname", "")
			// .put("lastname", "")
			// .put("email", "pranav@motifworks.com");
			if (addingOrEdittingProfileDecider == false) {
				jsonObject.put("existing_member", "yes").put(
						"existing_member_id",
						members.get(positionChatPerson).getId());
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		AddEditProfileAsync addEditProfileAsync = new AddEditProfileAsync();
		addEditProfileAsync.execute(jsonObject);
	}

	private class AddEditProfileAsync extends
			AsyncTask<JSONObject, Void, JSONObject> {

		@Override
		protected JSONObject doInBackground(JSONObject... params) {
			try {
				String url = GlobalVariable.URL
						+ GlobalVariable.ADD_EDIT_PROFILE + ".json";
				jsonObjectFromServer = jParser.getJSONFromUrlAfterHttpPost3(
						url, params[0], null,
						"user[members_attributes][0][avatar]", null);
				System.out.println("doInBackground done");
			} catch (Exception e) {
				e.printStackTrace();
			}
			return jsonObjectFromServer;
		}

		@Override
		protected void onPostExecute(JSONObject jsonObjectFromServer) {
			// super.onPostExecute(result);
			System.out.println("postExecute");

			if (jsonObjectFromServer == null)
				System.out.println("im null");
			else {
				System.out.println("im not null");
				if (jsonObjectFromServer.has(GlobalVariable.ID)) {
					try {
						System.out.println("check  : "
								+ jsonObjectFromServer
										.get(GlobalVariable.AVATAR_UPDATED_AT));
						if (addingOrEdittingProfileDecider == true) {
							// when new profile is added
							// System.out
							// .println("avatar is  : "
							// + jsonObjectFromServer
							// .get(GlobalVariable.AVATAR_UPDATED_AT));
							Members addMember = new Members(
									jsonObjectFromServer
											.getString(GlobalVariable.FIRST_NAME),
									jsonObjectFromServer
											.getString(GlobalVariable.NAME),
									jsonObjectFromServer
											.getString(GlobalVariable.IMAGE_URL),
									jsonObjectFromServer
											.getInt(GlobalVariable.ID),
									jsonObjectFromServer
											.getString(GlobalVariable.RELATIONSHIP));
							// globalVariable.getLoginDetails().getMembers().add(addMember);
							loginDetails.getMembers().size();
							loginDetails.getMembers().add(addMember);
							loginDetails.getMembers().size();
							modifyMembers(null);
							rightAdapter
									.swapData(TheRealAppActivity.this.members);
						} else {
							// profile is edited
							System.out.println("profile edit");
							if (positionChatPerson >= ownProfilePosition)
								positionChatPerson++;

							Members members = loginDetails.getMembers().get(
									positionChatPerson);
							// if (members == loginDetails.getMembers().get(
							// positionChatPerson))
							// System.out.println("==");
							members.setFirstName(jsonObjectFromServer
									.getString(GlobalVariable.FIRST_NAME));
							members.setName(jsonObjectFromServer
									.getString(GlobalVariable.NAME));
							members.setImageURL(jsonObjectFromServer
									.getString(GlobalVariable.IMAGE_URL));
							modifyMembers(null);
							rightAdapter
									.swapData(TheRealAppActivity.this.members);
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
				rightDrawerRLData.setVisibility(View.VISIBLE);
				rightDrawerRL.removeView(child);
				// members.remove(members.size()-1);
				// members.add(globalVariable.getAddedMember());
				// members.add(new Members("Create a contact",
				// drawableForLastBox));
				rightAdapter.notifyDataSetChanged();
				System.out.println("notified");
			}
		}

	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
	}
}
