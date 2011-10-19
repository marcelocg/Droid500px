package mcg.android.Droid500px;

import mcg.android.Droid500px.helpers.ImageAdapter;
import mcg.android.Droid500px.helpers.RequestManager;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Gallery;
import android.widget.Toast;

public class FreshActivity extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.gallery);

        Gallery gallery = (Gallery) findViewById(R.id.gallery);
        ImageAdapter imageAdapter = new ImageAdapter(this);
        
        String[] urls = RequestManager.readPhotoStream(RequestManager.FRESH_TODAY);
        imageAdapter.setPhotosURLs(urls);
        
        gallery.setAdapter(imageAdapter);

        gallery.setOnItemClickListener(
        	new OnItemClickListener() {
        		public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
        			Toast.makeText(FreshActivity.this, "" + position, Toast.LENGTH_SHORT).show();
        		}
        	}
        );    
    }
}
