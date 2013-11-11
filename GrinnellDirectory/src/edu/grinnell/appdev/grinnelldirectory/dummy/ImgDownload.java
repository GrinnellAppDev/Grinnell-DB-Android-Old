package edu.grinnell.appdev.grinnelldirectory.dummy;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import edu.grinnell.appdev.grinnelldirectory.dummy.Profile;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

class ImgDownload extends AsyncTask {

    protected Bitmap doInBackground(String... uri) {
    	URL url;        
        InputStream in;
        Bitmap pic = null;

        //BufferedInputStream buf;
        try {
            url = new URL(uri[0]);
            in = url.openStream();

            BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(uri[0]));
            int i;

             while ((i = in.read()) != -1) {
                 out.write(i);
             }
             out.close();
             in.close();

            BufferedInputStream buf = new BufferedInputStream(in);
            pic = BitmapFactory.decodeStream(buf);
            
            if (in != null) {
            in.close();
            }
            if (buf != null) {
            buf.close();
            }
            return pic;
        } catch (Exception e) {
            Log.e("Error reading file", e.toString());
        }
		return pic;
    }

    @Override
    protected void onPostExecute(Object o) {
    }

	@Override
	protected Object doInBackground(Object... params) {
		// TODO Auto-generated method stub
		return null;
	}
}