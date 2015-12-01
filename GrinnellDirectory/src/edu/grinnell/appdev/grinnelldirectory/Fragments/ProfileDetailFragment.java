/**************************************
 * ProfileDetailFragment
 * When an item in the profile list is selected,
 *      a ProfileDetailFragment is displayed,
 *      containing more details of that entry
 * Heavily based on the Master Flow template
 * The only changes were to incorporate Profiles,
 *      and to have a custom view with an image.
 * ************************************/

package edu.grinnell.appdev.grinnelldirectory.Fragments;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;

import edu.grinnell.appdev.grinnelldirectory.Activities.ProfileDetailActivity;
import edu.grinnell.appdev.grinnelldirectory.Models.Profile;
import edu.grinnell.appdev.grinnelldirectory.R;

public class ProfileDetailFragment extends Fragment {

    public static final String ARG_ITEM_ID = "item_id";

    Profile mItem;
    private ImageLoader imageLoader;
    ImageView imgview;
    ImageView rect;



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
        rect = ((ImageView) rootView.findViewById(R.id.rect));
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
                ((ProfileDetailActivity) getActivity()).mImage = loadedImage;
			    }
			});
	    } else {
            imgview.setImageResource(R.drawable.nopic);
        }

        imgview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Animation animScale = new ScaleAnimation(
//                        1f, 2.5f, // Start and end values for the X axis scaling
//                        1f, 2.5f, // Start and end values for the Y axis scaling
//                        Animation.RELATIVE_TO_SELF, 0f, // Pivot point of X scaling
//                        Animation.RELATIVE_TO_SELF, 1f); // Pivot point of Y scaling
//                animScale.setFillAfter(true);
//                animScale.setDuration(300);
//                Animation animTrans = new TranslateAnimation(0, 100, 0, 850);
//                animTrans.setFillAfter(true);
//                animTrans.setDuration(300);
//                AnimationSet animSet = new AnimationSet(true);
//                animSet.setFillEnabled(true);
//                animSet.addAnimation(animScale);
//                animSet.addAnimation(animTrans);
//                animSet.setFillAfter(true);
//                animSet.setInterpolator(new AccelerateInterpolator());
//                Animation animDarken = new AlphaAnimation(0.0f, 1.0f);
//                animDarken.setFillAfter(true);
//                animDarken.setDuration(300);
//
//
//                rect.setVisibility(View.VISIBLE);
//                rect.startAnimation(animDarken);
//                rect.bringToFront();
//                imgview.bringToFront();
//                imgview.startAnimation(animSet);
//
                ZoomPicFragment frag = ZoomPicFragment.newInstance();
                getFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(R.anim.right_slide_out,
                                R.anim.right_slide_inS)
                        .add(R.id.profile_detail_container, frag, "ZOOM")
                        .addToBackStack(null)
                        .commit();
            }
        });


        if (mItem != null) {
            ((TextView) rootView.findViewById(R.id.textUsername)).setText('[' + mItem.username + ']');
            ((TextView) rootView.findViewById(R.id.textMajor)).setText(mItem.dept);
            ((TextView) rootView.findViewById(R.id.textBoxNum)).setText(mItem.boxno);
            ((TextView) rootView.findViewById(R.id.textPhone)).setText(mItem.phonenum);
            ((TextView) rootView.findViewById(R.id.textAddress)).setText(mItem.campusaddress);
            ((TextView) rootView.findViewById(R.id.textName)).setText(mItem.lastName + " " + mItem.firstName);
            //((TextView) rootView.findViewById(R.id.textAll)).setText(mItem.toString());
        }
        return rootView;
    }
    
    
    
    
}
