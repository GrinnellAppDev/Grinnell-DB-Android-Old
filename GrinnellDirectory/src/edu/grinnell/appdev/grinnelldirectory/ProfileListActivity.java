package edu.grinnell.appdev.grinnelldirectory;

import java.util.ArrayList;

import edu.grinnell.appdev.grinnelldirectory.dummy.Profile;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;

public class ProfileListActivity extends FragmentActivity
        implements ProfileListFragment.Callbacks {

    private boolean mTwoPane;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_list);
        
        if (findViewById(R.id.profile_detail_container) != null) {
            mTwoPane = true;
            ((ProfileListFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.profile_list))
                    .setActivateOnItemClick(true);
        }
    }

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
    
    public static void setData(ArrayList<Profile> profileList){
    	Profile.setList(profileList);
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
