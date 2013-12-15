/****************************************
 * SearchFormActivity
 * The inital activity.
 * This activity runs the search form.
 * Upon hitting the submit button, it uses RequestTask
 * 	to create an ArrayList of Profiles.
 * 	It then launches a ListActivityIntent to display them.
 * **************************************/

package edu.grinnell.appdev.grinnelldirectory;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import edu.grinnell.appdev.grinnelldirectory.dummy.Profile;

public class SearchFormActivity extends Activity {

	// Fields in the layout
	TextView firstNameText;
	TextView lastNameText;
	TextView usernameText;
	TextView phoneText;
	TextView campusAddressText;
	TextView homeAddressText;
	Spinner facDeptSpinner;
	Spinner studentMajorSpinner;
	Spinner concentrationSpinner;
	Spinner sgaSpinner;
	Spinner haitusSpinner;
	Spinner studentClassSpinner;
	Button submitButton;

	// An intent for ProfileListActivity
	Intent listIntent;

	public static final int NETWORK = 3;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_form);

		initializeViews(this); // Initialize all of the variables.
		addListenerOnSubmitButton(); // Add a listener to the submit button.
		
		ConnectivityManager cm = (ConnectivityManager)
				this.getSystemService(Context.CONNECTIVITY_SERVICE);
		//check connections before downloading..

		if (!networkEnabled(cm)){
			noNetworkError();
		}

	}

	public void initializeViews(Context c) {
		firstNameText = (TextView) findViewById(R.id.first_text);
		lastNameText = (TextView) findViewById(R.id.last_text);
		usernameText = (TextView) findViewById(R.id.username_text);
		phoneText = (TextView) findViewById(R.id.phone_text);
		campusAddressText = (TextView) findViewById(R.id.campus_address_text);
		homeAddressText = (TextView) findViewById(R.id.home_address_text);
		facDeptSpinner = (Spinner) findViewById(R.id.fac_dept_spinner);
		studentMajorSpinner = (Spinner) findViewById(R.id.student_major_spinner);
		concentrationSpinner = (Spinner) findViewById(R.id.concentration_spinner);
		sgaSpinner = (Spinner) findViewById(R.id.sga_spinner);
		haitusSpinner = (Spinner) findViewById(R.id.hiatus_spinner);
		studentClassSpinner = (Spinner) findViewById(R.id.student_class_spinner);
		submitButton = (Button) findViewById(R.id.submit_button);
		
		listIntent = new Intent(this, ProfileListActivity.class);
	}

	// Adds a listener to the submit button.
	// The responding method gets the information from the fields,
	// and pieces together a valid DB request URL.
	// This is passed to RequestTask.
	// The ProfileListActivity intent is started.
	public void addListenerOnSubmitButton() {

		final Context context = this;
		submitButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				String theURL = "https://itwebapps.grinnell.edu/classic/asp/campusdirectory/GCdefault.asp?transmit=true&blackboardref=true&LastName="
						+ lastNameText.getText()
						+ "&LNameSearch=startswith&FirstName="
						+ firstNameText.getText()
						+ "&FNameSearch=startswith&email="
						+ usernameText.getText()
						+ "&campusphonenumber="
						+ phoneText.getText()
						+ "&campusquery="
						+ campusAddressText.getText()
						+ "&Homequery="
						+ homeAddressText.getText()
						+ "&Department="
						+ cleanString(facDeptSpinner.getSelectedItem()
								.toString())
						+ "&Major="
						+ cleanString(studentMajorSpinner.getSelectedItem()
								.toString())
						+ "&conc=&SGA=&Hiatus=&Gyear=&submit_search=Search";

				// TODO: Get rid of the fucking uberstring
				Log.d("test", "Test 1");

				ArrayList<Profile> profileList;
				try {
					profileList = new RequestTask().execute(theURL).get();
					ProfileListActivity.setData(profileList);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				startActivity(listIntent);

			}

		});

	}

	// Converts plain-text strings into HTTP-friendly strings.
	private String cleanString(String str) {
		str = str.replace(" ", "+");
		str = str.replace(",", "%2C");
		str = str.replace("&", "%26");
		if (str.length() > 36) {
			str = str.substring(0, 35);
		}
		return str;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_search_form, menu);
		return true;
	}

	/*
	 * Return true if the device has a network adapter that is capable of
	 * accessing the network.
	 */
	protected static boolean networkEnabled(ConnectivityManager connec) {
		// ARE WE CONNECTED TO THE NET
		if (connec.getNetworkInfo(0).getState() == NetworkInfo.State.CONNECTED
				|| connec.getNetworkInfo(1).getState() == NetworkInfo.State.CONNECTED) {
			// MESSAGE TO SCREEN FOR TESTING (IF REQ)
			// Toast.makeText(this, connectionType + ” connected”,
			// Toast.LENGTH_SHORT).show();
			return true;
		} else if (connec.getNetworkInfo(0).getState() == NetworkInfo.State.DISCONNECTED
				|| connec.getNetworkInfo(1).getState() == NetworkInfo.State.DISCONNECTED) {
			return false;
		}

		return false;
	}

	public void noNetworkError() {
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
				this);
		// set title
		alertDialogBuilder.setTitle("No Internet Connection");
		// set dialog message
		alertDialogBuilder
				.setPositiveButton("Exit",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								dialog.cancel();
								finish();
							}
						});
		// create alert dialog
		AlertDialog alertDialog = alertDialogBuilder.create();
		// show it
		alertDialog.show();
	}
}
