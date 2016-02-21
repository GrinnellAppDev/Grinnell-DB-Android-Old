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

import android.animation.ObjectAnimator;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;

import edu.grinnell.appdev.grinnelldirectory.Activities.ProfileDetailActivity;
import edu.grinnell.appdev.grinnelldirectory.Models.Profile;
import edu.grinnell.appdev.grinnelldirectory.Other.Utils;
import edu.grinnell.appdev.grinnelldirectory.R;

public class ProfileDetailFragment extends Fragment {

    public static final String ARG_ITEM_ID = "item_id";

    Profile mItem;
    private ImageLoader imageLoader;
    ImageView imgview;
    ImageView rect;
    View mWrapper;
    //The factor by which to enlarge our imageview in the zoom animation
    float scaleFactor = 2.5f;
    boolean isImageZoomed = false;
    float adjustedWidth;
    float adjustedHeight;


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

        //Layout Wrapper
        mWrapper = rootView.findViewById(R.id.wrapper);

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
                if (!isImageZoomed) {
                    float width = Utils.getScreenWidth(getActivity());
                    float height = Utils.getScreenHeight(getActivity());
                    Screen adjustedScreen = calculateAdjustedViewBounds(new Screen(width, height));
                    adjustedWidth = adjustedScreen.width;
                    adjustedHeight = adjustedScreen.height;

                    translateImageAnim(0,0, adjustedWidth, adjustedHeight, imgview);
                    scaleImageAnim(1f, scaleFactor, imgview);

                    //Animation for "dulling" the screen behind the image
                    Animation animDarken = new AlphaAnimation(0.0f, 0.8f);
                    animDarken.setFillAfter(true);
                    animDarken.setDuration(350);


                    rect.setVisibility(View.VISIBLE);
                    rect.startAnimation(animDarken);
                    rect.bringToFront();
                    imgview.bringToFront();
                    isImageZoomed = true;
                }

            }
        });

        rect.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == event.ACTION_DOWN) {
                    if (isImageZoomed) {
                        //Reverse animation
                        Animation animLighten = new AlphaAnimation(0.8f, 0.0f);
                        animLighten.setFillAfter(true);
                        animLighten.setDuration(350);
                        v.startAnimation(animLighten);
                        translateImageAnim(adjustedWidth, adjustedHeight, 0 ,0 , imgview);
                        scaleImageAnim(scaleFactor, 1f, imgview);

                        isImageZoomed = false;
                    }
                }
                return false;
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

    /**
     The following method handles the animation numbers, depending on orientation.
     The "magic" numbers you see were calculated to provide the most optimal image size for
     each orientation, depending on screen estate available. As they are based on each screen's
     width and height, they are guaranteed to provide a uniform experience across different devices.
     **/

    Screen calculateAdjustedViewBounds (Screen screenVals) {

        float width = Utils.getRawScreenWidth(getActivity());
        float height = Utils.getRawScreenHeight(getActivity());

        //This Handles when screen is in either landscape or portrait mode
            scaleFactor = (float) (0.7 * height)/imgview.getHeight();
            screenVals.width = width/2 - imgview.getWidth()/2;
            screenVals.height = (float) (height/2 - imgview.getHeight()/1.5);

        return screenVals;

    }

    void scaleImageAnim(float from, float to, View target) {

        ObjectAnimator animScaleNewX = ObjectAnimator.ofFloat(target, "scaleX", from, to);
        ObjectAnimator animScaleNewY = ObjectAnimator.ofFloat(target, "scaleY", from, to);
        animScaleNewX.setDuration(350);
        animScaleNewY.setDuration(350);
        animScaleNewX.start();
        animScaleNewY.start();

    }

    void translateImageAnim (float fromWidth, float fromHeight, float toWidth, float toHeight, View target) {
        ObjectAnimator animTransNewX = ObjectAnimator.ofFloat(target, "translationX", fromWidth, toWidth);
        ObjectAnimator animTransNewY = ObjectAnimator.ofFloat(target, "translationY", fromHeight, toHeight);
        animTransNewX.setDuration(350);
        animTransNewY.setDuration(350);
        animTransNewX.start();
        animTransNewY.start();
    }

    class Screen {
        float width;
        float height;

        public Screen (float width, float height) {
            this.width = width;
            this.height = height;
        }
    }
    
}
