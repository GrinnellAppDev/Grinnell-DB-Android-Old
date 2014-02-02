package edu.grinnell.appdev.grinnelldirectory;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

import edu.grinnell.appdev.grinnelldirectory.dummy.Profile;

/* A fragment off the Search Form Activity with a simple search interface */
public class BasicSearchFragment extends SherlockFragment {
	TextView firstNameText;
	TextView lastNameText;
	Button submitButton;
	static boolean noResults;
	static boolean tooManyResults;

	SearchFormActivity mActivity;
	View mView;

	// An intent for ProfileListActivity
	Intent listIntent;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mView = inflater.inflate(R.layout.fragment_simple_search, container,
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
			sendQuery();
			return true;
		case R.id.reset:
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public void initializeViews(Context c) {
		firstNameText = (TextView) mView.findViewById(R.id.first_text);
		lastNameText = (TextView) mView.findViewById(R.id.last_text);
		
		OnEditorActionListener editTextListener = new OnEditorActionListener() {
		    @Override
		    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
		        boolean handled = false;
		        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
		            sendQuery();
		            handled = true;
		        }
		        return handled;
		    }
		};  
		
		firstNameText.setOnEditorActionListener(editTextListener);
		lastNameText.setOnEditorActionListener(editTextListener);

		listIntent = new Intent(mActivity, ProfileListActivity.class);
	}

	// send user search query
	public void sendQuery() {

		tooManyResults = false;
		noResults = false;

		String theURL = "https://itwebapps.grinnell.edu/classic/asp/campusdirectory/GCdefault.asp?transmit=true&blackboardref=true&LastName="
				+ mActivity.cleanString(lastNameText.getText().toString())
				+ "&LNameSearch=startswith&FirstName="
				+ mActivity.cleanString(firstNameText.getText().toString());

		// TODO: Get rid of the fucking uberstring

		ArrayList<Profile> profileList;
		try {
			profileList = new RequestTask().execute(theURL).get();
			if (tooManyResults) {
				Toast toast = Toast.makeText(mActivity,
						"Too many results. Please refine search",
						Toast.LENGTH_LONG);
				toast.show();
			} else if (noResults) {
				Toast toast = Toast.makeText(mActivity, "No results found",
						Toast.LENGTH_LONG);
				toast.show();
			} else {
				ProfileListActivity.setData(profileList);
				startActivity(listIntent);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}
}
