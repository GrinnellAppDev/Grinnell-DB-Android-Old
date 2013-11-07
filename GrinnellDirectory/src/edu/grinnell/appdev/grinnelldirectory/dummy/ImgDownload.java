package edu.grinnell.appdev.grinnelldirectory.dummy;

import java.net.URL;
import java.net.URLConnection;

import edu.grinnell.appdev.grinnelldirectory.dummy.Profile;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

class ImgDownload extends AsyncTask {
    private String requestUrl;
    private Profile theUser;
    private Bitmap pic;

    ImgDownload(String requestUrl, Profile theUser) {
        this.requestUrl = requestUrl;
        this.theUser = theUser;
    }

    @Override
    protected Object doInBackground(Object... objects) {
        try {
            URL url = new URL(requestUrl);
            URLConnection conn = url.openConnection();
            pic = BitmapFactory.decodeStream(conn.getInputStream());
        } catch (Exception ex) {
        }
        return null;
    }

    @Override
    protected void onPostExecute(Object o) {
        theUser.pic = pic;
    }
}