package edu.grinnell.appdev.grinnelldirectory;

import java.util.ArrayList;
import java.util.List;

import edu.grinnell.appdev.grinnelldirectory.dummy.Profile;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ProfileListFragment extends ListFragment {

	public static ProfileAdapter profileAdapter;
	
    private static final String STATE_ACTIVATED_POSITION = "activated_position";

    private Callbacks mCallbacks = sDummyCallbacks;
    private int mActivatedPosition = ListView.INVALID_POSITION;

    public interface Callbacks {

        public void onItemSelected(String id);
    }

    private static Callbacks sDummyCallbacks = new Callbacks() {
        @Override
        public void onItemSelected(String id) {
        }
    };

    public ProfileListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        profileAdapter = new ProfileAdapter(Profile.ITEMS, getActivity());
        setListAdapter(profileAdapter);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (savedInstanceState != null && savedInstanceState
                .containsKey(STATE_ACTIVATED_POSITION)) {
            setActivatedPosition(savedInstanceState.getInt(STATE_ACTIVATED_POSITION));
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (!(activity instanceof Callbacks)) {
            throw new IllegalStateException("Activity must implement fragment's callbacks.");
        }

        mCallbacks = (Callbacks) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = sDummyCallbacks;
    }

    @Override
    public void onListItemClick(ListView listView, View view, int position, long id) {
        super.onListItemClick(listView, view, position, id);
        mCallbacks.onItemSelected(Profile.ITEMS.get(position).username);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mActivatedPosition != ListView.INVALID_POSITION) {
            outState.putInt(STATE_ACTIVATED_POSITION, mActivatedPosition);
        }
    }

    public void setActivateOnItemClick(boolean activateOnItemClick) {
        getListView().setChoiceMode(activateOnItemClick
                ? ListView.CHOICE_MODE_SINGLE
                : ListView.CHOICE_MODE_NONE);
    }

    public void setActivatedPosition(int position) {
        if (position == ListView.INVALID_POSITION) {
            getListView().setItemChecked(mActivatedPosition, false);
        } else {
            getListView().setItemChecked(position, true);
        }

        mActivatedPosition = position;
    }
    
    public static void refresh(){
    	if(profileAdapter != null)
    		profileAdapter.notifyDataSetChanged();
    }
    
    
    public class ProfileAdapter extends ArrayAdapter<Profile> {

    	private List<Profile> profileList;
    	private Context context;
    	 
    	public ProfileAdapter(List<Profile> profileList, Context ctx) {
    	    super(ctx, R.layout.fragment_result_list_entry, profileList);
    	    this.profileList = profileList;
    	    this.context = ctx;
    	}
    	 
    	public View getView(int position, View convertView, ViewGroup parent) {
    	     
    	    // First let's verify the convertView is not null
    	    if (convertView == null) {
    	        // This a new view we inflate the new layout
    	        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    	        convertView = inflater.inflate(R.layout.fragment_result_list_entry, parent, false);
    	    }
    	        // Now we can fill the layout with the right values
	        	Profile p = profileList.get(position);
    	        ((TextView) convertView.findViewById(R.id.textUsername)).setText(p.username);
    	 
    	    return convertView;
    	}
    }

}
