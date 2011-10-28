package mcg.android.Droid500px;

import mcg.android.Droid500px.helpers.ImageAdapter;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewStub;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class ImageViewActivity extends Activity {
	private ImageView imageView = null;
	private boolean panelOn = false;
	private View photoDataPanel = null;
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.imageview);
        imageView = (ImageView) findViewById(R.id.imageView);
        String url = this.getIntent().getExtras().getString("url");
        imageView.setImageBitmap(ImageAdapter.getImageFromURL(url));
        imageView.setAdjustViewBounds(false);
        
        imageView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (photoDataPanel == null) {
					photoDataPanel = ((ViewStub) findViewById(R.id.stub_stats)).inflate();
				}
	            TextView photographyName   = (TextView) photoDataPanel.findViewById(R.id.label_photography_name);
	            TextView photographerName  = (TextView) photoDataPanel.findViewById(R.id.label_photographer_name);
	            TextView photographyRating = (TextView) photoDataPanel.findViewById(R.id.label_photography_rating);
	            
	            photographerName.setText("MCG");
	            photographyName.setText("MCG Testando Labels");
	            photographyRating.setText("99.9");
	            
				if(panelOn){
					hidePanel(photoDataPanel, true);
				} else {
					showPanel(photoDataPanel, true);
				}
			}
		});
    }

    private void showPanel(View panel, boolean slideUp) {
        panel.startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_in));
        panel.setVisibility(View.VISIBLE);
        panelOn = true;
    }    

    private void hidePanel(View panel, boolean slideDown) {
        panel.startAnimation(AnimationUtils.loadAnimation(this, R.anim.slide_out));
        panel.setVisibility(View.GONE);
        panelOn = false;
    }

}

