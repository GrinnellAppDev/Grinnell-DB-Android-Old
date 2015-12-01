package edu.grinnell.appdev.grinnelldirectory.Activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;

import com.flurry.android.FlurryAgent;

import edu.grinnell.appdev.grinnelldirectory.Fragments.ProfileDetailFragment;
import edu.grinnell.appdev.grinnelldirectory.R;

public class ProfileDetailActivity extends ActionBarActivity {

	public Bitmap mImage;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile_detail);

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		setTitle("");

		if (savedInstanceState == null) {
			Bundle arguments = new Bundle();
			arguments.putString(ProfileDetailFragment.ARG_ITEM_ID, getIntent()
					.getStringExtra(ProfileDetailFragment.ARG_ITEM_ID));
			ProfileDetailFragment fragment = new ProfileDetailFragment();
			fragment.setArguments(arguments);
			getSupportFragmentManager().beginTransaction()
					.add(R.id.profile_detail_container, fragment).commit();
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
			NavUtils.navigateUpTo(this, new Intent(this,
					ProfileListActivity.class));
			overridePendingTransition(R.anim.right_slide_in,
					R.anim.right_slide_out);
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		overridePendingTransition(R.anim.right_slide_in, R.anim.right_slide_out);
	}

	@Override
	protected void onStart() {
		super.onStart();
		FlurryAgent.onStartSession(this, "PRC5TVNX9DP7C9SVQDW3");
	}

	@Override
	protected void onStop() {
		super.onStop();
		FlurryAgent.onEndSession(this);
	}
}
