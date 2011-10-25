package mcg.android.Droid500px;

import java.util.Arrays;

import mcg.android.Droid500px.helpers.ImageAdapter;
import mcg.android.Droid500px.helpers.RequestManager;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

public class GridViewActivity extends Activity {

	private int lastPageLoaded = 0;
	private String stream = null;
	private String[] urls = null;
	private ImageAdapter imageAdapter = null;
	private GridView grid = null;

    public void onCreate(Bundle savedInstanceState, String stream) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.gridview);
        
        this.initializePhotoStream(stream);

        grid.setOnItemClickListener(
        	new OnItemClickListener() {
        		public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
        			showImage(position);
//        			Toast.makeText(GridViewActivity.this, "" + (position + 1), Toast.LENGTH_SHORT).show();
        		}
        	}
        );
        
        grid.setOnScrollListener(
        	new OnScrollListener() {
        		@Override
        		public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) { 
        			boolean loadMore = firstVisibleItem + visibleItemCount >= totalItemCount;
        			
        			 if(loadMore) {
        				 addPhotos();
        				 imageAdapter.notifyDataSetChanged();
        		     }
        		}

				@Override
				public void onScrollStateChanged(AbsListView view, int scrollState) {
					// No implementation needed so far
				}
        	}
        );
    }
	
	private void showImage(int position) {
		Intent intent = new Intent(this, ImageViewActivity.class);
		String url = urls[position];
		url = url.replace("/1.jpg", "/3.jpg");
		intent.putExtra("url", url);
		startActivity(intent);
	}

	private void initializePhotoStream(String stream){
		setPhotoStream(stream);
		grid = (GridView) findViewById(R.id.gridview);
		urls = RequestManager.readPhotoStream(stream);
		lastPageLoaded = 1;
        imageAdapter = new ImageAdapter(this);
        imageAdapter.setPhotosURLs(urls);

        grid.setAdapter(imageAdapter);
	}

	private void setPhotoStream(String stream) {
		this.stream = stream;
	}
	
	public void refresh(){
		urls = RequestManager.readPhotoStream(stream);
		lastPageLoaded = 1;
        imageAdapter = new ImageAdapter(this);
        imageAdapter.setPhotosURLs(urls);
        imageAdapter.notifyDataSetChanged();
        grid.setAdapter(imageAdapter);
	}

	private void addPhotos(){
		int lastLength = urls.length;
		urls = Arrays.copyOf(urls, urls.length + RequestManager.DEFAULT_PHOTOS_PER_PAGE);
		String[] nextPhotos = RequestManager.readPhotoStreamPage(stream, ++lastPageLoaded);
		System.arraycopy(nextPhotos, 0, urls, lastLength, nextPhotos.length);
		imageAdapter.setPhotosURLs(urls);
	}
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.tab_options_menu, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
        case R.id.refresh:
            refresh();
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }
}
