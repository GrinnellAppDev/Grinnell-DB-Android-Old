/**************************************
 * ProfileDetailFragment
 * When an item in the profile list is selected,
 *      a ProfileDetailFragment is displayed,
 *      containing more details of that entry
 * Heavily based on the Master Flow template
 * The only changes were to incorporate Profiles,
 *      and to have a custom view with an image.
 * ************************************/

package edu.grinnell.appdev.grinnelldirectory;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;

import edu.grinnell.appdev.grinnelldirectory.dummy.Profile;

public class ProfileDetailFragment extends SherlockFragment {

    public static final String ARG_ITEM_ID = "item_id";

    Profile mItem;
    private ImageLoader imageLoader;
    ImageView imgview; 


    public ProfileDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments().containsKey(ARG_ITEM_ID)) {
            mItem = Profile.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_profile_detail, container, false);
        
        // Initializes an imageView
        imgview = ((ImageView) rootView.findViewById(R.id.imageDetail));
	    if (mItem.picurl != "") {

		// Fills the imageView with universalImageLoader
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
			getActivity()).build();
		imageLoader = ImageLoader.getInstance();
		imageLoader.init(config);
		imageLoader.loadImage(mItem.picurl,
			new SimpleImageLoadingListener() {
			    @Override
			    public void onLoadingComplete(String imageUri,
				    View view, Bitmap loadedImage) {
				imgview.setImageBitmap(loadedImage);
			    }
			});
	    } else
		imgview.setImageResource(R.drawable.nopic);
        if (mItem != null) {
            ((TextView) rootView.findViewById(R.id.textUsername)).setText(mItem.username);
            ((TextView) rootView.findViewById(R.id.textFirstName)).setText(mItem.toString());
        }
        return rootView;
    }
    
    
}
