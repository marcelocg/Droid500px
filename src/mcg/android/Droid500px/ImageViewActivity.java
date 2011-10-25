package mcg.android.Droid500px;

import mcg.android.Droid500px.helpers.ImageAdapter;
import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

public class ImageViewActivity extends Activity {
	ImageView imageView = null;
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.imageview);
        imageView = (ImageView) findViewById(R.id.imageView);
        String url = this.getIntent().getExtras().getString("url");
        imageView.setImageBitmap(ImageAdapter.getImageFromURL(url));
        imageView.setAdjustViewBounds(false);
    }

}
