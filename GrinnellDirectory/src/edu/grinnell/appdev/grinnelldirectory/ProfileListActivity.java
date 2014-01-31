/*****************************************
 * ProfileListActivity
 * This activity runs the results list.
 * This is very much based off of the Master Flow template.
 * Changes from the template include:
 *      -Incorporating Profile objects
 *      -setData(), resetData(), addToData()
 * The data is dowloaded outside of this method,
 *      so public data manipulating methods are required.
 * The list data is stored staticly in the Profile class.
 * ***************************************/

package edu.grinnell.appdev.grinnelldirectory;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;

import com.actionbarsherlock.app.SherlockFragmentActivity;

import edu.grinnell.appdev.grinnelldirectory.dummy.Profile;

public class ProfileListActivity extends SherlockFragmentActivity
        implements ProfileListFragment.Callbacks {

    private boolean mTwoPane;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_list);
        
        //Tablet 2-pane support
        if (findViewById(R.id.profile_detail_container) != null) {
            mTwoPane = true;
            ((ProfileListFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.profile_list))
                    .setActivateOnItemClick(true);
        }
    }

//Launches an intent, to display the selected item's Profile in a ProfileDetailFragment activity
    @Override
    public void onItemSelected(String id) {
        if (mTwoPane) {
            Bundle arguments = new Bundle();
            arguments.putString(ProfileDetailFragment.ARG_ITEM_ID, id);
            ProfileDetailFragment fragment = new ProfileDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.profile_detail_container, fragment)
                    .commit();

        } else {
            Intent detailIntent = new Intent(this, ProfileDetailActivity.class);
            detailIntent.putExtra(ProfileDetailFragment.ARG_ITEM_ID, id);
            startActivity(detailIntent);
        }
    }
    
    //the data is dowloaded outside of this method, so public data manipulating methods are required.
    public static void setData(ArrayList<Profile> profileList){
        //Manipulate the data. The data is stored in the Profile class.
    	Profile.setList(profileList);
    	//Tell the ListFragment that the data has beeen changed.
    	ProfileListFragment.refresh();
    }
    
    public static void resetData(){
    	Profile.resetList();
    	ProfileListFragment.refresh();
    }
    
    public static void addToData(ArrayList<Profile> profileList){
    	Profile.addToList(profileList);
    	ProfileListFragment.refresh();
    }

}
