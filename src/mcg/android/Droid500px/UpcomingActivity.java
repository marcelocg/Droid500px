package mcg.android.Droid500px;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class UpcomingActivity extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TextView textview = new TextView(this);
        textview.setText("This is the 500px Upcoming Photos tab");
        setContentView(textview);
    }

}
