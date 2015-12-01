package edu.grinnell.appdev.grinnelldirectory.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import edu.grinnell.appdev.grinnelldirectory.Activities.ProfileDetailActivity;
import edu.grinnell.appdev.grinnelldirectory.R;

public class ZoomPicFragment extends Fragment {

    private static final String ARG_URL = "param1";

    private ImageView mImage;

    public ZoomPicFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static ZoomPicFragment newInstance() {
        ZoomPicFragment fragment = new ZoomPicFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        ((ActionBarActivity) getActivity()).getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00FFFFFF")));
        View v = inflater.inflate(R.layout.fragment_zoom_pic, container, false);
        mImage = (ImageView) v.findViewById(R.id.image);
        mImage.setImageBitmap(((ProfileDetailActivity) getActivity()).mImage);
        return v;
    }



}
