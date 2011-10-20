package mcg.android.Droid500px;

import mcg.android.Droid500px.helpers.ImageAdapter;
import mcg.android.Droid500px.helpers.RequestManager;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.Toast;

public class GridViewActivity extends Activity {

	private String[] urls = null;
	private ImageAdapter imageAdapter = null;
	private GridView grid = null;
	
	private void setPhotoStream(String stream){

		grid = (GridView) findViewById(R.id.gridview);
		urls = RequestManager.readPhotoStream(stream);
        imageAdapter = new ImageAdapter(this);
        imageAdapter.setPhotosURLs(urls);

        grid.setAdapter(imageAdapter);
	}

    public void onCreate(Bundle savedInstanceState, String stream) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.gridview);
        
        this.setPhotoStream(stream);

        grid.setOnItemClickListener(
        	new OnItemClickListener() {
        		public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
        			Toast.makeText(GridViewActivity.this, "" + position, Toast.LENGTH_SHORT).show();
        		}
        	}
        );    
    }
	
}
