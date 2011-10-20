package mcg.android.Droid500px.helpers;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter {
    int galleryItemBackground;
    private Context context;

    private String[] photosURLs;

    public ImageAdapter(Context c) {
        context = c;
    }

    public int getCount() {
        return photosURLs == null ? 0 : photosURLs.length;
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {  // if it's not recycled, initialize some attributes
            imageView = new ImageView(context);
            imageView.setPadding(0, 0, 0, 0);
//            imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
//            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        } else {
            imageView = (ImageView) convertView;
        }
        imageView.setImageBitmap(getImageFromURL(photosURLs[position]));
        //imageView.setLayoutParams(new Gallery.LayoutParams(150, 100));
        //imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        //imageView.setBackgroundResource(galleryItemBackground);
        imageView.setAdjustViewBounds(false);

        return imageView;
    }

	private Bitmap getImageFromURL(String url) {
		URL imageURL = null;
		InputStream is = null;
		try {
			imageURL = new URL(url);
	        try {
				HttpURLConnection conn= (HttpURLConnection)imageURL.openConnection();
		        conn.setDoInput(true);
				conn.connect();
		        is = conn.getInputStream();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        Bitmap bmp = BitmapFactory.decodeStream(is);
        return bmp;
	}

	public String[] getPhotosURLs() {
		return photosURLs;
	}

	public void setPhotosURLs(String[] photosURLs) {
		this.photosURLs = photosURLs;
	}
}