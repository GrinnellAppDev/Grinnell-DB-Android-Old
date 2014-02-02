package edu.grinnell.appdev.grinnelldirectory;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.MenuItem;


public class ProfileDetailActivity extends SherlockFragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_detail);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		setTitle("");
        
        if (savedInstanceState == null) {
            Bundle arguments = new Bundle();
            arguments.putString(ProfileDetailFragment.ARG_ITEM_ID,
                    getIntent().getStringExtra(ProfileDetailFragment.ARG_ITEM_ID));
            ProfileDetailFragment fragment = new ProfileDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.profile_detail_container, fragment)
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            NavUtils.navigateUpTo(this, new Intent(this, ProfileListActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
