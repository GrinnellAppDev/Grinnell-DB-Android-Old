package edu.grinnell.appdev.grinnelldirectory.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

import edu.grinnell.appdev.grinnelldirectory.R;
import edu.grinnell.appdev.grinnelldirectory.Tasks.RequestTask;
import edu.grinnell.appdev.grinnelldirectory.Activities.SearchFormActivity;

/* A fragment off the Search Form Activity with a detailed search interface */
public class DetailedSearchFragment extends Fragment {
	SearchFormActivity mActivity;
	View mView;

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
	Spinner hiatusSpinner;
	Spinner studentClassSpinner;
	Button submitButton;

	// An intent for ProfileListActivity
	Intent listIntent;

	OnEditorActionListener editTextListener;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.fragment_detailed_search, container,
				false);
		mActivity = (SearchFormActivity) getActivity();

		setHasOptionsMenu(true);

		initializeViews(mActivity); // Initialize all of the variables.

		return mView;
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.activity_search_form, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.search:
			sendDetailedQuery();
			return true;
		case R.id.reset:
			clearFields();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public void initializeViews(Context c) {
		firstNameText = (TextView) mView.findViewById(R.id.first_text);
		lastNameText = (TextView) mView.findViewById(R.id.last_text);
		usernameText = (TextView) mView.findViewById(R.id.username_text);
		phoneText = (TextView) mView.findViewById(R.id.phone_text);
		campusAddressText = (TextView) mView
				.findViewById(R.id.campus_address_text);
		homeAddressText = (TextView) mView.findViewById(R.id.home_address_text);
		facDeptSpinner = (Spinner) mView.findViewById(R.id.fac_dept_spinner);
		studentMajorSpinner = (Spinner) mView
				.findViewById(R.id.student_major_spinner);
		concentrationSpinner = (Spinner) mView
				.findViewById(R.id.concentration_spinner);
		sgaSpinner = (Spinner) mView.findViewById(R.id.sga_spinner);
		hiatusSpinner = (Spinner) mView.findViewById(R.id.hiatus_spinner);
		studentClassSpinner = (Spinner) mView
				.findViewById(R.id.student_class_spinner);

		editTextListener = new OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {
				boolean handled = false;
				if (actionId == EditorInfo.IME_ACTION_SEARCH) {
					sendDetailedQuery();
					handled = true;
				}
				return handled;
			}
		};

		firstNameText.setOnEditorActionListener(editTextListener);
		lastNameText.setOnEditorActionListener(editTextListener);
		usernameText.setOnEditorActionListener(editTextListener);
		phoneText.setOnEditorActionListener(editTextListener);
		campusAddressText.setOnEditorActionListener(editTextListener);
		homeAddressText.setOnEditorActionListener(editTextListener);
	}

	public void sendDetailedQuery() {

		String theURL = "https://itwebapps.grinnell.edu/classic/asp/campusdirectory/GCdefault.asp?transmit=true&blackboardref=true&LastName="
				+ mActivity.cleanString(lastNameText.getText().toString())
				+ "&LNameSearch=startswith&FirstName="
				+ mActivity.cleanString(firstNameText.getText().toString())
				+ "&FNameSearch=startswith&email="
				+ mActivity.cleanString(usernameText.getText().toString())
				+ "&campusphonenumber="
				+ mActivity.cleanString(phoneText.getText().toString())
				+ "&campusquery="
				+ mActivity.cleanString(campusAddressText.getText().toString())
				+ "&Homequery="
				+ mActivity.cleanString(homeAddressText.getText().toString())
				+ "&Department="
				+ mActivity.cleanString(facDeptSpinner.getSelectedItem()
						.toString())
				+ "&Major="
				+ mActivity.cleanString(studentMajorSpinner.getSelectedItem()
						.toString())
				+ "&conc="
				+ mActivity.cleanString(concentrationSpinner.getSelectedItem()
						.toString())
				+ "&SGA="
				+ mActivity
						.cleanString(sgaSpinner.getSelectedItem().toString())
				+ "&Hiatus="
				+ mActivity.cleanHiatus(hiatusSpinner.getSelectedItem()
						.toString())
				+ "&Gyear="
				+ mActivity.cleanString(studentClassSpinner.getSelectedItem()
						.toString()) + "&submit_search=Search";

		if (mActivity.inGrinnell)
			new RequestTask(mActivity).execute(theURL);
		else {
			Toast toast = Toast
					.makeText(mActivity,
							"Connect to Grinnell College Student Network for Detailed Search",
							Toast.LENGTH_LONG);
			toast.show();
		}
	}

	public void clearFields() {
		firstNameText.setText("");
		lastNameText.setText("");
		usernameText.setText("");
		phoneText.setText("");
		campusAddressText.setText("");
		homeAddressText.setText("");
		facDeptSpinner.setSelection(0);
		studentMajorSpinner.setSelection(0);
		concentrationSpinner.setSelection(0);
		sgaSpinner.setSelection(0);
		hiatusSpinner.setSelection(0);
		studentClassSpinner.setSelection(0);
	}

}
