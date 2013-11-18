package edu.grinnell.appdev.grinnelldirectory;


import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.concurrent.ExecutionException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
	Intent listIntent;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_form);
        
        initializeViews(this);
	addListenerOnSubmitButton();
    }
    
    public void initializeViews(Context c){
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
			+ cleanString(facDeptSpinner.getSelectedItem().toString())
			+ "&Major=" 
			+ cleanString(studentMajorSpinner.getSelectedItem().toString())
			+ "&conc=&SGA=&Hiatus=&Gyear=&submit_search=Search";

			 	//TODO: Get rid of the fucking uberstring
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
		        
		        
//		        ArrayList<Profile> theData = new ArrayList<Profile>();
//		        String result = "";
//		        try {
//					theData = new RequestTask().execute(theURL).get();
//					Log.d("test", result);
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				} catch (ExecutionException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
		        
		        //parse the uberstring, add profiles, reset the list adapter
//		        StringTokenizer strTok = new StringTokenizer(result, ",");
//		        
//		        while(strTok.hasMoreTokens()){
//		        	theData.add(new Profile(strTok.nextToken(), strTok.nextToken(), strTok.nextToken()));
//		        }
		        //ProfileListActivity.setData(theData);
		        startActivity(listIntent);
                            
			}
 
		});
 
	}
	
	private String cleanString(String str) {
	   str = str.replace(" ", "+");
	   str = str.replace(",", "%2C");
	   str =str.replace("&", "%26");
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
}
